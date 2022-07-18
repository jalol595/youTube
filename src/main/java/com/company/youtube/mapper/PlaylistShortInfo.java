package com.company.youtube.mapper;

import java.time.LocalDateTime;

public interface PlaylistShortInfo {

    Integer getPlaylistId();

    String getPlaylistName();

    LocalDateTime getPlaylistCreatedDate();

    String getChannelId();

    String getChannelName();

    Integer getCountVideo();

    Integer getTotalWatchedCount();
}
