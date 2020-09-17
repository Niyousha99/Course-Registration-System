package serverModel;

/**
 * Provides data fields and methods to create a Java-type, representing a Registration App in a Student Course Registration System.
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class RegistrationApp {
	/**
	 * Course Catalogue
	 */
	private CourseCatalogue catCourse;
	
	/**
	 * Database Manager
	 */
	private DBManager dataBase;
	
	/**
	 * Constructs a RegistrationApp object by invoking the constructors of DBManager and CourseCatalogue
	 */
	public RegistrationApp() {
		dataBase = new DBManager();
		catCourse = new CourseCatalogue(dataBase);
	}
	
	/**
	 * Searches Course Catalogue for Course with information supplied by the given parameters
	 * 
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 * @return Course if found in Course Catalogue and null otherwise
	 */
	public String searchCatCourses(String courseName, int courseNum) {
		Course c = this.catCourse.searchCat(courseName, courseNum);
		if(c == null) return null;
		return c.output();
	}

	/**
	 * Adds Course Registration for Student supplied by the given parameters by calling method addCourse
	 * in class CourseCatalogue
	 *  
	 * @param studentName - Student's first name
	 * @param studentId - Student's 3-digit Id number
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 * @param secNum - Course section number
	 * @return String containing status of adding Student Course
	 */
	public String addCourse(String studentName, int studentId, String courseName, int courseNum, int secNum) {
		
		Student theStudent = dataBase.findStudent(studentName, studentId);
		Course theCourse = dataBase.findCourse(courseName, courseNum);
		
		if (theStudent != null && theCourse != null && this.catCourse.addCourse(theStudent, theCourse, secNum)) {
			return "valid";
		}

		return "Student course could not be added. Please try again.\n";
	}

	/**
	 * Lists Courses for Student whose information is supplied in the given parameters by calling method 
	 * listStudentCourses in class CourseCatalogue
	 * @param name - Student's first name
	 * @param id - Student's 3-digit Id number
	 * @return String containing Student Courses
	 */
	public String listStudentCourses(String name, int id) {
		return this.catCourse.listStudentCourses(name, id);
	}

	/**
	 * Removes Student Course supplied by the given parameters by calling method removeStudentCourse in class 
	 * CourseCatalogue
	 * @param studentName - Student's first name
	 * @param studentId - Student's 3-digit Id number
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 * @param secNum - Course section number
	 * @return String containing status of removing Student Course
	 */
	public String removeStudentCourse(String studentName, int studentId, String courseName, int courseNum, int secNum) {
		
		Student theStudent = dataBase.findStudent(studentName, studentId);
		Course theCourse = dataBase.findCourse(courseName, courseNum);
		
		if (theStudent != null && theCourse != null && this.catCourse.removeStudentCourse(theStudent, theCourse, secNum))
			return "valid";

		
		return "Student course could not be removed. Please try again.\n";
	}

	/**
	 * Lists all Courses by calling method listAllCourses in class CourseCatalogue
	 * @return String containing all Courses
	 */
	public String listAllCourses() {
		return catCourse.listAllCourses();
	}
	
	public DBManager getDataBase(){
		return dataBase;
	}

	public CourseCatalogue getCat() {
		return catCourse;
	}
}
