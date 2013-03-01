package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;

import play.data.format.Formats;
import play.data.validation.Constraints;

import play.db.ebean.Model;


/**
 * CompenteceProfile entity managed by Ebean
 */
@Entity
@Table(name = "competence_profile")
public class CompetenceProfile extends Model {
	
	@Id
	@Formats.NonEmpty
	public long competence_profile_id;
	
	@Formats.NonEmpty
	@Constraints.Required
	@ManyToOne
	@JoinColumn(name="person")
	@Column(name="person_id")
	public User person;
	
	
	@Formats.NonEmpty
	@Constraints.Required
	@OneToOne
	@Column(name="competence_id")
	public Competence competence;
	
	@Formats.NonEmpty
	@Constraints.Required
	public float years_of_experience;

    /**
     * Generic query helper for entity CompetenceProfile
     */
	public static Model.Finder<String, CompetenceProfile> find = new Model.Finder(String.class, CompetenceProfile.class);
	
    /**
     * Generic query helper for entity CompetenceProfile with id Long
     */
	public static CompetenceProfile findById(long id) {
		return find.where().eq("competence_profile_id", id).findUnique();
	}

	
	public String validate() {
		return null;
	}
}
