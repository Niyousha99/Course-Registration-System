package serverModel;

/**
 * Provides data fields and methods to create a Java-type, representing a Registration in a Student Course Registration System.
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class Registration {
	/**
	 * The Student
	 */
	private Student theStudent;
	
	/**
	 * The Course Offering
	 */
	private CourseOffering theOffering;
	
	/**
	 * Completes Registration of a Student in a Course Offering supplied by the given parameters
	 * @param st - the Student
	 * @param of - the Course Offering
	 */
	public void completeRegistration (Student st, CourseOffering of) {
		theStudent = st;
		theOffering = of;
		addRegistration();
	}
	
	/**
	 * Adds Registration to the Student and Course Offering
	 */
	private void addRegistration () {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	
	/**
	 * Lists Courses of Student whose information is supplied by the given parameters
	 * @param name - Student's first name
	 * @param id - Student's 3-digit Id number
	 * @return s - String containing Student Courses
	 */
	public String listStudentCourses(String name, int id) {
		String s = "";
		if (validateStudent(name, id)) {
			s += theOffering.getTheCourse().getCourseName() + " " + theOffering.getTheCourse().getCourseNum();
			s += ": Section " + theOffering.getSecNum() + "\n";
		}
		return s;							
	}
	
	/**
	 * Validates Student whose information is supplied by the given parameters
	 * @param name - Student's first name
	 * @param id - Student's 3-digit Id number
	 * @return true if Student is valid and false otherwise
	 */
	public boolean validateStudent(String name, int id) {
		if (this.theStudent.getStudentName().equals(name) && 
				this.theStudent.getStudentId() == id) {
			return true;
		}
		else 
			return false;
	}

	/**
	 * Validates Course whose information is supplied by the given parameters
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 * @return true if Course is valid and false otherwise
	 */
	public boolean validateCourse(String courseName, int courseNum) {
		if (this.theOffering.getTheCourse().getCourseName() == courseName &&
				this.theOffering.getTheCourse().getCourseNum() == courseNum)
			return true;
		else
			return false;
	}

	public Student getTheStudent() {
		return theStudent;
	}
	
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	
	@Override
	public String toString () {
		String st = "\n";
		st += "Student: " + getTheStudent().toString() + "\n";
		st += "The Offering: " + getTheOffering().toString() + "\n";
		st += "\n-----------\n";
		return st;	
	}
}
