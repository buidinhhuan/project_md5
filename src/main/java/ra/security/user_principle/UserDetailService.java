package ra.security.user_principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.model.domain.Users;
import ra.service.IUserService;

import java.security.Security;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userService.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("Username Not Found"));
        return UserPrinciple.build(users);
    }
    public  Users getUserFromAuthentication() {
        UserPrinciple userPrinciple= (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findById(userPrinciple.getId()).get();
    }
  }
