package serverModel;

import java.util.ArrayList;

/**
 * Provides data fields and methods to create a Java-type, representing a Course Catalogue in a Student Course Registration System.
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class CourseCatalogue implements Constants{
	/**
	 * ArrayList of Courses
	 */
	private ArrayList <Course> courseList;
	
	/**
	 * Database Manager
	 */
	DBManager dBase;
	
	/**
	 * Constructs a CourseCatalogue object with the supplied database given in the parameter
	 * @param dataBase - The database containing Students, Courses, and Registrations
	 */
	public CourseCatalogue (DBManager dataBase) {
		setCourseList(dataBase.getCourseListFromDataBase());
		dBase = dataBase;
	}
	
	/**
	 * Adds Course Registration for Student supplied by the given parameters
	 * 
	 * @param theStudent - the Student
	 * @param theCourse - the Course
	 * @param secNum - Course section number
	 * @return status of adding Student Course
	 */
	public boolean addCourse(Student theStudent, Course theCourse, int secNum) {
		boolean status = false;
		if (validateSecNum(secNum)) {
			for (Course i: courseList) {
				if (i.equals(theCourse))
					if (i.addCourse(theStudent, secNum))
						status = true;
			}
		}
		return status;
	}
	
	/**
	 * Removes Student Course supplied by the given parameters
	 * @param theStudent - the Student
	 * @param theCourse - the Course
	 * @param secNum - Course section number
	 * @return status of removing Student Course
	 */
	public boolean removeStudentCourse(Student theStudent, Course theCourse, int secNum) {
		boolean status = false;
		if (theCourse != null) {
			if (theCourse.removeStudentCourse(theStudent, secNum)) {
				status = true;
			}
		}		
		return status;
	}
	
	/**
	 * Validates section number by checking if supplied section number in the given parameter
	 * exceeds the maximum number of section per Course
	 * @param secNum - Course section number
	 * @return true is secNum is valid and returns false otherwise
	 */
	private boolean validateSecNum(int secNum) {
		if (secNum > 0 && secNum <= NUMSECTIONS)
			return true;
		else {
			return false;
		}
	}

	/**
	 * Searches Course Catalogue for Course with information supplied by the given parameters
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 * @return Course if found in Course Catalogue and null otherwise
	 */
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		return null;
	}
	
	/**
	 * Updates Course Catalogue by calling method getCourseListFromDataBase in class DBManager
	 */
	public void updateCourseCatalogue() {
		setCourseList(dBase.getCourseListFromDataBase());
	}
	
	/**
	 * Lists Courses for Student whose information is supplied in the given parameters 
	 * @param name - Student's first name
	 * @param id - Student's 3-digit Id number
	 * @return String containing Student Courses
	 */
	public String listStudentCourses(String name, int id) {
		String s = "";
		for (Course i: courseList) {
			s += i.listStudentCourses(name, id);
		}
		return s;
	}

	/**
	 * Lists all Courses by calling method toString
	 * @return String containing all Courses
	 */
	public String listAllCourses() {
		return this.toString();
	}
	
	public ArrayList <Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}
	
	@Override
	public String toString () {
		String st = "";
		for (Course c : courseList) {
			st += c.toString(); 
			st += " ";
		}
		return st;
	}
}
