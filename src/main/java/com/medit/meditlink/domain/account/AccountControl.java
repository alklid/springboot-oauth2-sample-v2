package com.medit.meditlink.domain.account;


import com.medit.meditlink.common.exception.GlobalException;
import com.medit.meditlink.domain.account.model.AccountDto;
import com.medit.meditlink.domain.account.model.AccountEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/{v}/account")
public class AccountControl {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;

    // info
    @PreAuthorize(  "isAuthenticated()" +
                    " and #oauth2.hasScope('MANAGE')" +
                    " and hasAuthority('USER:INFO')" +
                    " and @customSecurityPermissionEvaluator.isMe(authentication, #users_email)")
    @RequestMapping(method = RequestMethod.GET, value = "/{users_email}")
    public ResponseEntity<AccountDto.Response> getAccount(HttpServletRequest httpRequest,
                                                          @PathVariable("v") String v,
                                                          @PathVariable("users_email") String users_email) {
        switch (v) {
            case "1.0":
                AccountEntity user = accountService.getAccount(users_email);
                return new ResponseEntity<>(modelMapper.map(user, AccountDto.Response.class), HttpStatus.OK);
            default:
                throw new GlobalException.InvalidAPIVersion(v);
        }
    }
}
