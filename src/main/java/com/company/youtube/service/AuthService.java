package com.company.youtube.service;


import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.profile.AuthDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${server.url}")
    private String serverUrl;

    @Autowired
    private AuthenticationManager authenticationManager;
    public ProfileDTO login(AuthDTO authDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();
        ProfileEntity profile = user.getProfile();

        ProfileDTO dto = new ProfileDTO();
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setEmail(profile.getEmail());

        if (profile.getPhoto()!=null){
            dto.setUrl(serverUrl + "" + "attache/open/" + profile.getPhoto().getId());
        }else {
            dto.setUrl(null);
        }


        dto.setJwt(JwtUtil.encode(profile.getId(), profile.getRole()));

        return dto;
    }
}
