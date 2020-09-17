package serverModel;

import java.util.ArrayList;

/**
 * Provides data fields and methods to create a Java-type, representing a Student in a Student Course Registration System.
 * 
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class Student implements Constants{
	/**
	 * Student first name
	 */
	private String studentName;
	
	/**
	 * Student 3-digit Id number
	 */
	private int studentId;
	
	/**
	 * ArrayList of Student Registrations
	 */
	private ArrayList<Registration> studentRegList;
	
	/**
	 * Constructs a Student object with values supplied by the given parameters 
	 * @param studentName - Student first name
	 * @param studentId - Student 3-digit Id number
	 */
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
	}
	
	/**
	 * Checks if registration is valid by calling method validateReg and adds registration to studentRegList 
	 * @param registration - new registration
	 */
	public void addRegistration(Registration registration) {
		if (validateReg())
			this.studentRegList.add(registration);
	}

	/**
	 * Validates new Registration by checking if studentRegList exceeds maximum number of Courses
	 * @return true if new Registration is valid and false otherwise
	 */
	private boolean validateReg() {
		if (studentRegList.size() < MAXCOURSES)
			return true;
		else
			return false;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}	
	
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}
	
	public void updateFromDB(ArrayList<Registration> regList) {
		studentRegList = regList;
	}
	
	@Override
    public boolean equals(Object o) {   
		if (o == this)  return true; 

		if (o instanceof Student) return true; 
       		  
		return false;
	}
}
