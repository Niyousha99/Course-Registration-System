package serverController;

import serverModel.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Provides data fields and methods to create a Java-type, representing a Server Back End in a Student Course Registration System.
 *
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class BackEnd implements Runnable, Constants{
	/**
	 * Registration App
	 */
	private RegistrationApp regApp;
	
	/**
	 * Socket to communicate with Client
	 */
	private Socket aSocket;
	
	/**
	 * Output socket to send information to Client
	 */
	private PrintWriter socketOut;
	
	/**
	 * Input socket to receive commands from Client
	 */
	private BufferedReader socketIn;
	
	/**
	 * Constructs a BackEnd object by invoking the constructor of class RegistrationApp and 
	 * establishing connection with Client with socket supplied by the given parameter
	 * @param s - socket connected to Client
	 */
	public BackEnd(Socket s) {
		regApp = new RegistrationApp();
		aSocket = s;
		try {
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream());
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Calls methods according to command received from Client
	 */
	public void menu() {
		int breaker = 0;
		String choiceFromServer = "";
		
		while (breaker == 0) {
			try{
				choiceFromServer = readFromSocket();
				
			}catch(Exception e){
				try{
					socketIn.close();
					socketOut.close();
					aSocket.close();
				}catch(Exception e1){
				}
			}
						
			if(choiceFromServer.compareTo( "4" ) != 0 && choiceFromServer != null) sendString("valid");

			switch (choiceFromServer) {
			case "1":
				searchCatCourses();
				break;
				
			case "2":
				addStudentCourse();
				break;
				
			case "3":
				removeStudentCourse();
				break;
				
			case "4":
				listCatCourses();
				break;
				
			case "5":
				listStudentCourses();
				break;
				
			case "6":
				searchStudent();
				break;
				
			case "7":
				verifyAdminKey();
				break;
				
			case "8":
				addCourse();
				break;
				
			case "9":
				removeCourse();
				break;
				
			default:
				sendString("invalid");
				breaker = -1;
				break;
			}
		}
	}
	
	/**
	 * Searches Course catalog for Course read from socket by calling method searchCatCourses in class RegistrationApp
	 * Then calls method sendString with the result
	 */
	private void searchCatCourses() {
		String str = readFromSocket();
		String searchCatCoursesParameters[] = str.split(" ");
		sendString(regApp.searchCatCourses(searchCatCoursesParameters[0], Integer.parseInt(searchCatCoursesParameters[1])) + "\0");	
	}
	
	/**
	 * Adds Student Course read from socket by calling method addCourse in class RegistrationApp
	 * Then calls method sendString with the result
	 */
	private void addStudentCourse() {
		String str = readFromSocket();
		String addCourseParameters[] = str.split(" ");
		sendString(regApp.addCourse(addCourseParameters[0], Integer.parseInt(addCourseParameters[1]), addCourseParameters[2],
				Integer.parseInt(addCourseParameters[3]), Integer.parseInt(addCourseParameters[4])));
	}
	
	/**
	 * Removes Student Course read from socket by calling method removeCourse in class RegistrationApp
	 * Then calls method sendString with the result
	 */
	private void removeStudentCourse() {
		String str = readFromSocket();
		String removeCourseParameters[] = str.split(" ");
		sendString(regApp.removeStudentCourse(removeCourseParameters[0], Integer.parseInt(removeCourseParameters[1]), removeCourseParameters[2],
				Integer.parseInt(removeCourseParameters[3]), Integer.parseInt(removeCourseParameters[4])));
	}
	
	/**
	 * Gets list of Catalog Courses from method listAllCourses in class RegistrationApp
	 * Then calls method sendString with the result
	 */
	private void listCatCourses() {
		sendString(regApp.listAllCourses() + "\0");
	}

	/**
	 * Lists Courses of Student read from socket by calling method listStudentCourses in class RegistrationApp
	 * Then calls method sendString with the result
	 */
	private void listStudentCourses() {
		String str = readFromSocket();
		String student [] = str.split(" ");
		sendString(regApp.listStudentCourses(student[0], Integer.parseInt(student[1])) + "\0");
	}

	/**
	 * Searches for Student read from socket by calling method validateStudent
	 * Then calls method sendString with the result
	 */
	private void searchStudent() {
		int checker = 0;
		String str = readFromSocket();
		String student [] = str.split(" ");

		for (Student s: regApp.getDataBase().getStudentListFromDataBase()) {
			if (validateStudent(student[0], Integer.parseInt(student[1]), s)) {
				checker++;
				break;
			}
		}
		
		if (checker == 0)
			sendString("invalid");
		else
			sendString("valid");
	}
	
	/**
	 * Verifies administrator Key and calls method sendString with the result
	 */
	private void verifyAdminKey() {
		String key = readFromSocket();
		
		if (Integer.parseInt(key) > 0)
			sendString("valid");
		else
			sendString("invalid");
	}
	
	/**
	 * Adds Course read from socket by calling method addCourse and updatCourseCatalogue in classes 
	 * DBController and CourseCatalogue respectively. Then calls method sendString with the result.
	 */
	private void addCourse() {
		String str = readFromSocket();
		String student [] = str.split(" ");
		String courseName = student[0] + " " + student[1];
		
		if(regApp.getDataBase().getDBController().addCourse(courseName, NUMSECTIONS)) {
			regApp.getCat().updateCourseCatalogue();
			sendString("valid");
		}
		else
			sendString("invalid");
	}
	
	/**
	 * Removed Course read from socket by calling method removeCourse and updatCourseCatalogue in classes 
	 * DBController and CourseCatalogue respectively. Then calls method sendString with the result.
	 */
	private void removeCourse() {
		String str = readFromSocket();
		String student [] = str.split(" ");
		String courseName = student[0] + " " + student[1];
		if(regApp.getDataBase().getDBController().removeCourse(courseName)) {
			regApp.getCat().updateCourseCatalogue();
			sendString("valid");
		}
	}
	
	/**
	 * Reads message from socket
	 * @return message
	 */
	private String readFromSocket() {
		String message = " ";
		
		try {
			message = socketIn.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * Validates Student information supplied by the given parameters
	 * 
	 * @param name - Student first name
	 * @param id - Student 3-digit Id
	 * @param theStudent - Student 
	 * @return true if Student is valid and returns false otherwise
	 */
	private boolean validateStudent(String name, int id, Student theStudent) {
		if (theStudent.getStudentName().equalsIgnoreCase(name) && theStudent.getStudentId() == id)
			return true;
		else
			return false;
	}

	/**
	 * Sends string to output socket then flushes the socket
	 * @param toSend - String printed to socket
	 */
	public void sendString(String toSend) {
		socketOut.println(toSend);
		socketOut.flush();
	}
	
	/**
	 * Runs Back End by calling method menu
	 */
	@Override
	public void run() {
		try {
			menu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
