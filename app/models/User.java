package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import java.util.List;

@Entity
@Table(name = "account")
public class User extends Model {

	@Id
	@Formats.NonEmpty
	public Long person_id;

	@Constraints.Required(message="Username is required")
	public String username;

	@Constraints.Email(message="Email is required")
	public String email;

	@Constraints.Required(message="Surname is required")
	public String surname;

	@Constraints.Required(message="Name is required")
	public String name;

	@Constraints.Required
	@Enumerated(EnumType.ORDINAL)
	public Role role = Role.Applicant;

	@Constraints.MinLength(6)
	@Constraints.Required(message="Password is required")
	public String password;

	@Constraints.MinLength(6)
	@Constraints.Required(message="Confirm password is required")
	public transient String confirmPassword;
	
	@Constraints.Required(message="Security social number is required")
	public String ssn;
	
	public static Model.Finder<String, User> find = new Model.Finder(String.class, User.class);

	public User() {
	}

	/**
	 * Retrieve a User from email.
	 */
	public static User findByEmail(String email) {
		return find.where().eq("email", email).findUnique();
	}

	/**
	 * Retrieve a User from user name.
	 */
	public static User findByUsername(String username) {
		return find.where().eq("username", username).findUnique();
	}

	/**
	 * Authenticate a User.
	 */
	public static User authenticate(String username, String password) {
		return find.where().eq("username", username).eq("password", password)
				.findUnique();
	}

	public static List<User> getAll() {
		return find.all();
	}

	@Override
	public String toString() {
		return username;
	}

	public String validate() {
		if (findByEmail(email) != null)
			return "Email already registered";
		
		if (findByUsername(username) != null)
			return "Username already registered";
		
		if (!password.equals(confirmPassword))
			return "Your password and confirmation password don't match";

		return null;
	}
}
