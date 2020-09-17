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
import javax.swing.SwingConstants;

/**
 * Provides data fields and methods to create a Java data-type, representing a GUI Student main frame in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class StudentMainFrame extends JFrame{
	/**
	 * Components of the frame
	 */
	private Container c;
	private JPanel pCenter, pSouth;
	private JButton viewAllCourses, viewStudentCourses, logout;
	private JLabel labelCenter;
	private JTextArea instructions;
	
	/**
	 * Constructs a StudentMainFrame object
	 */
	public StudentMainFrame() {
		// Main Frame
		super("Student Course Registration System");
		setBounds(350, 200, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// South Panel
		pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout());
		viewAllCourses = new JButton("View All Courses");
		viewStudentCourses = new JButton("View My Courses");
		logout = new JButton ("logout");
		pSouth.add(viewAllCourses);
		pSouth.add(viewStudentCourses);
		pSouth.add(logout);
		c.add("South", pSouth);
		
		// Center Panel
		pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(2, 0));
	}
	
	/**
	 * Creates a welcome message including the Student's name supplied by the given parameter.
	 * Then sets the instructions text area to include both the welcome message and the instructions for the Student.
	 * Then adds the text area to the frame.
	 * 
	 * @param studentName is the Student user's first name
	 */
	public void setInstructionMessage(String studentName) {
		// Center Panel Continued
		labelCenter = new JLabel("Welcome " + studentName + "!");
		labelCenter.setFont(new Font("Arial", Font.BOLD, 30));
		labelCenter.setHorizontalAlignment(SwingConstants.CENTER);
		pCenter.add(labelCenter);
		instructions = new JTextArea();
		instructions.setEditable(false);
		instructions.setFont(new Font("Arial", Font.PLAIN, 20));
		instructions.setLineWrap(true);
		String welcome = "To view all available courses, click on View All Courses.\n"
				+ "To view all courses you are registered in, click on View My Courses.";
		instructions.setText(welcome);
		pCenter.add(instructions);
		c.add("Center", pCenter);
	}
	
	/**
	 * Displays message supplied by the given parameter in a dialog box
	 * @param message is the message to display
	 */
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public JButton getViewAllCoursesButton() {
		return viewAllCourses;
	}
	
	public JButton getViewStudentCoursesButton() {
		return viewStudentCourses;
	}
	
	public JButton getLogoutButton() {
		return logout;
	}
}
