package rmhub.user.management.service.command;

import rmhub.user.management.model.User;

public interface IUserCommandService {
  User createUser(User newUser);
  User updateUser(String name, String role, String engineeringBureau);
  boolean valiadteUpdateUser(String name, String role, String engineeringBureau);
  boolean valiateNewUser(User newUser);
  boolean validateEmailAddress(String emailAddress);
}
