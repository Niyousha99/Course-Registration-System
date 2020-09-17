package clientView;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Provides data fields and methods to create a Java data-type, representing a GUI administrator frame in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class AdminFrame extends JFrame{
	/**
	 * Components of the frame
	 */
	private Container c;
	private JTextField courseName, courseNum;
	private JButton addCourse, removeCourse, viewAllCourses, logout;
	private JTextArea instructions;
	private JPanel pNorth, pSouth;
	
	/**
	 * Constructs an AdminFrame object by initializing the frame's components
	 */
	public AdminFrame() {
		super("Admin Page");
		setBounds(400, 350, 700, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// North Panel
		pNorth = new JPanel();
		pNorth.setLayout(new GridLayout(1, 0));
		instructions = new JTextArea();
		instructions.setEditable(false);
		instructions.setFont(new Font("Arial", Font.BOLD, 15));
		instructions.setLineWrap(true);
		String instructionsMessage = " To add a course, click on Add course.\n To "
				+ "remove a course, click on Remove Course.\n To view all "
				+ "courses, click on View All Courses: ";
		instructions.setText(instructionsMessage);
		pNorth.add(instructions);
		c.add("North", pNorth);	
		
		// South Panel
		pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout());
		addCourse = new JButton("Add Course");
		removeCourse = new JButton("Remove Course");
		viewAllCourses = new JButton("View All Courses");
		logout = new JButton ("Logout");
		pSouth.add(addCourse);
		pSouth.add(removeCourse);
		pSouth.add(viewAllCourses);
		pSouth.add(logout);
		c.add("South", pSouth);
		
		pack();
	}
	
	/**
	 * Creates a dialog box to prompt administrator user to enter Course information
	 * @return Course information entered by user
	 */
	public String courseAction() {
		courseName = new JTextField(15);
		courseNum = new JTextField(15);
		JPanel coursePanel = new JPanel(new GridLayout(3,2));
		
		coursePanel.add(new JLabel("Course Name:"));
		coursePanel.add(courseName);
		coursePanel.add(new JLabel("Course Number:"));
		coursePanel.add(courseNum);
		
		int result = JOptionPane.showConfirmDialog(null, coursePanel, 
				"Enter Course Information", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) 
			return courseName.getText() + " " + courseNum.getText();
		
		return null;
	}
	
	/**
	 * Displays message supplied by the given parameter in a dialog box
	 * @param message is the message to display
	 */
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public JButton getAddCourseButton() {
		return addCourse;
	}
	
	public JButton getRemoveCourseButton() {
		return removeCourse;
	}
	
	public JButton getViewAllCoursesButton() {
		return viewAllCourses;
	}
	
	public JButton getLogoutButton() {
		return logout;
	}
	
	public String getCourseName() {
		return courseName.getText();
	}
	
	public String getCourseNum() {
		return courseNum.getText();
	}
}
