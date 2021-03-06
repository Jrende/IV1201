package models;

/**
 * Description of a User role within the application.
 *
 */
public enum Role {
  Recruiter,
  Applicant;
  
  /**
   * Get Role from ordinal number.
   * 
   * @param ordinal - Enum ordinal representation of Role
   * @return Role representation of ordinal
   */
  static public Role getRole(int ordinal) {
	  return Role.values()[ordinal];
  }
}
