package com.medit.meditlink.domain.user;

import com.medit.meditlink.common.exception.UserException;
import com.medit.meditlink.domain.user.model.UserDto;
import com.medit.meditlink.domain.user.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableJpaAuditing
@Service
@Slf4j
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// UserDetailsService impl, oauth2에서 사용자 인증을 위해 사용
	public UserDetails loadUserByUsername(final String email) {
		UserEntity user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UserException.NotFound(null, email);
		}

		return new org.springframework.security.core.userdetails.User(
						user.getEmail(),
						user.getPwd(),
						getAuthority(user.getPermissions())
		);
	}

	// 사용자의 권한
	private List<SimpleGrantedAuthority> getAuthority(final String permissions) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		if (permissions == null || "".equals(permissions)) {
			authorities.add(new SimpleGrantedAuthority("NONE"));
		} else {
			String[] perms = permissions.split(",");
			for(String perm : perms) {
				authorities.add(new SimpleGrantedAuthority(perm));
			}
		}
		return authorities;
	}


	public UserEntity getAccount(final Long users_sid) {
		return Optional.ofNullable(getBySid(users_sid))
				.orElseThrow(() -> new UserException.NotFound(users_sid, null));
	}


	public UserEntity getAccount(final String users_email) {
		return Optional.ofNullable(getByEmail(users_email))
				.orElseThrow(() -> new UserException.NotFound(null, users_email));
	}


	private UserEntity getBySid(final Long users_sid) {
		Optional<UserEntity> user = userRepo.findById(users_sid);
		if (!user.isPresent()) {
			return null;
		}
		return user.get();
	}


	private UserEntity getByEmail(final String users_email) {
		UserEntity user = userRepo.findByEmail(users_email);
		return user;
	}

	// update user
	@Transactional
	public UserEntity updateAccount(String users_email, UserDto.Update user) {
		UserEntity updateUser = getByEmail(users_email);
		updateUser.setName(user.getName());
		updateUser.setDateUpdate(user.getDateUpdate());

		// update
		return userRepo.save(updateUser);
	}
}
