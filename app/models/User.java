package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import java.util.List;

@Entity 
@Table(name="account")
public class User extends Model {
	
    @Id
    @Formats.NonEmpty
    public Long person_id;
    
    @Constraints.Required
    public String username;
    
    public String email;
    
    @Constraints.Required
    public String surname;
    
    @Constraints.Required
    public String name;

    @Constraints.Required
    public String thingie;
    
//    @Constraints.Required
//    public Role role;
    
    @Constraints.Required
    public String password;
    
    @Constraints.Required
    public String ssn;
    
    
    public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);

	public User() {
		thingie = "Hej";
	}
    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String username, String password) {
        return find.where()
            .eq("username", username)
            .eq("password", password)
            .findUnique();
    }

	public static List<User> getAll() {
		return find.all();
	}

	@Override
	public String toString() {
		return username;
	}

}
