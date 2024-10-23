CREATE TYPE course_type AS ENUM ('OPENED', 'CLOSED');

create table course
(
    id                      SERIAL PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    description             TEXT,
    price                   REAL         NOT NULL,
    duration                INTEGER      NOT NULL,
    start_date              TIMESTAMP,
    last_update_date        TIMESTAMP    NOT NULL,
    course_type             course_type  NOT NULL
);

create table student
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table course_student
(
    course_id   INTEGER,
    student_id  INTEGER,
    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id)     REFERENCES course(id)   ON DELETE CASCADE,
    Foreign Key (student_id)    REFERENCES student(id)  ON DELETE CASCADE
);


WITH gen_courses AS (
    SELECT
        'Course '                                        || gs::text AS name,
        'Description for course '                        || gs::text AS description,
        (500 + (gs % 5) * 200)                                       AS price,
        (30 + (gs % 5) * 15)                                         AS duration,
        (CURRENT_DATE + interval '1 day' * (gs % 30))::timestamp     AS start_date,
        (CURRENT_TIMESTAMP - interval '1 day' * (gs % 7))::timestamp AS last_update_date,
        'OPENED'::course_type                                        AS course_type
    FROM
        generate_series(1, 1000) AS gs
)
INSERT INTO course (name, description, price, duration, start_date, last_update_date, course_type)
SELECT *
FROM gen_courses;

WITH gen_students AS (
    SELECT 'Student ' || gs::text   AS name
    FROM    generate_series(1, 350) AS gs
)
INSERT INTO student (name)
SELECT *
FROM gen_students;

WITH course_student_enrollments AS (
    SELECT
        c.id AS course_id,
        s.id AS student_id
    FROM
        course c
            CROSS JOIN
        student s
    WHERE
        s.id IN (
            SELECT s2.id
            FROM student s2
            ORDER BY random()
    LIMIT 30 + (random() * 21)::int
    )
)
INSERT INTO course_student (course_id, student_id)
SELECT     course_id, student_id
FROM       course_student_enrollments
ORDER BY   course_id, student_id;
