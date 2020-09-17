package clientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Provides data fields and methods to create a Java-type, representing a Client communication controller which sends commands and data 
 * to the Server in a Student Course Registration System.
 * 
 * @author Niyousha Raeesinejad
 * @author Dunsin Shitta-Bey
 */
public class ComController implements Constants{
	/**
	 * Socket connecting Server to Client
	 */
	private Socket socket;
	
	/**
	 * Output socket to send commands/data to Server
	 */
	private PrintWriter socketOut;
	
	/**
	 * Input socket to receive data from Server
	 */
	private BufferedReader socketIn;

	/**
	 * Constructs a ComController object and establishes a connection to the Server by initializing socket, socketIn, and socketOut.
	 */
	public ComController() {
		try {
//			InetAddress addr = InetAddress.getByName("10.0.0.44");
//			socket = new Socket("192.168.1.77", PORT, addr, PORT);
			socket = new Socket(HOST, PORT);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter (socket.getOutputStream(), true);
		} catch (IOException e) { 
			e.printStackTrace();
		}catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: SEARCHCOURSE - commands the Server to search for a specified Course
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVESPECIFIEDCOURSE 
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @return data requested from Server
	 */
	public String searchCourse(String courseName, String courseNum) {
		return communicateWithServer(SEARCHCOURSE, courseName + " " + courseNum, RECEIVESPECIFIEDCOURSE);
	}
	
	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: ADDSTUDENTCOURSE - commands the Server to add a specified Student's Course
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVEMESSAGE
	 * 
	 * @param studentName is the Student first name
	 * @param studentId is the Student 3-digit id
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @param secNum is the Course section number
	 * @return data requested from Server
	 */
	public String addStudentCourse(String studentName, int studentId, String courseName, String courseNum, String secNum) {	
		return communicateWithServer(ADDSTUDENTCOURSE, studentName + " " + studentId + " " + courseName + " " + courseNum + " " + secNum, RECEIVEMESSAGE);
	}
	
	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: REMOVESTUDENTCOURSE - commands the Server to remove a specified Student's Course
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVEMESSAGE
	 * 
	 * @param studentName is the Student first name
	 * @param studentId is the Student 3-digit id
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @param secNum is the Course section number
	 * @return data requested from Server
	 */
	public String removeStudentCourse(String studentName, int studentId, String courseName, String courseNum, String secNum) {
		return communicateWithServer(REMOVESTUDENTCOURSE, studentName + " " + studentId + " " + courseName + " " + courseNum + " " + secNum, RECEIVEMESSAGE);
	}
	
	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: GETCOURSECATALOGUE - commands the Server to get the Course catalogue
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVECOURSES
	 * 
	 * @return data requested from Server
	 */
	public String getCourseCatalogue() {
		return communicateWithServer(GETCOURSECATALOGUE, "", RECEIVECOURSES);
	}
	
	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: GETSTUDENTCOURSES - commands the Server to get a specified Student's Courses
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVESPECIFIEDCOURSES
	 * 
	 * @param studentName is the Student first name
	 * @param studentId is the Student 3-digit id
	 * @return data requested from Server
	 */
	public String getStudentCourses(String studentName, int studentId) {
		return communicateWithServer(GETSTUDENTCOURSES, studentName + " " + studentId, RECEIVESPECIFIEDCOURSE);
	}
	
	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: SEARCHSTUDENT - commands the Server to search for a specified Student
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVEMESSAGE
	 * 
	 * @param studentName is the Student name
	 * @param studentId is the Student Id
	 * @return data requested from Server
	 */
	public String searchStudent(String studentName, int studentId) {
		return communicateWithServer(SEARCHSTUDENT, studentName + " " + studentId, RECEIVEMESSAGE);
	}

	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: VERIFYADMINKEY - commands the Server to verify the supplied administrator key
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVEMESSAGE
	 * 
	 * @param key is the administrator key
	 * @return data requested from Server
	 */
	public String verifyAdminKey(String key) {	
		return communicateWithServer(VERIFYADMINKEY, key, RECEIVEMESSAGE);
	}
	
	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: ADDCOURSE - commands the Server to add a Course
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVEMESSAGE
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @return data requested from Server
	 */
	public String addCourse(String courseName, String courseNum) {
		return communicateWithServer(ADDCOURSE, courseName + " " + courseNum, RECEIVEMESSAGE);
	}
	
	/**
	 * Calls method communicateWithServer with the following parameters:
	 * commandKey: REMOVESTUDENT - commands the Server to remove a Course
	 * dataToSend: given by the supplied parameters
	 * dataToReceive: RECEIVEMESSAGE
	 * 
	 * @param courseName is the Course name
	 * @param courseNum is the Course number
	 * @return data requested from Server
	 */
	public String removeCourse(String courseName, String courseNum) {
		return communicateWithServer(REMOVECOURSE, courseName + " " + courseNum, RECEIVEMESSAGE);
	}
	
	/**
	 * Sends command key to Server through the socket and checks if command is received by Server. 
	 * If command is received, then returns proceeding data received from Server through socket.
	 * Otherwise, returns null to indicate that Server did not receive command or command key is invalid.
	 * 
	 * @param commandKey is the command key sent to Server
	 * @param dataToSend is the data to send to Server to perform the command
	 * @param dataToReceive is the data returned from Server
	 * @return data received from Server if commandKey is received by Server and valid. Otherwise returns null.
	 */
	private String communicateWithServer(String commandKey, String dataToSend, int dataToReceive) {
		socketOut.println(commandKey); 
		
		switch(dataToReceive) {
		
		case RECEIVEMESSAGE:
			if (verifyReceivedCommand(receiveMessageFromServer())) {
				socketOut.println(dataToSend);
				return receiveMessageFromServer();
			}
			break;
			
		case RECEIVECOURSES:
			return receiveMultipleLineMessageFromServer();
		
		case RECEIVESPECIFIEDCOURSE:
			if (verifyReceivedCommand(receiveMessageFromServer())) { 
				socketOut.println(dataToSend);
				return receiveMultipleLineMessageFromServer(); 
			}
			break;	
		}
		
		return null;
	}
		
	/**
	 * Receives message from the Server by reading one line from the socket.
	 * @return message from Server
	 */
	public String receiveMessageFromServer() {
		String messageFromServer = " ";		
		try {
			messageFromServer = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageFromServer;
	}

	/**
	 * Received messages from the Server by reading multiple lines from the socket.
	 * @return messaged from Server
	 */
	public String receiveMultipleLineMessageFromServer() {
		String line;
		String messageFromServer = " ";	
		int checker = 0;
		try {
			while((line = socketIn.readLine()) != null) {
				if(line.contains("\0")) {
					line = line.replace("\0", "");
					checker = 1;
				}
				messageFromServer += line + "\n";
				if(checker == 1) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageFromServer;
	}
	
	/**
	 * Verifies is command is received by Server by checking if message from Server is equal to "valid".
	 * @param recieved is the message received from the Server
	 * @return true if message is equal to "valid". Otherwise returns false.
	 */
	private boolean verifyReceivedCommand(String recieved) {
		if (recieved.equalsIgnoreCase("valid"))
			return true;
		else
			return false;
	}
}