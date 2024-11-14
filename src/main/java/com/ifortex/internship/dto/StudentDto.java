package com.ifortex.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifortex.internship.dto.markers.Create;
import com.ifortex.internship.dto.markers.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

  @JsonProperty("id")
  private long id;

  @NotNull(message = "Name can't be null", groups = Create.class)
  @Size(
      min = 2,
      max = 50,
      message = "Name must be between 2 and 50 characters long",
      groups = {Update.class, Create.class})
  @JsonProperty("name")
  private String name;
}
