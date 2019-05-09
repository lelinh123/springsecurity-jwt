package rmhub.user.management.model;

public enum RoleName {
ROLE_ADMIN(1), ROLE_DISPATCHER(2), ROLE_USER(3);
  
  int value;
  RoleName(int value) {
    this.value = value;
  }
}
