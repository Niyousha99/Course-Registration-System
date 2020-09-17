package clientView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Provides data fields and methods to create a Java data-type, representing a GUI Course display frame in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class CourseDisplayFrame extends JFrame{
	/**
	 * Components of the frame
	 */
	private Container c;
	private JPanel pNorth, pCenter, pSouth;
	private JButton returnToSearchCourse, addCourse, removeCourse;
	private String courseName, courseNum;
	private JTextField secNum;
	private JLabel courseText;
	
	/**
	 * Constructs a CourseDisplayFrame object 
	 */
	public CourseDisplayFrame() {
		super("Course Display");
		setBounds(400, 400, 600, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// Center Panel
		pCenter = new JPanel();
		pCenter.add(new JLabel("Section number: "));
		secNum = new JTextField(15);
		pCenter.add(secNum);
		c.add("Center", pCenter);
		
		// South Panel
		pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		returnToSearchCourse = new JButton("Return");
		addCourse = new JButton("Add Course");
		removeCourse = new JButton ("Remove Course");
		pSouth.add(returnToSearchCourse);
		pSouth.add(addCourse);
		pSouth.add(removeCourse);
		c.add("South", pSouth);
	}
	
	/**
	 * Sets coureText to the combined Course name and number supplied by the given parameters.
	 * Then adds courseText to the frame. 
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 */
	public void setCourseTextArea(String courseName, String courseNum) {
		// North Panel
		this.courseName = courseName;
		this.courseNum = courseNum;
		pNorth = new JPanel();
		courseText = new JLabel("Course: " + courseName + " " + courseNum);
		pNorth.add(courseText);
		c.add("North", pNorth);
		pack();
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
	
	public JButton getReturnToSearchCourseButton() {
		return returnToSearchCourse;
	}
	
	public String getSecNum() {
		return secNum.getText();
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseNum() {
		return courseNum;
	}
}
