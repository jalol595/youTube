package com.company.youtube.dto.playlist;

import com.company.youtube.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeStatusPlayListDTO {

   @NotNull
   private PlaylistStatus status;

}
