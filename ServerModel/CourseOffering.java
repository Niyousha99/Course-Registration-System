package serverModel;

import java.util.*;

/**
 * Provides data fields and methods to create a Java-type, representing a Course offering in a Student Course Registration System.
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class CourseOffering {
	/**
	 * Course section number
	 */
	private int secNum;
	
	/**
	 * Course section capacity
	 */
	private int secCap;
	
	/**
	 * Course Section Id in database
	 */
	private int sqlCourseKeyId;
	
	/**
	 * The Course
	 */
	private Course theCourse;
	
	/**
	 * ArrayList of Course offering Registrations
	 */
	private ArrayList <Registration> offeringRegList;
	
	/**
	 * Constructs a CourseOffering object with values supplied by the given parameters
	 * @param theCourse - the Course
	 * @param secNum - Course section number
	 * @param secCap - Course section capacity
	 * @param sqlCourseKeyId - Course Id in database
	 */
	public CourseOffering (Course theCourse, int secNum, int secCap, int sqlCourseKeyId) {
		this.setTheCourse(theCourse);
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		this.setSqlCourseKeyId(sqlCourseKeyId);
		offeringRegList = new ArrayList <Registration>();
	}
	
	/**
	 * Adds Course Registration for Student supplied by the given parameter 
	 * @param theStudent - the Student
	 * @return true if Student Registration is valid and returns false otherwise
	 */
	public boolean addCourse(Student theStudent) {
		Registration newReg = new Registration();
		newReg.completeRegistration(theStudent, this);
		
		if (newReg.validateStudent(theStudent.getStudentName(), theStudent.getStudentId()))
			return true;
		else
			return false;
	}

	/**
	 * Adds Registration to offeringRegList
	 * @param registration - Student registration in this Course offering
	 */
	public void addRegistration(Registration registration) {
		offeringRegList.add(registration);
	}
	
	/**
	 * Lists Courses of Student whose information is supplied by the given parameters
	 * @param name - Student's first name
	 * @param id - Student's 3-digit Id number 
	 * @return s - String containing Student's registered Courses
	 */
	public String listStudentCourses(String name, int id) {
		String s = "";
		for (Registration i: offeringRegList) {
			s += i.listStudentCourses(name, id);
		}
		return s;
	}
	
	/**
	 * Removed Course for Student supplied by the given parameter
	 * @param theStudent - the Student
	 * @return status of removing Student Course
	 */
	public boolean removeStudentCourse(Student theStudent){
		boolean status = false;
		Registration index = null;
		
		for (Registration i: offeringRegList) {
			if(i.validateStudent(theStudent.getStudentName(), theStudent.getStudentId())){
				index = i;
			}
		}
		
		if (index != null) {
			offeringRegList.remove(index);
			status = true;
		}
		return status;
	}
	
	public int getSqlCourseKeyId() {
		return sqlCourseKeyId;
	}

	public void setSqlCourseKeyId(int sqlCourseKeyId) {
		this.sqlCourseKeyId = sqlCourseKeyId;
	}

	public int getSecNum() {
		return secNum;
	}
	
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	
	public int getSecCap() {
		return secCap;
	}
	
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}
	
	public Course getTheCourse() {
		return theCourse;
	}
	
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
		
	@Override
	public String toString () {
		String st = "\n";
		st += "Section Number: " + getSecNum() + ", Section Capacity: "+ getSecCap() +"\n";
		return st;
	}
}
