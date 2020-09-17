package clientView;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Provides data fields and methods to create a Java data-type, representing a GUI frame for an administrator to view all courses in a 
 * Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class AdminViewAllCoursesFrame extends ViewAllCoursesFrame {
	/**
	 * Component of the frame
	 */
	private JButton returnToAdminFrame;
	
	/**
	 * Constructs an AdminViewAllCoursesFrame object by invoking the constructor 
	 * of superclass ViewAllCoursesFrame and calls method createSouthPanel
	 */
	public AdminViewAllCoursesFrame() {
		super();
		createSouthPanel();
	}
	
	/**
	 * Adds another component to the frame
	 */
	public void createSouthPanel(){
		pSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		returnToAdminFrame = new JButton("Return");
		pSouth.add(returnToAdminFrame);
		c.add("South", pSouth);
	}
	
	public JButton getReturnToAdminFrameButton() {
		return returnToAdminFrame;
	}
}
