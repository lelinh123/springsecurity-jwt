package rmhub.user.management.service.query.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rmhub.user.management.exception.BusinessException;
import rmhub.user.management.model.Role;
import rmhub.user.management.model.User;
import rmhub.user.management.repository.UserRepository;
import rmhub.user.management.service.query.IUserQueryService;

@Service
public class UserQueryServiceImpl implements IUserQueryService {

  @Autowired
  UserRepository userRepository;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmail(email);
    // User user = userRepository.findByLoginName(loginName);
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    Set<Role> roles = user.getRoles();
    for (Role role : roles) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().toString()));
    }
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonlocked = true;
    UserDetails userDetails =
        new org.springframework.security.core.userdetails.User(email, user.getPassword(), enabled,
            accountNonExpired, credentialsNonExpired, accountNonlocked, grantedAuthorities);
    return userDetails;
  }



  // @Override
  // public boolean checkLogin(User user) {
  //
  // List<User> listUsers = userRepository.findAll();
  // for (User userExist : listUsers) {
  // if (StringUtils.equals(user.getLoginName(), userExist.getLoginName())
  // && StringUtils.equals(user.getPassword(), userExist.getPassword())) {
  // return true;
  // }
  // }
  // return false;
  // }
  @Override
  public boolean checkLogin(String email, String password) {
    // if (email.isBlank()) {
    // throw new BusinessException("Email must be fulfilled", HttpStatus.BAD_REQUEST);
    // }
    // User checkUser = null;
    // checkUser = userRepository.findByEmail(email.trim());
    // if (checkUser == null) {
    // throw new BusinessException("The email is wrong. Check it again, please.",
    // HttpStatus.NOT_FOUND);
    // } else {
    // if (password.isBlank()) {
    // throw new BusinessException("Password must be fulfilled", HttpStatus.BAD_REQUEST);
    // }
    // if (StringUtils.equals(checkUser.getPassword(), password)) {
    // return true;
    // } else {
    // throw new BusinessException("The password is wrong. Check it again, please.",
    // HttpStatus.NOT_FOUND);
    // }
    // }
    validateEmail(email);
    validatePassword(password);
    User checkUser = null;
    checkUser = userRepository.findByEmail(email.trim());
    if (null == checkUser) {
      throw new BusinessException("The email is wrong. Check it again, please.",
          HttpStatus.NOT_FOUND);
    }

    if (StringUtils.equals(checkUser.getPassword(), password)) {
      return true;
    } else {
      throw new BusinessException("The password is wrong. Check it again, please.",
          HttpStatus.NOT_FOUND);
    }

  }

  public boolean validateEmail(String email) {
    if (email.isBlank()) {
      throw new BusinessException("Email must be fulfilled", HttpStatus.BAD_REQUEST);
    }
    return true;
  }

  public boolean validatePassword(String password) {
    if (password.isBlank()) {
      throw new BusinessException("Password must be fulfilled", HttpStatus.BAD_REQUEST);
    }
    return true;
  }

  @Override
  public User findById(int id) {
    return userRepository.findById(id).get();
  }
}
