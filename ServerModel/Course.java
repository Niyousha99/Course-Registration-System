package serverModel;

import java.util.ArrayList;

/**
 * Provides data fields and methods to create a Java-type, representing a Course in a Student Course Registration System.
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class Course {
	/**
	 * Course name
	 */
	private String courseName;
	
	/**
	 * Course number
	 */
	private int courseNum;

	/**
	 * ArrayList of Course offerings
	 */
	private ArrayList<CourseOffering> offeringList;

	/**
	 * Constructs a Course object supplied by the given parameters
	 * 
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		offeringList = new ArrayList<CourseOffering>();
	}

	/**
	 * Adds offering to Course offeringList
	 * @param offering - offering to be added
	 */
	public void addOffering(CourseOffering offering) {
		if (offering != null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				return;
			}
			offeringList.add(offering);
		}
	}

	/**
	 * Adds Course for specific Student and section number supplied by the given parameter
	 * @param theStudent - the Student
	 * @param secNum - Course section number
	 * @return status of Course being added  
	 */
	public boolean addCourse(Student theStudent, int secNum) { 
		boolean status = false; 
		for (CourseOffering i: offeringList) {
			if (i.getSecNum() == secNum && i.addCourse(theStudent)) 
					status = true; 
			}
		return status;
	}
	
	/**
	 * Lists Courses of Student with information supplied by the given parameters
	 * @param name - Student's first name
	 * @param id - Student's 3-digit Id number
	 * @return s - String containing Course information
	 */
	public String listStudentCourses(String name, int id) {
		String s = "";
		for(CourseOffering i: offeringList) {
			s += i.listStudentCourses(name, id);
		}
		return s;
	}
	
	/**
	 * Remove Course for specific Student and section number supplied by the given parameter
	 * @param theStudent - the Student
	 * @param secNum - Course section number
	 * @return status of Course being removed
	 */
	public boolean removeStudentCourse(Student theStudent, int secNum) {
		boolean status = false;
		for (CourseOffering i: offeringList) {
			if(i.getSecNum() == secNum)
				status = i.removeStudentCourse(theStudent);
		}
		return status;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	
	public ArrayList <CourseOffering> getCourseOffering() {
		return this.offeringList;
	}
	
	public CourseOffering getCourseOfferingAt(int i) {
		if (i < 0 || i >= offeringList.size() )
			return null;
		else
			return offeringList.get(i);
	}
	
	public String output() {
		return this.toString();
	}	
		
	@Override
	public String toString () {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum ();
		for (CourseOffering c : offeringList)
			st += c.toString();
		st += "\n-------\n";
		return st;
	}
}
