package rmhub.user.management.service.command.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import rmhub.user.management.exception.BusinessException;
import rmhub.user.management.model.EngineeringBureau;
import rmhub.user.management.model.Role;
import rmhub.user.management.model.RoleName;
import rmhub.user.management.model.User;
import rmhub.user.management.repository.BureauRepository;
import rmhub.user.management.repository.RoleRepository;
import rmhub.user.management.repository.UserRepository;
import rmhub.user.management.service.command.IUserCommandService;

public class UserCommandServiceImpl implements IUserCommandService {

  UserRepository userRepository;
  RoleRepository roleRepository;
  BureauRepository bureauRepository;
  private Pattern regexPattern;
  private Matcher regMatcher;
  @Override
  public User createUser(User newUser) {
    valiateNewUser(newUser);
    userRepository.save(newUser);
    return newUser;
  }
  @Override
  public boolean valiateNewUser(User newUser) {
    if (newUser.getLoginName().isBlank() || newUser.getEmail().isBlank() || 
      newUser.getRoles().isEmpty()) {
      throw new BusinessException("The (*) fields are mandatory fields", HttpStatus.BAD_REQUEST);
    }
    for (Role role : newUser.getRoles()) {
      if (role.getName().equals(RoleName.ROLE_USER)) {
        throw new BusinessException("Unauthoried", HttpStatus.BAD_REQUEST);
      }
    }
    validateEmailAddress(newUser.getEmail());
    return true;
  }
  
  public boolean validateEmailAddress(String emailAddress) {
    
    regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
    regMatcher   = regexPattern.matcher(emailAddress);
    if(!regMatcher.matches()) {
        throw new BusinessException("This email address does not exist", HttpStatus.BAD_REQUEST);
    } 
    return true;       
}
  @Override
  public User updateUser(String name, String roleName, String engineeringBureau) {
      valiadteUpdateUser(name, roleName, engineeringBureau);
      User user = userRepository.findByLoginName(roleName);
      user.setLoginName(name);
      user.getRoles().clear();
      Role role = roleRepository.findByName(roleName);
      user.getRoles().add(role);
      user.getEngineeringBureaus().clear();
      EngineeringBureau bureau = bureauRepository.findByName(engineeringBureau);
      user.getEngineeringBureaus().add(bureau);
      userRepository.save(user);
      return user;
  }
  @Override
  public boolean valiadteUpdateUser(String name, String roleName, String engineeringBureau) {
    if (name.isBlank() || roleName.isBlank() || engineeringBureau.isBlank()) {
      throw new BusinessException("The (*) fields are mandatory fields", HttpStatus.BAD_REQUEST);
    }
    return true;
  }

}
