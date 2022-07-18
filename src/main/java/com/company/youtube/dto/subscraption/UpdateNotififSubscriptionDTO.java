package com.company.youtube.dto.subscraption;

import com.company.youtube.enums.NotificationType;
import com.company.youtube.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNotififSubscriptionDTO {

    private String channelId;

    private NotificationType type;


}
