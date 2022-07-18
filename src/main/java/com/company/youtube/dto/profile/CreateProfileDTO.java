package com.company.youtube.dto.profile;

import com.company.youtube.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProfileDTO {

    private Integer id;

    @NotBlank
    @Size(min = 4)
    private String name;

    @NotBlank
    @Size(min = 4)
    private String surname;

    @Email
    private String email;

    @NotNull
    private ProfileRole role;


    @NotBlank
    @Size(min = 4)
    private String password;

}
