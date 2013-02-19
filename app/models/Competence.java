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
import java.util.ArrayList;

@Entity
@Table(name = "competence")
public class Competence extends Model {
	
	@Id
	public int competence_id;

	@Constraints.Required(message="Required")
	@Formats.NonEmpty
	public String name;

	public static Model.Finder<String, Competence> find = new Model.Finder(String.class, Competence.class);

    /**
     * Return a list of all Competences.
     * 
     * @return List of all Competences.
     */
	public static List<Competence> getAll() {
		return find.all();
	}

	public static Competence findById(int id) {
        return find.where().eq("competence_id", id).findUnique();
	}

	@Override
	public String toString() {
		return name;
	}

	public static List<String> getList() {
		List<String> ret = new ArrayList<String>();
		for(Competence comp: getAll()) {
			ret.add(Integer.toString(comp.competence_id));
		}
		return ret;
	}

}
