package clientView;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

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
 * Provides data fields and methods to create a Java data-type, representing a GUI Student login frame in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class StudentLoginFrame extends JFrame implements DocumentListener{
	/**
	 * Components of the frame
	 */
	private Container c;
	private JTextField name, id;
	private JButton login;
	private JTextArea welcomeMessage;
	private JPanel pNorth, pSouth;
	
	/**
	 * Constructs a StudentLoginFrame object
	 */
	public StudentLoginFrame() {
		// Login Frame
		super("Student Login");
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
		String welcome = " Welcome!\n\n We're excited to have you try our Student Course" + 
								" Resgistration System! \n This tool is for planning and registration purposes" + 
								" to generate course list options. \n\n Please login with your first name and Student Id:\n";
		welcomeMessage.setText(welcome);
		pNorth.add(welcomeMessage);
		c.add("North", pNorth);
		
		// South Panel
		pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout());
		name = new JTextField(15);
		name.getDocument().addDocumentListener(this);
		id = new JTextField(15);
		id.getDocument().addDocumentListener(this);
		login = new JButton("Login");
		login.setEnabled(false);
		pSouth.add(new JLabel("Name: "));
		pSouth.add(name);
		pSouth.add(new JLabel("Student Id: "));
		pSouth.add(id);
		pSouth.add(Box.createRigidArea(new Dimension(200, 0)));
		pSouth.add(login);
		
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
	 * Checks if the name and id text fields are empty. 
	 * @return false if one of the text fields is empty. Otherwise returns true.
	 */
	private boolean isStudentInfoEntered() {
		if (name.getText().trim().length() == 0 || id.getText().trim().length() == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Enables/Disables the login button according to return value of 
	 * called method isStudentInfoEntered
	 */
	private void checkStudentLoginInfo() {
		login.setEnabled(isStudentInfoEntered());
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		checkStudentLoginInfo();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		checkStudentLoginInfo();
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}
	
	public JButton getLoginButton() {
		return login;
	}
	
	public JTextField getNameField() {
		return name;
	}
	
	public JTextField getIdField() {
		return id;
	}
	
	public String getName() {
		return name.getText();
	}
	
	public int getId() {
		return Integer.parseInt(id.getText());
	}


}
