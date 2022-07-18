package com.company.youtube.dto.profile;

import com.company.youtube.entity.AttachEntity;
import com.company.youtube.enums.ProfileRole;
import com.company.youtube.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterDTO {


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
    @Size(min = 4)
    private String password;

    private String photoId;





}
