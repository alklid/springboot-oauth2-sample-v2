package com.alklid.oauth2.domain.account;


import com.alklid.oauth2.common.DefineException;
import com.alklid.oauth2.common.ErrorResponseBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                    " and hasAuthority('USER:INFO')")
    @RequestMapping(method = RequestMethod.GET, value = "/{users_sid}")
    public ResponseEntity<AccountDto.Response> getAccount(  HttpServletRequest httpRequest,
                                                            @PathVariable("v") String v,
                                                            @PathVariable("users_sid") Long users_sid) {
        switch (v) {
            case "1.0":
                AccountEntity user = accountService.getAccount(httpRequest, users_sid);
                return new ResponseEntity<>(modelMapper.map(user, AccountDto.Response.class), HttpStatus.OK);
            default:
                throw new DefineException.InvalidAPIVersion(v, httpRequest);
        }
    }






    @ExceptionHandler(AccountDefineException.Duplicated.class)
    public ResponseEntity<ErrorResponseBean> DuplicatedHandler(AccountDefineException.Duplicated e) {
        ErrorResponseBean errorResponseBean = new ErrorResponseBean();
        errorResponseBean.setStatus(HttpStatus.CONFLICT.value());
        errorResponseBean.setError("duplicated_user");
        errorResponseBean.setMessage("동일한 데이터가 존재합니다. [" + e.getEmail() + "]");
        errorResponseBean.setPath(e.getPath());
        return new ResponseEntity<>(errorResponseBean, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountDefineException.NotFound.class)
    public ResponseEntity<ErrorResponseBean> NotFoundHandler(final AccountDefineException.NotFound e) {
        ErrorResponseBean errorResponseBean = new ErrorResponseBean();
        errorResponseBean.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseBean.setError("not_found_user");
        errorResponseBean.setMessage("데이터가 없습니다. [" + e.getUsers_sid() + "] " + e.getEmail());
        errorResponseBean.setPath(e.getPath());
        return new ResponseEntity<>(errorResponseBean, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AccountDefineException.invalidPwd.class)
    public ResponseEntity<ErrorResponseBean> invalidPwd(final AccountDefineException.invalidPwd e) {
        ErrorResponseBean errorResponseBean = new ErrorResponseBean();
        errorResponseBean.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponseBean.setError("invalid_pwd");
        errorResponseBean.setMessage(" 잘못된 비밀번호입니다. [" + e.getEmail() + "]");
        errorResponseBean.setPath(e.getPath());
        return new ResponseEntity<>(errorResponseBean, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(AccountDefineException.samePwd.class)
    public ResponseEntity<ErrorResponseBean> samePwd(final AccountDefineException.samePwd e) {
        ErrorResponseBean errorResponseBean = new ErrorResponseBean();
        errorResponseBean.setStatus(HttpStatus.CONFLICT.value());
        errorResponseBean.setError("duplicated_pwd");
        errorResponseBean.setMessage(" 기존 비밀번호와 변경하고자 하는 비밀번호와 동일합니다. [" + e.getEmail() + "]");
        errorResponseBean.setPath(e.getPath());
        return new ResponseEntity<>(errorResponseBean, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(AccountDefineException.UserSidNotMatched.class)
    public ResponseEntity<ErrorResponseBean> UserSidNotMatched(final AccountDefineException.UserSidNotMatched e) {
        ErrorResponseBean errorResponseBean = new ErrorResponseBean();
        errorResponseBean.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseBean.setError("not_matched_user_sid");
        errorResponseBean.setMessage("잘못된 요청입니다. [" + e.getUsers_sid() + "]");
        errorResponseBean.setPath(e.getPath());
        return new ResponseEntity<>(errorResponseBean, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(AccountDefineException.UserEmailNotMatched.class)
    public ResponseEntity<ErrorResponseBean> UserEmailNotMatched(final AccountDefineException.UserEmailNotMatched e) {
        ErrorResponseBean errorResponseBean = new ErrorResponseBean();
        errorResponseBean.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseBean.setError("not_matched_user_email");
        errorResponseBean.setMessage("사용자 이메일 정보가 일치하지 않습니다.");
        errorResponseBean.setPath(e.getPath());
        return new ResponseEntity<>(errorResponseBean, HttpStatus.NOT_FOUND);
    }
}
