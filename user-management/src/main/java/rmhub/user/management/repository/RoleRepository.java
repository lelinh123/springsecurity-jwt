package rmhub.user.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rmhub.user.management.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}
