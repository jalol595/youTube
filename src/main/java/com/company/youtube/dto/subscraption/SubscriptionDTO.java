package com.company.youtube.dto.subscraption;

import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.NotificationType;
import com.company.youtube.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SubscriptionDTO {


    private Integer profileId;


    private String channelId;

    private SubscriptionStatus status;

    private NotificationType type;


}
