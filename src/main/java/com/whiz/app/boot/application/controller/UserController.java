package com.whiz.app.boot.application.controller;

import com.whiz.app.boot.application.service.UserService;
import com.whiz.app.boot.domain.model.Authority;
import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.service.UserProfileService;
import com.whiz.app.boot.infrastructure.event.UserUpdateEventPublisher;
import com.whiz.app.boot.interfaces.dto.PagingParamRequest;
import com.whiz.app.boot.interfaces.dto.UserDetail;
import com.whiz.app.boot.interfaces.dto.form.NewUserForm;
import com.whiz.app.boot.interfaces.dto.form.UserUpdatedForm;
import com.whiz.app.boot.interfaces.dto.response.GenericResponseData;
import com.whiz.app.boot.interfaces.dto.response.ResponseDataType;
import com.whiz.app.boot.interfaces.dto.response.SimplePageData;
import com.whiz.app.boot.interfaces.dto.response.SimpleResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "RESTful APIs of User Provided Outside")
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final UserUpdateEventPublisher userUpdateEventPublisher;

    public UserController(
        UserService userService,
        UserProfileService userProfileService,
        UserUpdateEventPublisher userUpdateEventPublisher) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.userUpdateEventPublisher = userUpdateEventPublisher;
    }

    @ApiOperation(value = "Get Profile Details of Current User", nickname = "GetActualUser")
    @GetMapping("/me")
    public ResponseEntity<UserDetail> getActualUser() {
        UserProfile userProfile = userService.getUserProfileWithAuthorities();
        return ResponseEntity.ok(UserDetail.from(userProfile));
    }

    @ApiOperation(value = "Get All Users in System", nickname = "GetAllUsers")
    @GetMapping("/all")
    public ResponseEntity<GenericResponseData<SimplePageData<UserProfile>>> getAllUsers(
        @Valid PagingParamRequest pagingParamRequest) {
        Page<UserProfile> userProfilePage = userProfileService.getAllUsers(pagingParamRequest);
        return ResponseEntity.ok(GenericResponseData.ok(SimplePageData.of(userProfilePage)));
    }

    @ApiOperation(value = "Get Specified User Profile Details by Id", nickname = "GetUserById")
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDetail> getUserById(@ApiParam(value = "userId") @PathVariable Long id) {
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        return ResponseEntity.ok(UserDetail.from(userProfile));
    }

    @ApiOperation(value = "Create New User Entity", nickname = "NewUser")
    @PostMapping("/users")
    public ResponseEntity<GenericResponseData<UserDetail>> createNewUser(@RequestBody @Valid NewUserForm newUserForm) {
        log.debug("newUserForm = {}", newUserForm);
        List<Authority> authorities = userProfileService.getAuthorityListByNames(newUserForm.getAuthorities());
        UserProfile userProfile = userProfileService.saveNewUser(UserProfile.from(newUserForm, authorities));
        return ResponseEntity.ok(GenericResponseData.ok(UserDetail.from(userProfile)));
    }

    @ApiOperation(value = "Update Exist User Entity", nickname = "UpdateUser")
    @PutMapping("/users")
    public ResponseEntity<SimpleResponseData> updateUser(@RequestBody @Valid UserUpdatedForm userUpdatedForm) {
        log.debug("userUpdatedForm = {}", userUpdatedForm);
        UserProfile user = userProfileService.getUserProfileById(userUpdatedForm.getId());
        if (null != user) {
            List<Authority> authorities = userProfileService.getAuthorityListByNames(userUpdatedForm.getAuthorities());
            userUpdateEventPublisher.doPublishEvent(user.sync(userUpdatedForm, authorities));
            return ResponseEntity.ok(SimpleResponseData.success());
        }
        return ResponseEntity.ok(SimpleResponseData.failure(ResponseDataType.BAD_REQUEST, "User not existed"));
    }
}