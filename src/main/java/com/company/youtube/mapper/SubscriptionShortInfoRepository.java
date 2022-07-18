package com.company.youtube.mapper;

import com.company.youtube.enums.NotificationType;

public interface SubscriptionShortInfoRepository {



    Integer getId();
    String getchannelId();
    String getchannelPhoto();

    NotificationType getNotification();

}
