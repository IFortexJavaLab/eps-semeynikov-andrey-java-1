package com.ifortex.internship.dto;

import com.ifortex.internship.dto.enumeration.SortType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CourseFilterSortDto {
  private String studentName;
  private String courseName;
  private String courseDescription;
  private SortType sortByDate;
  private SortType sortByName;
}
