package com.company.youtube;

import com.company.youtube.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YouTubeApplicationTests {

    @Autowired
    private ProfileService profileService;

    @Test
    void contextLoads() {
    }
/*    @Test
    void createProfile() {
        ProfileDTO profile = new ProfileDTO();
        profile.setName("Toshmat");
        profile.setSurname("Toshmatov");
        profile.setEmail("toshmat@mail.tu");
        profile.setPassword("1234");
        profile.setRole(ProfileRole.ROLE_ADMIN);
        profileService.create(profile);
    }*/
}
