package rmhub.user.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import rmhub.user.management.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    @Async
    @Query(value = "select * from users u where u.login_name = ?1 ",  nativeQuery = true)
    User findByLoginName(String loginName);
    
    @Async
    @Query(value = "select * from users u where u.email = ?1 ",  nativeQuery = true)
    User findByEmail(String email);
}

