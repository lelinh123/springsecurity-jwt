package rmhub.user.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rmhub.user.management.model.EngineeringBureau;

public interface BureauRepository extends JpaRepository<EngineeringBureau, Integer> {
 EngineeringBureau findByName(String name);
}
