package clientView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Provides data fields and methods to create a Java data-type, representing a GUI frame to view Student's registered Courses in a Student 
 * Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class ViewStudentCoursesFrame extends JFrame {
	/**
	 * Components of the frame
	 */
	private Container c;
	private JTextArea textArea;
	private JPanel pSouth, pCenter;
	private JButton returnToMain;
	private JScrollPane scrollPanel;
	
	/**
	 * Constructs a ViewStudentCoursesFrame object
	 */
	public ViewStudentCoursesFrame() {
		super("View My Courses");
		setBounds(400, 350, 700, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// South Panel
		pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		returnToMain = new JButton("Return");
		pSouth.add(returnToMain);
		c.add("South", pSouth);
	}

	/**
	 * Sets textArea to the list of Student Courses supplied as a String in the given parameter.
	 * Then adds textArea to the frame.
	 * 
	 * @param studentCourses is the list of the Student's registered Courses
	 */
	public void setTextArea(String studentCourses) {
		pCenter = new JPanel();
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.BOLD, 15));
		textArea.setEditable(false);
		pCenter.add(textArea);
		textArea.setText(studentCourses);
		textArea.setLineWrap(false); // may not need
		scrollPanel = new JScrollPane(textArea);
		c.add(scrollPanel, BorderLayout.CENTER);
	}
	
	public JButton getReturnToMainButton() {
		return returnToMain;
	}
}
