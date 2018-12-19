package com.medit.meditlink.domain.user;


import com.medit.meditlink.common.Router;
import com.medit.meditlink.common.exception.GlobalException;
import com.medit.meditlink.domain.user.model.UserDto;
import com.medit.meditlink.domain.user.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(Router.User.USER)
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    // info
    @PreAuthorize(  "isAuthenticated()" +
                    " and #oauth2.hasScope('MANAGE')" +
                    " and hasAuthority('USER:INFO')" +
                    " and @customSecurityPermissionEvaluator.isMe(authentication, #users_email)")
    @RequestMapping(method = RequestMethod.GET, value = "/{users_email}")
    public ResponseEntity<UserDto.Response> getAccount(HttpServletRequest httpRequest,
                                                        @PathVariable("v") String v,
                                                        @PathVariable("users_email") String users_email) {
        switch (v) {
            case "v1":
                UserEntity user = userService.getAccount(users_email);
                return new ResponseEntity<>(modelMapper.map(user, UserDto.Response.class), HttpStatus.OK);
            default:
                throw new GlobalException.InvalidAPIVersion(v);
        }
    }

    // update Test
    @PreAuthorize(  "isAuthenticated()" +
            " and #oauth2.hasScope('MANAGE')" +
            " and hasAuthority('USER:INFO')" +
            " and @customSecurityPermissionEvaluator.isMe(authentication, #users_email)")
    @RequestMapping(method = RequestMethod.PUT, value = "/{users_email}")
    public ResponseEntity<UserDto.Response> updateAccount(HttpServletRequest httpRequest,
                                                          @PathVariable("v") String v,
                                                          @PathVariable("users_email") String users_email,
                                                          @RequestBody @Valid UserDto.Update user) {
        switch (v) {
            case "v1":
                UserEntity updateUser = userService.updateAccount(users_email, user);
                return new ResponseEntity<>(modelMapper.map(updateUser, UserDto.Response.class), HttpStatus.OK);
            default:
                throw new GlobalException.InvalidAPIVersion(v);
        }
    }
}
