
import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

/**
 * 
 * @author mao
 *
 */
public class Global extends GlobalSettings {
    
	/**
	 * Run on application boot.
	 */
    public void onStart(Application app) {
        InitialData.insert(app);
    }
    
    static class InitialData {
    	/**
    	 * Import initial YML data into database.
    	 * 
    	 * @param this Application instance for this application.
    	 */
        public static void insert(Application app) {
            if(Ebean.find(User.class).findRowCount() == 0) {
                
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");

                // Insert users first
                Ebean.save(all.get("user"));
            }
        }
    }
}
