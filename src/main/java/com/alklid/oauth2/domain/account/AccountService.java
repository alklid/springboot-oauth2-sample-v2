package com.alklid.oauth2.domain.account;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableJpaAuditing
@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private ModelMapper modelMapper;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// UserDetailsService impl
	public UserDetails loadUserByUsername(final String email) {
		AccountEntity user = accountRepo.findByEmail(email);
		if (user == null) {
			throw new AccountDefineException.NotFound(null, email, null);
		}

		return new org.springframework.security.core.userdetails.User(
						user.getEmail(),
						user.getPwd(),
						getAuthority(user.getPermissions())
		);
	}

	// UserDetailsService impl
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

	public AccountEntity getAccount(HttpServletRequest httpRequest, final Long users_sid) {
		return Optional.ofNullable(getBySid(users_sid))
				.orElseThrow(() -> new AccountDefineException.NotFound(users_sid, null,httpRequest));
	}

	public AccountEntity getAccount(HttpServletRequest httpRequest, final String users_email) {
		return Optional.ofNullable(getByEmail(users_email))
				.orElseThrow(() -> new AccountDefineException.NotFound(null, users_email, httpRequest));
	}

	public AccountEntity getBySid(final Long users_sid) {
		Optional<AccountEntity> user = accountRepo.findById(users_sid);
		if (!user.isPresent()) {
			return null;
		}
		return user.get();
	}
	
	public AccountEntity getByEmail(final String users_email) {
		AccountEntity user = accountRepo.findByEmail(users_email);
		return user;
	}

}
