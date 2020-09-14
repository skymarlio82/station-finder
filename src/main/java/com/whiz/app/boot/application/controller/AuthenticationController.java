package com.whiz.app.boot.application.controller;

import com.whiz.app.boot.application.service.AuthenticationService;
import com.whiz.app.boot.infrastructure.security.JwtFilter;
import com.whiz.app.boot.infrastructure.security.TokenProvider;
import com.whiz.app.boot.interfaces.dto.JwtToken;
import com.whiz.app.boot.interfaces.dto.form.LoginForm;
import com.whiz.app.boot.interfaces.dto.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@Controller
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public AuthenticationController(
        AuthenticationService authenticationService,
        AuthenticationManagerBuilder authenticationManagerBuilder,
        TokenProvider tokenProvider) {
        this.authenticationService = authenticationService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/authorization/callback")
    public void authorizationCodeCallback(String code, HttpServletRequest request, HttpServletResponse response) {
        authenticationService.loginAsOAuth2(code, request, response);
    }

    @GetMapping("/")
    public String indexPage(@RequestParam(value = "err", required = false) Integer err, Model model) {
        if (err != null && err == 1) {
            model.addAttribute("message", "User not existed");
        }
        return "index";
    }

    @GetMapping("/info")
    public String infoPage() {
        return "info";
    }

    @ApiOperation(value = "Login to Authenticate the User", nickname = "LoginUser")
    @PostMapping("/login/native")
    public ResponseEntity<ResponseResult<JwtToken>> loginUser(@Valid @RequestBody LoginForm loginForm) {
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
            loginForm.getUsername(), loginForm.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(upat);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = loginForm.getRememberMe() != null && loginForm.getRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(ResponseResult.success(new JwtToken(jwt)), httpHeaders, HttpStatus.OK);
    }
}