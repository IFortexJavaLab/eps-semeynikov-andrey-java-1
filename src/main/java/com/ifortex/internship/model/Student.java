package com.ifortex.internship.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {

  @EqualsAndHashCode.Exclude private Long id;
  private String name;
}
