package serverController;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import serverModel.*;

/**
 * Provides data fields and methods to create a Java-type, representing a Server Database Controller in a Student Course Registration System.
 *
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class DBController implements IDBCredentials, Constants{
	/**
	 * Connection to the database
	 */
	private Connection conn;
	
	/**
	 * Cursor in a database result set
	 */
	private ResultSet rs;
	
	private Student theStudent;
	
	/**
	 * Constructs a DBController object by calling methods initializeConnection() and init()
	 */
	public DBController() {
		initializeConnection();
		init();
	}

	/**
	 * Calls corresponding methods to create each table in database
	 */
	private void init() {
		createStudentTable();
		createCourseTable();
		createRegistrationTable();
	}
	
	/**
	 * Initializes connection with database
	 */
	private void initializeConnection(){
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Closes connection to database
	 */
	private void close() {
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates table STUDENT in database
	 */
	private void createStudentTable() {
		String sql = "CREATE TABLE STUDENT " + "(id INTEGER not NULL, " + " name "
				+ "VARCHAR(255), " + " PRIMARY KEY ( id ))";
		try {
			Statement stmt = conn.createStatement(); 
			stmt.executeUpdate(sql); 
			stmt.close();
			populateStudentTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates table COURSE in database
	 */
	private void createCourseTable() {
		String sql = "CREATE TABLE COURSE " + " (id INTEGER not NULL, " + " name VARCHAR(255), " + 
					 "sec INTEGER not NULL, " + " PRIMARY KEY ( id ))";
		try {
			Statement stmt = conn.createStatement(); 
			stmt.executeUpdate(sql); 
			stmt.close();
			populateCourseTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates table REGISTRATION in database
	 */
	private void createRegistrationTable() {
		String sql = "CREATE TABLE REGISTRATION " + " (id INTEGER not NULL, " + " studentId INTEGER not NULL, " 
				+ "courseId INTEGER not NULL, " + " PRIMARY KEY ( id ))";
		try {
			Statement stmt = conn.createStatement(); 
			stmt.executeUpdate(sql); 
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Populates table STUDENT using method insertStudent
	 */
	private void populateStudentTable() {
		insertStudent(123, "John");
		insertStudent(343, "Mary");
		insertStudent(345, "Niyousha");
		insertStudent(987, "Dunsin");
		insertStudent(453, "Francis");
		insertStudent(278, "Joy");
		insertStudent(145, "Prince");
		insertStudent(943, "Helen");
	}
	
	/**
	 * Populates arraylist of Student with values in table STUDENT from database
	 * @return studentList - arraylist of Student
	 */
	public ArrayList <Student> populateStudentListFromTable() {
		ArrayList <Student> studentList = new ArrayList <Student>();
		try {
			String query = "SELECT * FROM STUDENT";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				studentList.add(new Student(rs.getString("name"), rs.getInt("id")));
			}
			
			for(Student s: studentList) {
				updateStudent(s);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	}
	
	/**
	 * Updates Student's course registrations using method setRegistration
	 * @param theStudent - the Student user
	 */
	public void updateStudent(Student theStudent) {
		ArrayList<Registration> studentRegList = new ArrayList<Registration>();
		setTheStudent(theStudent);
		try {
			String query = "SELECT * FROM REGISTRATION";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				if(rs.getInt("studentId") == theStudent.getStudentId()) {
					int keyId = rs.getInt("courseId");
					String regDetails[] = getCourse(keyId).split(" ");
					if(regDetails.length == 3) {
						studentRegList.add(setRegistration(keyId, regDetails[0], Integer.parseInt(regDetails[1]), 
								Integer.parseInt(regDetails[2])));
					} 
				}
			}
			theStudent.updateFromDB(studentRegList);
			
		}catch (SQLException e) {
		e.printStackTrace();
		}
	}
	
	/**
	 * Creates new Student Registration with the Course specifications supplied by the given parameters
	 * 
	 * @param keyId - Registration ID (i.e. primary key in REGISTRATION table)
	 * @param courseName - Course name
	 * @param courseNum - Course number
	 * @param secNum - Course section number
	 * @return reg - new Registration
	 */
	private Registration setRegistration(int keyId, String courseName, int courseNum, int secNum) {
		Registration reg = new Registration();
		reg.setTheStudent(theStudent);
		Course c = new Course(courseName, courseNum);
		CourseOffering cOffer = new CourseOffering(c, secNum, CAPACITY, keyId);		
		c.addOffering(cOffer);
		reg.setTheOffering(cOffer);
		theStudent.addRegistration(reg);
		cOffer.addRegistration(reg);
		return reg;
	}					
	
	/**
	 * Gets Course by selecting entry in table COURSE whose primary key matches the id supplied by the 
	 * given parameter
	 * 
	 * @param id - Course id (i.e. primary key in COURSE table)
	 * @return course - Course information
	 */
	private String getCourse(int id) {
		String course = "";
		try {
			String query = "SELECT * FROM COURSE";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				if(rs.getInt("id") == id) {
					course = rs.getString("name") + " " + rs.getInt("sec");
				}
			}
		}catch (SQLException e) {
		e.printStackTrace();
		}
		
		return course;
	}
	
	/**
	 * Inserts new entry in table STUDENT 
	 * 
	 * @param studentId - Student id (i.e. the primary key)
	 * @param studentName - Student first name
	 */
	private void insertStudent(int studentId, String studentName) {
		if(studentAlreadyExists(studentId)) return;
		try {
			String query = "INSERT INTO STUDENT (id, name) values (?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, studentId);
			pStat.setString(2, studentName);
			pStat.execute();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if Student entry already exists in table STUDENT
	 * 
	 * @param studentId - Student id (i.e. the primary key)
	 * @return true if studentId is found in table Student and returns false otherwise.
	 */
	private boolean studentAlreadyExists(int studentId) {
		try {
			String query = "SELECT * FROM STUDENT";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt("id") == studentId)
					return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Populates table COURSE using method insertCourse
	 */
	private void populateCourseTable() {
		
		insertCourse("ENGG 233", NUMSECTIONS);
		insertCourse("ENSF 409", NUMSECTIONS);
		insertCourse("PHYS 259", NUMSECTIONS);
		insertCourse("ENGG 209", NUMSECTIONS);
		insertCourse("ENGG 213", NUMSECTIONS);
		insertCourse("CHEM 209", NUMSECTIONS);
		insertCourse("MATH 211", NUMSECTIONS);
		insertCourse("MATH 275", NUMSECTIONS);
		insertCourse("MATH 277", NUMSECTIONS);
		insertCourse("ENGG 201", NUMSECTIONS);
		insertCourse("ENGG 200", NUMSECTIONS);
		insertCourse("ENGG 225", NUMSECTIONS);
		insertCourse("ENGG 202", NUMSECTIONS);
	}
	
	/**
	 * Inserts new Course entry in table COURSE with the given parameters
	 * 
	 * @param courseName - Course name 
	 * @param courseSec - Course section
	 * @return true if Course successfully added to table and false otherwise
	 */
	private boolean insertCourse(String courseName, int courseSec) {
		if(courseAlreadyExists(courseName)) return false;
		for (int i = 0; i < courseSec; i++) {
			insertCourseSection(generateId(), courseName, i);
		}
		return true;
	}
	
	/**
	 * Adds a Course with values supplied by the given parameters by calling method insertCourse
	 * @param courseName - Course name
	 * @param courseSec - Course section
	 * @return boolean return value of method insertCourse
	 */
	public boolean addCourse(String courseName, int courseSec) {
		return insertCourse(courseName, courseSec);
	}
	
	/**
	 * Checks if a Course entry already exists in table COURSE 
	 * 
	 * @param courseName - Course name
	 * @return - true if courseName is found in table Course and returns false otherwise
	 */
	private boolean courseAlreadyExists(String courseName) {
		try {
			String query = "SELECT * FROM COURSE";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				if (rs.getString("name").equals(courseName))
					return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Generates an incremented Id number
	 * @return generatedId - Id number
	 */
	private int generateId() {
		int generatedId = 1;
		while (doesIdExist(generatedId)) {
			generatedId ++;
		}
		return generatedId;
	}
	
	/**
	 * Checks if generated Id number already exists in table COURSE
	 * @param generatedId - Id number
	 * @return - true if generatedId is found in COURSE and returns false otherwise
	 */
	private boolean doesIdExist(int generatedId) {
		try {
			String query = "SELECT * FROM COURSE";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt("id") == generatedId)
					return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Inserts Course section into table COURSE with values supplied by the given parameters
	 * 
	 * @param courseId - Course Id number (i.e. the primary key)
	 * @param coursename - Course name
	 * @param secNum - Course section number
	 */
	private void insertCourseSection(int courseId, String courseName, int secNum) {
		try {
			String query = "INSERT INTO COURSE (id, name, sec) values (?,?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, courseId);
			pStat.setString(2, courseName);
			pStat.setInt(3, secNum);
			pStat.execute();
			pStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes Course from table COURSE by finding matching Course id to the Course name supplied by
	 * the given parameter and calling method removeCourse
	 * @param courseName - Course name
	 * @return true if Course removed successfully and returns false otherwise
	 */
	public boolean removeCourse(String courseName) {
		try {
			String query = "SELECT * FROM COURSE";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				if(rs.getString("name").equals(courseName)) {
					removeCourse(rs.getInt("id"));
				}
			}
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Removed course from table COURSE matching the Course Id supplied by the given parameter
	 * @param courseId - Course Id (i.e. the primary key)
	 */
	private void removeCourse(int courseId) {
		String query = "DELETE FROM COURSE where id = ?";
		try {
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, courseId);
			pStat.execute();
			pStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Populates arrayList of Course with entries in table COURSE
	 * @return courseList - populated Course arrayList
	 */
	public ArrayList <Course> populateCourseListFromTable() {
		ArrayList <Course> courseList = new ArrayList <Course>();
		Course c = null;
		try {
			String query = "SELECT * FROM COURSE";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			int sectionOffering, sqlId;
			String initialCourse;
			
			while (rs.next()) {
				initialCourse = rs.getString("name");
				String[] courseVals = initialCourse.split(" ");
				sectionOffering = rs.getInt("sec");
				sqlId = rs.getInt("id");
				
				if(courseVals.length == 2) {
					c = new Course(courseVals[0], Integer.parseInt(courseVals[1]));	
					c.addOffering(new CourseOffering(c, sectionOffering, CAPACITY, sqlId));
				}
				
				int checker = 0;
				for(Course course: courseList) {
					if(course.getCourseName().equals(courseVals[0]) && course.getCourseNum() == Integer.parseInt(courseVals[1])) {
						checker++;
						course.addOffering(new CourseOffering(c, sectionOffering, CAPACITY, sqlId));
					}
				}
				
				if(checker == 0 && courseVals.length == 2) {
					courseList.add(c);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	/**
	 * Inserts a new Registration entry in table REGISTRATION with values supplied by the given parameters 
	 * 
	 * @param registrationId - Registration Id (i.e. the primary key) 
	 * @param studentId - Student Id
	 * @param courseId - Course Id
	 */
	public void insertRegistration(int registrationId, int studentId, int courseId) {
		if(doesRegExist(registrationId)) return;
		try {
			String query = "INSERT INTO REGISTRATION (id, studentId, courseId) values (?,?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, registrationId);
			pStat.setInt(2, studentId);
			pStat.setInt(3, courseId);
			pStat.execute();
			pStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removed Registration entry from table REGISTRATION matching the Registration Id supplied by 
	 * the given parameter
	 *  
	 * @param registrationId - Registration Id (i.e. the primary key)
	 */
	public void removeRegistration (int registrationId) {
		if (!doesRegExist(registrationId)) return;
		String query = "DELETE FROM REGISTRATION where id = '" + registrationId;
		try {
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, registrationId);
			pStat.execute();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if Registration exists in table REGISTRATION
	 * 
	 * @param regId - Registration Id (i.e. the primary key)
	 * @return true if regId is found in table and returns false otherwise
	 */
	private boolean doesRegExist(int regId) {
		try {
			String query = "SELECT * FROM REGISTRATION";
			PreparedStatement pStat = conn.prepareStatement(query);
			rs = pStat.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt("id") == regId)
					return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
}
