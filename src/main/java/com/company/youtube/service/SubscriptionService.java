package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.subscraption.SubscriptionDTO;
import com.company.youtube.dto.subscraption.SubscriptionResponseDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.subscraption.SubscriptionShortInfoDTO;
import com.company.youtube.dto.subscraption.UpdateNotififSubscriptionDTO;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.CommentEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.SubscriptionEntity;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.mapper.SubscriptionShortInfoRepository;
import com.company.youtube.repository.SubscriptionRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ChannelService channelService;

    @Value("${server.url}")
    private String serverUrl;

    public SubscriptionResponseDTO create(SubscriptionDTO dto) {

        ChannelEntity channel = channelService.get(dto.getChannelId());
        Optional<SubscriptionEntity> repository = subscriptionRepository.findByChannelAndProfileAndVisibleTrue(channel, getProfile());
        if (repository.isPresent()) {
            throw new AlreadyExist("Alredy exsists");
        }

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setChannel(channel);
        subscription.setProfile(getProfile());
        subscription.setType(dto.getType());
        subscriptionRepository.save(subscription);

        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setId(subscription.getId());
        response.setChannel(new ChannelDTO(subscription.getChannel().getId()));
        response.setProfile(new ProfileDTO(getProfile().getId()));
        response.setNotificationType(subscription.getType());
        response.setCreatedDate(subscription.getCreatedDate());
        return response;
    }

    public ResposeDTO changeStatus(Integer id, SubscriptionDTO dto) {
        Optional<SubscriptionEntity> repository = subscriptionRepository.findByIdAndVisibleTrue(id);
        if (repository.isEmpty()) {
            throw new BadRequestException("not found");
        } else if (!repository.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        subscriptionRepository.updateStatus(dto.getStatus(), id);
        return new ResposeDTO("sucsess", true);

    }

    public ResposeDTO changeNotification(Integer id, UpdateNotififSubscriptionDTO dto) {
        Optional<SubscriptionEntity> repository = subscriptionRepository.findByIdAndVisibleTrue(id);
        if (repository.isEmpty()) {
            throw new BadRequestException("not found");
        } else if (!repository.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        repository.get().setType(dto.getType());
        subscriptionRepository.updateNotification(dto.getType(), id);
        return new ResposeDTO("sucsess", true);

    }

    public List<SubscriptionShortInfoDTO> userSubscriptionList() {

        List<SubscriptionShortInfoRepository> repositories = subscriptionRepository.userSubscriptionList(getProfile().getId());
        List<SubscriptionShortInfoDTO> dtoList = new LinkedList<>();

        repositories.forEach(subscriptionShortInfoRepository -> {


            SubscriptionShortInfoDTO infoDTO = new SubscriptionShortInfoDTO();
            infoDTO.setId(subscriptionShortInfoRepository.getId());

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(subscriptionShortInfoRepository.getchannelId());
            channelDTO.setUrl(serverUrl + "" + "attache/open" + subscriptionShortInfoRepository.getchannelPhoto());

            infoDTO.setChannel(channelDTO);
            infoDTO.setType(subscriptionShortInfoRepository.getNotification());

            dtoList.add(infoDTO);
        });

        return dtoList;
    }

    public List<SubscriptionShortInfoDTO> userSubscriptionListByUserId(Integer id) {

        if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }

        List<SubscriptionShortInfoRepository> repositories = subscriptionRepository.userSubscriptionList(id);
        List<SubscriptionShortInfoDTO> dtoList = new LinkedList<>();

        repositories.forEach(subscriptionShortInfoRepository -> {

            SubscriptionShortInfoDTO infoDTO = new SubscriptionShortInfoDTO();
            infoDTO.setId(subscriptionShortInfoRepository.getId());

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(subscriptionShortInfoRepository.getchannelId());
            channelDTO.setUrl(serverUrl + "" + "attache/open" + subscriptionShortInfoRepository.getchannelPhoto());

            infoDTO.setChannel(channelDTO);
            infoDTO.setType(subscriptionShortInfoRepository.getNotification());

            dtoList.add(infoDTO);
        });

        return dtoList;

    }


    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }


}
