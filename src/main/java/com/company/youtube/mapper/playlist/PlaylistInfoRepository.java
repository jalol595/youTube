package com.company.youtube.mapper.playlist;

import com.company.youtube.enums.PlaylistStatus;

public interface PlaylistInfoRepository {

//      id,name,description,status(private,public),order_num,
//            channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))

    Integer getId();
    String getName();
    String getDescription();
    PlaylistStatus getStatus();
    Integer getOrderNum();
    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();
    String getChannelPhotoUrl();

    Integer getProfileId();
    String getProfileName();
    String getProfileSurname();
    String getProfilePhotoId();
    String getProfilePhotoUrl();

}
