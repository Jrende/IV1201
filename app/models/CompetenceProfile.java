package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;


@Entity
@Table(name = "competence_profile")
public class CompetenceProfile {
	
	@Id
	@Formats.NonEmpty
	public long  competence_profile_id;
	
	@Formats.NonEmpty
	@Constraints.Required
	@OneToOne
	public User person_id;
	
	
	@Formats.NonEmpty
	@Constraints.Required
	@OneToOne
	public Competence competence;
	
	@Formats.NonEmpty
	@Constraints.Required
	public float years_of_experience;
	
	
	public String validate() {
		return null;
	}
}
