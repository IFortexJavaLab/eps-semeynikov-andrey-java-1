CREATE TABLE IF NOT EXISTS course
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255)                  NOT NULL,
    description      TEXT,
    price            DECIMAL(10, 2)                NOT NULL,
    duration         INTEGER                       NOT NULL,
    start_date       TIMESTAMP                     NOT NULL,
    last_update_date TIMESTAMP                     NOT NULL,
    course_status    VARCHAR(255) DEFAULT 'OPENED' NOT NULL
);

CREATE TABLE IF NOT EXISTS student
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS course_student
(
    course_id  BIGINT,
    student_id BIGINT,
    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    Foreign Key (student_id) REFERENCES student (id) ON DELETE CASCADE
);


WITH gen_courses AS (SELECT 'Course ' || gs::text                                        AS name,
                            'Description for course ' || gs::text                        AS description,
                            (500 + (gs % 5) * 200)                                       AS price,
                            (30 + (gs % 5) * 15)                                         AS duration,
                            (CURRENT_DATE + interval '1 day' * (gs % 30))::timestamp     AS start_date,
                            (CURRENT_TIMESTAMP - interval '1 day' * (gs % 7))::timestamp AS last_update_date,
                            'OPENED'                                                     AS course_status
                     FROM generate_series(1, 1000) AS gs)
INSERT
INTO course (name, description, price, duration, start_date, last_update_date, course_status)
SELECT *
FROM gen_courses;

WITH gen_students AS (SELECT 'Student ' || gs::text AS name
                      FROM generate_series(1, 5000) AS gs)
INSERT
INTO student (name)
SELECT *
FROM gen_students;

DO
$$
    DECLARE
        batch_start_course  INT := 0;
        batch_start_student INT := 0;
        course_batch_size   INT := 10;
        student_batch_size  INT := 50;
        total_courses       INT;
        total_students      INT;
    BEGIN

        SELECT COUNT(*) INTO total_courses FROM course;
        SELECT COUNT(*) INTO total_students FROM student;

        WHILE batch_start_course < total_courses AND batch_start_student < total_students
            LOOP
                -- Cross join 10 courses with 50 students in the current batch
                WITH batch_courses AS (SELECT id AS course_id
                                       FROM course
                                       ORDER BY id
                                       OFFSET batch_start_course LIMIT course_batch_size),
                     batch_students AS (SELECT id AS student_id
                                        FROM student
                                        ORDER BY id
                                        OFFSET batch_start_student LIMIT student_batch_size)
                INSERT
                INTO course_student (course_id, student_id)
                SELECT c.course_id, s.student_id
                FROM batch_courses c
                         CROSS JOIN batch_students s;

                batch_start_course := batch_start_course + course_batch_size;
                batch_start_student := batch_start_student + student_batch_size;
            END LOOP;

    END
$$;

CREATE OR REPLACE PROCEDURE update_today_courses_status()
    LANGUAGE plpgsql
AS
$$
BEGIN
    UPDATE course c
    SET course_status = 'CLOSED'
    WHERE c.start_date::DATE = current_date
      AND (SELECT count(*)
           FROM course_student cs
           WHERE cs.course_id = c.id) < 30;

    UPDATE course c
    SET course_status = 'STARTED'
    WHERE c.start_date::DATE = current_date
      AND (SELECT count(*)
           FROM course_student cs
           WHERE cs.course_id = c.id) >= 30;
END;
$$;