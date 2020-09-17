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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Provides data fields and methods to create a Java data-type, representing a GUI frame to search for a Courses in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class SearchCourseFrame extends JFrame implements DocumentListener{
	/**
	 * Components of the frame
	 */
	private Container c;
	private JPanel pCenter, pSouth;
	private JButton returnToMain, ok;
	private JTextField courseName, courseNum;
	
	/**
	 * Constructs a SearchCourseFrame object
	 */
	public SearchCourseFrame() {
		super("Select Courses");
		setBounds(400, 400, 600, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// Center Panel
		pCenter = new JPanel();
		pCenter.setLayout(new FlowLayout());
		courseName = new JTextField(15);
		courseName.getDocument().addDocumentListener(this);
		pCenter.add(new JLabel("Course Name: "));
		pCenter.add(courseName);
		courseNum = new JTextField(15);
		courseNum.getDocument().addDocumentListener(this);
		pCenter.add(new JLabel("Course Number: "));
		pCenter.add(courseNum);
		c.add("Center", pCenter);
		
		// South Panel
		pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));
		returnToMain = new JButton("Return");
		ok = new JButton("Ok");
		ok.setEnabled(false);
		pSouth.add(returnToMain);
		pSouth.add(ok);
		c.add("South", pSouth);

		pack();
	}
	
	/**
	 * Displays message supplied by the given parameter in a dialog box
	 * @param message is the message to display
	 */
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	/**
	 * Checks if the courseName and courseNum text fields are empty. 
	 * @return false if either text field is empty. Otherwise returns true.
	 */
	private boolean isCourseInfoEntered() {
		if (courseName.getText().trim().length() == 0 || courseNum.getText().trim().length() == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Enables/Disables the ok button according to return value of 
	 * called method isCourseInfoEntered
	 */
	private void checkCourseInformation() {
		ok.setEnabled(isCourseInfoEntered());
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		checkCourseInformation();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		checkCourseInformation();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}
	
	public JButton getReturnToMainButton() {
		return returnToMain;
	}
	
	public JButton getOkButton() {
		return ok;
	}
	
	public String getCourseName() {
		return courseName.getText();
	}
	
	public String getCourseNum() {
		return courseNum.getText();
	}
}
