package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;

import play.db.ebean.Model;

@Entity
@Table(name = "competenceProfile")
public class CompetenceProfile extends Model {
	
	@Id
	@Formats.NonEmpty
	public int competence_profile_id;
	
	@Formats.NonEmpty
	@Constraints.Required
	@ManyToOne
	@JoinColumn(name="person")
	public User person;
	
	
	@Formats.NonEmpty
	@Constraints.Required
	@OneToOne
	public Competence competence;
	
	@Formats.NonEmpty
	@Constraints.Required
	public float years_of_experience;

	public static Model.Finder<String, CompetenceProfile> find = new Model.Finder(String.class, CompetenceProfile.class);
	
	public static CompetenceProfile findById(int id) {
		return find.where().eq("competence_profile_id", id).findUnique();
	}

	public String validate() {
		return null;
	}
}
