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
 * Provides data fields and methods to create a Java data-type, representing a GUI frame to view all Courses in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class ViewAllCoursesFrame extends JFrame{
	/**
	 * Components of the frame
	 */
	protected Container c;
	protected JPanel pCenter, pSouth;
	protected JTextArea textArea;
	private JButton returnToMain, searchCourse;
	protected JScrollPane scrollPanel;
	
	/**
	 * Constructs a ViewAllCoursesFrame object
	 */
	public ViewAllCoursesFrame() {
		super("View All Courses");
		setBounds(350, 200, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
	}

	/**
	 * Creates the south panel and adds it to the frame
	 */
	public void createSouthPanel() {
		pSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		returnToMain = new JButton("Return");
		searchCourse = new JButton("Search Course");
		pSouth.add(returnToMain);
		pSouth.add(searchCourse);
		c.add("South", pSouth);
	}
	
	/**
	 * Sets textArea to the list of Courses supplied as a String in the given parameter.
	 * Then adds textArea to the frame.
	 * 
	 * @param courses is the list of Courses
	 */
	public void setTextArea(String courses) {
		pCenter = new JPanel();
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.BOLD, 20));
		textArea.setEditable(false);
		pCenter.add(textArea);
		textArea.setText(courses);
		textArea.setLineWrap(false);
		scrollPanel = new JScrollPane(textArea);
		c.add(scrollPanel, BorderLayout.CENTER);
	}
	
	public JButton getReturnToMainButton() {
		return returnToMain;
	}
	
	public JButton getSearchCourse() {
		return searchCourse;
	}
}
