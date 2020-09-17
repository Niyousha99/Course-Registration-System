package clientController;

/**
 * Provides data fields to be implemented by class ComController
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public interface Constants {
	/**
	 * Host name
	 */
	static final String HOST = "localhost";
	
	/**
	 * Port number
	 */
	static final int PORT = 9090;
	
	/**
	 * Command keys to be sent to the Server
	 */
	static final String SEARCHCOURSE = "1";
	static final String ADDSTUDENTCOURSE = "2";
	static final String REMOVESTUDENTCOURSE = "3";
	static final String GETCOURSECATALOGUE = "4";
	static final String GETSTUDENTCOURSES = "5";
	static final String SEARCHSTUDENT = "6";
	static final String VERIFYADMINKEY = "7";
	static final String ADDCOURSE = "8";
	static final String REMOVECOURSE = "9";
	
	/**
	 * Keywords for types data to receive from Server
	 */
	static final int RECEIVEMESSAGE = 1;
	static final int RECEIVECOURSES = 2;
	static final int RECEIVESPECIFIEDCOURSE = 3;
}
