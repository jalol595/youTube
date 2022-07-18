package com.company.youtube.dto.subscraption;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionShortInfoDTO {


    private Integer id;
    private ChannelDTO channel;
    private NotificationType type;

}
