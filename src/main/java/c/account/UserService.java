package c.account;

import java.util.Collections;

import javax.annotation.PostConstruct;

import m.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import c.beerSources.AddonUsingTime;
import c.beerSources.AddonUsingTimeRepository;

public class UserService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AddonUsingTimeRepository addonUsingTimeRepository;
	@PostConstruct
	public void initialize() {
//		Account acc1 = new Account("user", "demo", "ROLE_USER");
//		Account acc2 = new Account("admin", "admin", "ROLE_ADMIN");
//		accountRepository.save(acc1);
//		accountRepository.save(acc2);	

//		AddonUsingTime add1 = new AddonUsingTime("Warzenie");
//		AddonUsingTime add2 = new AddonUsingTime("Chmielenie");
//		AddonUsingTime add3 = new AddonUsingTime("Fermentacja");
//		addonUsingTimeRepository.save(add1);
//		addonUsingTimeRepository.save(add2);
//		addonUsingTimeRepository.save(add3);
		
		m.gpio.GPIO.INSTANCE.initPins();
		Settings.readSettingsFromFile();
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if (account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(
				authenticate(account));
	}

	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account),
				null, Collections.singleton(createAuthority(account)));
	}

	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(),
				Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
