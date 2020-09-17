package clientView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Provides data fields and methods to create a Java data-type, representing a GUI main frame in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class MainFrame extends JFrame implements DocumentListener{
	/**
	 * Components of the frame
	 */
	private Container c;
	private JTextField adminKey;
	private JButton adminContinue, studentLogin;
	private JTextArea welcomeMessage;
	private JPanel pNorth, pSouth;
	
	/**
	 * Constructs a MainFrame object
	 */
	public MainFrame() {
		super("Student Course Registration System");
		setBounds(400, 350, 700, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// North Panel
		pNorth = new JPanel();
		pNorth.setLayout(new GridLayout(1, 0));
		welcomeMessage = new JTextArea();
		welcomeMessage.setEditable(false);
		welcomeMessage.setFont(new Font("Arial", Font.BOLD, 15));
		welcomeMessage.setLineWrap(true);
		String welcome = " Welcome!\n\n If you are an administrator, please enter your "
				+ "Admin Key and click on Continue.\n If you are a student, please "
				+ "click on Student Login:";
		welcomeMessage.setText(welcome);
		pNorth.add(welcomeMessage);
		c.add("North", pNorth);
		
		// South Panel
		pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout());
		adminKey = new JTextField(15);
		adminKey.getDocument().addDocumentListener(this);
		pSouth.add(new JLabel("Admin Key: "));
		pSouth.add(adminKey);
		adminContinue = new JButton("Continue");
		adminContinue.setEnabled(false);
		pSouth.add(adminContinue);
		pSouth.add(Box.createRigidArea(new Dimension(200, 0)));
		studentLogin = new JButton("Student Login");
		pSouth.add(studentLogin);
		c.add("South", pSouth);
		
		pack();
	}
	
	/**
	 * Displays message supplied by the given parameter in a dialog box
	 * @param message is the message to display
	 */
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	/**
	 * Checks if the adminKey text field is empty. 
	 * @return false if adminKey is empty. Otherwise returns true.
	 */
	private boolean isAdminKeyEntered() {
		if (adminKey.getText().trim().length() == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Enables/Disables the adminContinue button according to return value of 
	 * called method isAdminKeyEntered
	 */
	private void checkAdminKey() {
		adminContinue.setEnabled(isAdminKeyEntered());
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		checkAdminKey();		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		checkAdminKey();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}
	
	public JButton getStudentLoginButton() {
		return studentLogin;
	}
	
	public JButton getContinueButton() {
		return adminContinue;
	}
	
	public String getAdminKey() {
		return adminKey.getText();
	}
}
