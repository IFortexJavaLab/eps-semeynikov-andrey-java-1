package com.ifortex.internship.dto;

import com.ifortex.internship.dto.markers.Create;
import com.ifortex.internship.dto.markers.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentDto {

  @EqualsAndHashCode.Exclude private long id;

  @NotNull(message = "Name can't be null", groups = Create.class)
  @Size(
      min = 2,
      max = 50,
      message = "Name must be between 2 and 50 characters long",
      groups = {Update.class, Create.class})
  private String name;
}
