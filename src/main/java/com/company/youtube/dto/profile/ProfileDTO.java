package com.company.youtube.dto.profile;

import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    private Integer id;

    @NotBlank
    @Size(min = 4)
    private String name;

    @NotBlank
    @Size(min = 4)
    private String surname;

    @Email
    private String email;

    private ProfileRole role;

    private String url;

    @NotBlank
    @Size(min = 4)
    private String password;

    private AttachDTO phototId;

    private String jwt;

    public ProfileDTO() {
    }

    public ProfileDTO(Integer id) {
        this.id = id;
    }
}
