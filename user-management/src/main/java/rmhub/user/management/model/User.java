package rmhub.user.management.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  @Id
  private int id;
  private String loginName;
  private String password;
  private String email;
  private String phone;
  private Date createDate;
  private int createBy;
  private Date lastModifiedDate;
  private int lastModifiedBy;
  private int isActive;
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles = new HashSet<Role>();
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<EngineeringBureau> engineeringBureaus = new HashSet<EngineeringBureau>();
}
