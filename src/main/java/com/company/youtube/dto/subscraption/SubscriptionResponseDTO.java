package com.company.youtube.dto.subscraption;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.NotificationType;
import com.company.youtube.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class SubscriptionResponseDTO {

    private Integer id;

    private ProfileDTO profile;

    private ChannelDTO channel;

    private SubscriptionStatus status;

    private NotificationType notificationType;

    private LocalDateTime createdDate;

}
