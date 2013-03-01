package models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.i18n.Messages;

/**
 * Availability entity managed by Ebean
 */
@Entity
@Table(name = "availability")
public class Availability extends Model{
	
	@Id
	@Formats.NonEmpty
	public Long availability_id;
	
	@Formats.NonEmpty
	@Constraints.Required
	@ManyToOne
	@JoinColumn(name="person_id")
	public User person_id;  
	
	@Formats.NonEmpty
    @Formats.DateTime(pattern="yyyy-MM-dd")
	@Constraints.Required()
    public Date from_date;
    
	@Formats.NonEmpty
    @Formats.DateTime(pattern="yyyy-MM-dd")
	@Constraints.Required()
    public Date to_date;
    
    /**
     * Generic query helper for entity Availability with id Long
     */
    public static Model.Finder<Long, Availability> find = new Model.Finder(Long.class, Availability.class);
    
    /**
     * Find Availability by id
     * 
     * @param id
     * @return Availability
     */
	public static Availability findById(Long id) {
		return find.where().eq("availability_id", id).findUnique();
	}
	
	/**
	 * Validate Availability.
	 * 
	 * @return null if validation is successful, else error message is returned.
	 */
	public static String validate(Date to_date, Date from_date) {
		if(from_date == null)
			return "from date fail";
		else if(to_date == null)
			return "to date fail";
		else
			return null;
	}
	
	public String toString() {
		return new SimpleDateFormat("yyyy-MM-dd").format(from_date) + " " + Messages.get("to") + " " + new SimpleDateFormat("yyyy-MM-dd").format(to_date);
	}


}
