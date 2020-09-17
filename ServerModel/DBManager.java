package serverModel;

import java.util.ArrayList;

import serverController.DBController;

/**
 * Provides data fields and methods to create a Java-type, representing a Database Manager in a Student Course Registration System.
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class DBManager implements Constants {
	/**
	 * ArrayList of Courses
	 */
	ArrayList <Course> courseList;
	
	/**
	 * ArrayList of Students
	 */
	ArrayList <Student> studentList;
	
	/**
	 * Database Controller
	 */
	private DBController dbController;

	/**
	 * Constructs a DBManager object by invoking the constructor of DBController and calling method Init
	 */
	public DBManager () {
		dbController = new DBController();
		init();
	}
	
	/**
	 * Initializes studentList and courseList by calling methods populateStudentListFromTable and populateCourseListFromTable
	 * respectively from class DBController
	 */
	private void init() {
		studentList = dbController.populateStudentListFromTable();
		courseList = dbController.populateCourseListFromTable();
	}
	
	/**
	 * Searches for Student whose information is supplied by the given parameters
	 * @param studentName - Student's first name
	 * @param studentId - Student's 3-digit Id number
	 * @return Student if found and null otherwise
	 */
	public Student findStudent(String studentName, int studentId) {
		for (Student s: studentList) {
			if (s.getStudentName().equalsIgnoreCase(studentName) && s.getStudentId() == studentId)
				return s;
		}
		
		return null;
	}
	
	/**
	 * Searches for Course with matching information as supplied in the given parameters
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 * @return Course if found and null otherwise
	 */
	public Course findCourse(String courseName, int courseNum) {
		for (Course c: courseList) {
			if (c.getCourseName().equalsIgnoreCase(courseName) && c.getCourseNum() == courseNum)
				return c;
		}
		return null;
	}
	
	/**
	 * Reads Students from database using method populateStudentListFromTable in class DBController and
	 * stores copy of Students from database to studentList
	 */
	public void updateStudentListFromDataBase() {
		studentList = dbController.populateStudentListFromTable();	
	}
	
	/**
	 * Reads Courses from database using method populateCourseListFromTable in class DBController and
	 * stores copy of Courses from database to courseList
	 */
	public void updateCourseListFromDataBase() {
		courseList = dbController.populateCourseListFromTable();
	}
	
	/**
	 * Calls method updateStudentListFromDataBase and returns studentList
	 * @return studentList - arrayList of Students
	 */
	public ArrayList<Student> getStudentListFromDataBase() {
		updateStudentListFromDataBase();
		return studentList;
	}
	
	/**
	 * Calls method updateCourseListFromDataBase and returns courseList
	 * @return courseList - arrayList of Courses
	 */
	public ArrayList<Course> getCourseListFromDataBase() {
		updateCourseListFromDataBase();
		return courseList;
	}

	public DBController getDBController() {
		return dbController;
	}
}
