package rmhub.user.management.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "engineering_bureau")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EngineeringBureau {
  @Id
  private int id;
  private String name;
  private Date createdDate;
  private int createdBy;
  private Date lastModifiedDate;
  private int lastModifyBy;
  private String description;
}
