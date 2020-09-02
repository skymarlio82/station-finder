package com.whiz.app.boot.application.service;

import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.service.UserProfileService;
import com.whiz.app.boot.infrastructure.config.GithubOauth2Properties;
import com.whiz.app.boot.infrastructure.security.TokenProvider;
import com.whiz.app.boot.interfaces.dto.GithubAccessToken;
import com.whiz.app.boot.interfaces.dto.GithubUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class AuthenticationService {
    private final UserProfileService userProfileService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final GithubOauth2Properties githubOauth2Properties;

    public AuthenticationService(
        UserProfileService userProfileService,
        AuthenticationManagerBuilder authenticationManagerBuilder,
        TokenProvider tokenProvider,
        GithubOauth2Properties githubOauth2Properties) {
        this.userProfileService = userProfileService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.githubOauth2Properties = githubOauth2Properties;
    }

    @Transactional
    public void loginAsOAuth2(String code, HttpServletRequest request, HttpServletResponse response) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> req = new HttpEntity<>(headers);
        String tokenGetUrl = githubOauth2Properties.getTokenGetUrl() +
            "?client_id=" + githubOauth2Properties.getClientId() +
            "&client_secret=" + githubOauth2Properties.getClientSecret() +
            "&code=" + code;
        log.debug("tokenGetUrl = {}", tokenGetUrl);
        ResponseEntity<GithubAccessToken> resTokenGet = restTemplate.postForEntity(
            tokenGetUrl,
            req,
            GithubAccessToken.class);
        log.debug("resTokenGet = {}", resTokenGet);
        GithubAccessToken accessTokenDto = resTokenGet.getBody();
        log.debug("accessTokenDto = {}", accessTokenDto);
        Assert.notNull(accessTokenDto, "AccessToken from Github deserialized failed!");
        headers.set("Authorization", "token " + accessTokenDto.getAccessToken());
        String userInfoUrl = githubOauth2Properties.getUserInfoUrl() +
            "?access_token=" + accessTokenDto.getAccessToken();
        log.debug("userInfoUrl = {}", userInfoUrl);
        ResponseEntity<GithubUserInfo> resUserInfo = restTemplate.getForEntity(userInfoUrl, GithubUserInfo.class);
        log.debug("resUserInfo = {}", resUserInfo);
        GithubUserInfo userInfo = resUserInfo.getBody();
        log.debug("userInfo = {}", userInfo);
        Assert.notNull(userInfo, "UserInfo from Github deserialized failed!");
        UserProfile user = userProfileService.getUserProfileByEmail(userInfo.getUserEmail());
        if (user == null) {
            response.setHeader("Location", request.getContextPath() + "?err=1");
            response.setStatus(302);
            return;
        }
        user.getCredential().setPassword((new BCryptPasswordEncoder()).encode(userInfo.getLoginId()));
        userProfileService.saveNewUser(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            user.getCredential().getUsername(),
            userInfo.getLoginId());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);
        userInfo.setAccessToken(accessTokenDto.getAccessToken());
        HttpSession session = request.getSession();
        session.setAttribute("USER_CONFIG", userInfo);
        response.setHeader("Location", request.getContextPath() + "/info?jwt=" + jwt);
//        response.setHeader("Location", "http://localhost:8090/#/oauth2Token?jwt=" + jwt);
        response.setStatus(302);
    }
}