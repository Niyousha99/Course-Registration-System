package clientController;

import clientView.*;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 * Provides data fields and methods to represents a Java data-type, representing a Client GUI Controller which displays multiple frames and 
 * information to the Client user in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class GUIController {
	/**
	 * mainFrame is the main frame (i.e. first frame displayed)
	 */
	private MainFrame mainFrame;
	
	/**
	 * adminFrame is the frame for the administrator
	 */
	private AdminFrame adminFrame;
	
	/**
	 * adminViewAllCoursesFrame is the frame for the administrator to view all Courses
	 */
	private AdminViewAllCoursesFrame adminViewAllCoursesFrame;
	
	/**
	 * studentLoginFrame is the frame for the student to login
	 */
	private StudentLoginFrame studentLoginFrame;
	
	/**
	 * studentMainFrame is the main frame for the Student
	 */
	private StudentMainFrame studentMainFrame;
	
	/**
	 * searchCourseFrame is the frame for the Student to search for a Course
	 */
	private SearchCourseFrame searchCourseFrame;
	
	/**
	 * viewAllCoursesFrame is the frame for the Student to view all Courses
	 */
	private ViewAllCoursesFrame viewAllCoursesFrame;
	
	/**
	 * viewStudentCoursesFrame is the frame for the Student to view Student's registered Courses
	 */
	private ViewStudentCoursesFrame viewStudentCoursesFrame;
	
	/**
	 * courseDisplayFrame is the frame which displays Course information
	 */
	private CourseDisplayFrame courseDisplayFrame;
	
	/**
	 * comController is the Client communication controller
	 */
	private ComController comController;
	
	/**
	 * studentName is the first name of the logged in Student
	 */
	private String studentName;
	
	/**
	 * studentId is the 3-digit id number of the logged in Student
	 */
	private int studentId;
	
	/**
	 * Constructs a GUIController object and also constructs a ComController object.
	 * Then calls method initMainFrame.
	 */
	public GUIController(){
		comController = new ComController();
		initMainFrame();
	}

	/**
	 * Initializes the main frame by constructing a MainFrame object and setting it to visible.
	 * Then calls method mainFrameEventHandler.
	 */
	private void initMainFrame() {
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		mainFrameEventHandler();
	}
	
	/**
	 * Initializes the administrator frame by constructing an AdminFrame object and setting it to visible.
	 * Then calls method adminFrameEventHandler.
	 */
	private void initAdminFrame() {
		adminFrame = new AdminFrame();
		adminFrame.setVisible(true);
		adminFrameEventHandler();
	}
	
	/**
	 * Initializes the Student login frame by constructing a StudentLoginFrame object and setting it to visible.
	 * Then calls method studentLoginFrameEventHandler.
	 */
	private void initStudentLoginFrame() {
		studentLoginFrame = new StudentLoginFrame();
		studentLoginFrame.setVisible(true);
		studentLoginFrameEventHandler();
	}

	/**
	 * Initializes the Student main Frame by constructing a StudentMainFrame object.
	 * Then calls method studentMainFrameEventHandler.
	 */
	private void initstudentMainFrame() {
		studentMainFrame = new StudentMainFrame();
		studentMainFrameEventHandler();
	}
	
	/**
	 * Sets the frame supplied by the first parameter to visible and sets the frame supplied by the second parameter
	 * to not visible.
	 * 
	 * @param visibleFrame is the frame to be set to visible
	 * @param invisibleFrame is the frame to be set to not visible
	 */
	private void setVisibleFrame(JFrame visibleFrame, JFrame invisibleFrame) {
		visibleFrame.setVisible(true);
		invisibleFrame.setVisible(false);
	}
	
	/**
	 * Switches back to the desired frame supplied by the first parameter by disposing the frame supplied by the second parameter 
	 * and setting the desired frame to visible.
	 * 
	 * @param desiredFrame is the frame to be displayed
	 * @param disposeFrame is the frame to be disposed
	 */
	private void switchFrame(JFrame desiredFrame, JFrame disposeFrame) {
		disposeFrame.dispose();
		desiredFrame.setVisible(true);
	}
	
	/**
	 * Waits and responds to events in mainFrame through lambda expressions.
	 */
	private void mainFrameEventHandler() {
		mainFrame.getContinueButton().addActionListener((ActionEvent e) ->{
			if (verifyAction(verifyAdminKey())) {
				mainFrame.dispose();
				initAdminFrame();
			}
			else
				mainFrame.displayErrorMessage("Invalid admin key");
		});
		
		mainFrame.getStudentLoginButton().addActionListener((ActionEvent e) ->{
			mainFrame.dispose();
			initStudentLoginFrame();
		});
	}
	
	/**
	 * Waits and responds to events in adminFrame through lambda expressions.
	 */
	private void adminFrameEventHandler() {
		adminFrame.getAddCourseButton().addActionListener((ActionEvent e) ->{
			adminFrame.courseAction();
			String added = addCourse(adminFrame.getCourseName(), 
					adminFrame.getCourseNum());
			if(verifyAction(added))
				adminFrame.displayMessage("Course added successfully!");
			else
				adminFrame.displayMessage(added);
		});	
		
		adminFrame.getRemoveCourseButton().addActionListener((ActionEvent e) ->{
			adminFrame.courseAction();
			String removed = removeCourse(adminFrame.getCourseName(), 
					adminFrame.getCourseNum());
			if(verifyAction(removed))
				adminFrame.displayMessage("Course removed successfully!");
			else
				adminFrame.displayMessage(removed);
		});	
		
		adminFrame.getViewAllCoursesButton().addActionListener((ActionEvent e) ->{
			adminViewAllCoursesFrame = new AdminViewAllCoursesFrame();
			String courses = getCourseCatalogue();
			adminViewAllCoursesFrame.setTextArea(courses);
			setVisibleFrame(adminViewAllCoursesFrame, adminFrame);
			adminViewAllCoursesFrameEventHandler();
		});	
		
		adminFrame.getLogoutButton().addActionListener((ActionEvent e) ->{
			switchFrame(mainFrame, adminFrame);
		});	
	}
	
	/**
	 * Waits and responds to events in adminViewAllCoursesFrame through lambda expressions.
	 */
	private void adminViewAllCoursesFrameEventHandler() {
		adminViewAllCoursesFrame.getReturnToAdminFrameButton().addActionListener((ActionEvent e) ->{
			switchFrame(adminFrame, adminViewAllCoursesFrame);
		});	
	}
	
	private void studentLoginFrameEventHandler() {
		studentLoginFrame.getLoginButton().addActionListener((ActionEvent e) ->{
			setStudent(studentLoginFrame.getName(), studentLoginFrame.getId());

			if(verifyAction(searchStudent(studentName, studentId))){
				initstudentMainFrame();
				studentMainFrame.setInstructionMessage(studentName);
				switchFrame(studentMainFrame, studentLoginFrame);
				
			}else {
				studentLoginFrame.displayErrorMessage("Invalid!");
			}			
		});	
	}

	/**
	 * Waits and responds to events in studentMainFrame through lambda expressions.
	 */
	private void studentMainFrameEventHandler() {
				
		studentMainFrame.getViewAllCoursesButton().addActionListener((ActionEvent e) ->{
			viewAllCoursesFrame = new ViewAllCoursesFrame();
			viewAllCoursesFrame.createSouthPanel();
			String courses = getCourseCatalogue();
			viewAllCoursesFrame.setTextArea(courses);
			setVisibleFrame(viewAllCoursesFrame, studentMainFrame);
			viewAllCoursesEventHandler();
		});
				
		studentMainFrame.getViewStudentCoursesButton().addActionListener((ActionEvent e) ->{
			String studentCourses = getStudentCourses();
			viewStudentCoursesFrame = new ViewStudentCoursesFrame();
			
			if(studentCourses.isBlank()) 
				studentCourses = studentName + " is not enrolled in any courses yet!";
			
			viewStudentCoursesFrame.setTextArea(studentCourses);
			setVisibleFrame(viewStudentCoursesFrame, studentMainFrame);
			viewStudentCoursesEventHandler();
		});
		
		studentMainFrame.getLogoutButton().addActionListener((ActionEvent e) ->{
			switchFrame(mainFrame, studentMainFrame);
		});
	}
	
	/**
	 * Waits and responds to events in viewAllCoursesFrame through lambda expressions.
	 */
	private void viewAllCoursesEventHandler() {
		viewAllCoursesFrame.getReturnToMainButton().addActionListener((ActionEvent e) ->{
			switchFrame(studentMainFrame, viewAllCoursesFrame);
		});
		
		viewAllCoursesFrame.getSearchCourse().addActionListener((ActionEvent e) ->{
			searchCourseFrame = new SearchCourseFrame();
			setVisibleFrame(searchCourseFrame, viewAllCoursesFrame);
			searchCourseFrameEventHandler();
		});

	}
	
	/**
	 * Waits and responds to events in viewStudentCoursesFrame through lambda expressions.
	 */
	public void viewStudentCoursesEventHandler() {
		viewStudentCoursesFrame.getReturnToMainButton().addActionListener((ActionEvent e) ->{
			switchFrame(studentMainFrame, viewStudentCoursesFrame);
		});
	}
	
	/**
	 * Waits and responds to events in seachCourseFrame through lambda expressions.
	 */
	private void searchCourseFrameEventHandler() {
		searchCourseFrame.getReturnToMainButton().addActionListener((ActionEvent e) ->{
			setVisibleFrame(viewAllCoursesFrame, searchCourseFrame);
		});
				
		searchCourseFrame.getOkButton().addActionListener((ActionEvent e) ->{
			while(!verifyCourse(searchCourse(searchCourseFrame.getCourseName(), searchCourseFrame.getCourseNum()))) {
				searchCourseFrame.displayMessage("Course does not exist!");
				return;
			}
			courseDisplayFrame = new CourseDisplayFrame();
			courseDisplayFrame.setCourseTextArea(searchCourseFrame.getCourseName(), searchCourseFrame.getCourseNum());
			setVisibleFrame(courseDisplayFrame, searchCourseFrame);
			courseDisplayEventHandler();
		});
	}

	/**
	 * Waits and responds to events in the courseDisplayFrame through lambda expressions.
	 */
	public void courseDisplayEventHandler() {
		courseDisplayFrame.getReturnToSearchCourseButton().addActionListener((ActionEvent e) ->{
			setVisibleFrame(searchCourseFrame, courseDisplayFrame);
		});
		
		courseDisplayFrame.getAddCourseButton().addActionListener((ActionEvent e) ->{
			String added = addStudentCourse(courseDisplayFrame.getCourseName(), courseDisplayFrame.getCourseNum(), courseDisplayFrame.getSecNum());
			if(verifyAction(added))
				courseDisplayFrame.displayMessage("Course added successfully!");
			else {
				courseDisplayFrame.displayMessage(added); // error message from addCourse on server end sent as string
				setVisibleFrame(searchCourseFrame, courseDisplayFrame);
			}
			setVisibleFrame(viewAllCoursesFrame, courseDisplayFrame);
		});
		
		courseDisplayFrame.getRemoveCourseButton().addActionListener((ActionEvent e) ->{
			String removed = removeStudentCourse(courseDisplayFrame.getCourseName(), courseDisplayFrame.getCourseNum(), courseDisplayFrame.getSecNum());
			if (verifyAction(removed))
				courseDisplayFrame.displayMessage("Course removed successfully!");
			else {
				courseDisplayFrame.displayMessage(removed); // error message from addCourse on server end sent as string
				setVisibleFrame(searchCourseFrame, courseDisplayFrame);
			}
			setVisibleFrame (viewAllCoursesFrame, courseDisplayFrame);
		});
	}

	/**
	 * Calls method searchCourse in class ComController with the supplied parameters.
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @return message from searchCourse in ComController
	 */
	private String searchCourse(String courseName, String courseNum) {
		return comController.searchCourse(courseName, courseNum);
	}

	/**
	 * Calls method addStudentCourse in class ComController with the supplied parameters.
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @param secNum is the Course section number
	 * @return message from addStudentCourse in ComController
	 */
	private String addStudentCourse(String courseName, String courseNum, String secNum) {
		return comController.addStudentCourse(studentName, studentId, courseName, courseNum, secNum);
	}
	
	/**
	 * Calls method removeStudentCourse in class ComController with the supplied parameters.
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @param secNum is the Course section number
	 * @return message from removeStudentCourse in ComController
	 */
	private String removeStudentCourse(String courseName, String courseNum, String secNum) {
		return comController.removeStudentCourse(studentName, studentId, courseName, courseNum, secNum);
	}
	
	/**
	 * Calls method getCourseCatalogue in class ComController with the supplied parameters.
	 * 
	 * @return message from getCourseCatalogue in ComController
	 */
	private String getCourseCatalogue() {
		return comController.getCourseCatalogue();
	}
	
	/**
	 * Calls method getStudentCourses in class ComController with the supplied parameters.
	 * 
	 * @return message from getStudentCourses in ComController
	 */
	private String getStudentCourses() {
		return comController.getStudentCourses(studentName, studentId);
	}
	
	/**
	 * Calls method searchStudent in class ComController with the supplied parameters.
	 * 
	 * @param studentName is the first name of the Student
	 * @param studentId is the 3-digit id of the Student
	 * @return message from searchStudent in ComController
	 */
	private String searchStudent(String studentName, int studentId) {
		return comController.searchStudent(studentName, studentId);
	}

	/**
	 * Calls method verifyAdminKey in class ComController with the supplied parameters.
	 * 
	 * @return message from verifyAdminKey in ComController
	 */
	private String verifyAdminKey() {
		return comController.verifyAdminKey(mainFrame.getAdminKey());
	}
	
	/**
	 * Calls method addCourse in class ComController with the supplied parameters.
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @param secNum is the Course section number
	 * @return message from addCourse in ComController
	 */
	private String addCourse(String courseName, String courseNum) {
		return comController.addCourse(courseName, courseNum);
	}
	
	/**
	 * Calls method removeCourse in class ComController with the supplied parameters.
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @param secNum is the Course section number
	 * @return message from removeCourse in ComController
	 */
	private String removeCourse(String courseName, String courseNum) {
		return comController.removeCourse(courseName, courseNum);
	}
	
	/**
	 * Verifies a Course.
	 * 
	 * @param searchCourse is the Course information
	 * @return true if the Course information is not null. Otherwise returns false.
	 */
	private boolean verifyCourse(String searchCourse) {
		if (searchCourse == null)
			return false;
		else
			return true;
	}
	
	/**
	 * Verifies action performed on Server side.
	 * 
	 * @param valid is the message from the Server
	 * @return true if message is valid. Otherwise returns false.
	 */
	private boolean verifyAction(String valid) {
		if (valid.equalsIgnoreCase("valid"))
				return true;
		else
			return false;
	}
	
	/**
	 * Sets Student credentials to the values supplied by the given parameters.
	 * 
	 * @param studentName is the first name of the Student
	 * @param studentId is the 3-digit id number of the Student
	 */
	private void setStudent(String studentName, int studentId) {
		this.studentName = studentName;
		this.studentId = studentId;
	}
	
	public static void main(String []args) {
		GUIController GUI = new GUIController();
	}
}
