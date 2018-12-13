package test.oauth.common;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import test.oauth.domain.users.UserEntity;
import test.oauth.domain.users.UserService;

@Component
public class CustomSecurityPermissionEvaluator implements PermissionEvaluator {
	
    @Autowired
    private UserService userService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    	// custom
    	return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    	// custom
    	return false;
    }

    // 접근하려는 targetDomainObject이 요청한 token과 같은지
    // 내가 나의 자원에만 접근해야 할 경우 사용
    public boolean isMe(Authentication authentication, Object targetDomainObject) {
    	UserEntity user = userService.getByEmail(authentication.getName());
    	if (user.getSid() == Long.valueOf(targetDomainObject.toString())) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean isNotMe(Authentication authentication, Object targetDomainObject) {
        return !isMe(authentication, targetDomainObject);
    }

}