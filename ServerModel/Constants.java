package serverModel;

/**
 * Provides data fields to be implemented by the Server
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public interface Constants {
	/**
	 * Maximum number of Courses that a Student may register in
	 */
	static final int MAXCOURSES = 6;
	
	/**
	 * Minimum number of registrations in a Course 
	 */
	static final int MINREG = 8;
	
	/**
	 * Default number of sections per Course
	 */
	static final int NUMSECTIONS = 4;
	
	/**
	 * Capacity of Registrations in a Course section
	 */
	static final int CAPACITY = 100;
}
