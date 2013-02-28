package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import java.util.List;
import java.util.ArrayList;

/**
 * Competence entity managed by Ebean
 */
@Entity
@Table(name = "competence")
public class Competence extends Model {
	
	@Id
	public long competence_id;

	@Constraints.Required(message="Required")
	@Formats.NonEmpty
	public String name;

    /**
     * Generic query helper for entity Competence by name
     */
	public static Model.Finder<String, Competence> find = new Model.Finder(String.class, Competence.class);

    /**
     * Return a list of all Competences.
     * 
     * @return List <Competence>
     */
	public static List<Competence> getAll() {
		return find.all();
	}

    /**
     * Generic query helper for entity Competence with id Long
     */
	public static Competence findById(long id) {
		return find.where().eq("competence_id", id).findUnique();
	}

	@Override
	public String toString() {
		return name;
	}

    /**
     * Return a list of Strings with the names of all Competences.
     * 
     * @return List <Competence>
     */
	public static List<String> getList() {
		List<String> ret = new ArrayList<String>();
		for(Competence comp: getAll()) {
			ret.add(Long.toString(comp.competence_id));
		}
		return ret;
	}

}
