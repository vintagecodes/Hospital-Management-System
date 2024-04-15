package sec.model;

import java.util.HashSet;
import java.util.Set;



import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";
  @Id
  private String userId;
  @NotBlank
  @Size(max = 20)
  private String username;
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  @NotBlank
  @Size(max = 120)
  private String password;
  private Set<Role> roles = new HashSet<>();
  
  public User(String userId, String username, String email, String password) {
	  	this.userId = userId;
	    this.username = username;
	    this.email = email;
	    this.password = password;
	  }
  

}