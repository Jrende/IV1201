package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity 
@Table(name="account")
public class User extends Model{
	
    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public Long person_id;
    
    @Constraints.Required
    public String username;
    
    @Constraints.Required
    public String email;
    
    @Constraints.Required
    public String surname;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public Role role;
    
    public String ssn;
    
    @Constraints.Required
    public Integer birthdate;
    
    
    public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);

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

}
