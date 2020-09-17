package serverController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provides data fields and methods to create a Java-type, representing a Server Communication Controller in a 
 * Student Course Registration System.
 *
 * @author Dunsin Shitta-Bey
 * @author Niyousha Raeesinejad
 */
public class ServerCommsController{
	/**
	 * Server socket connected to the Client
	 */
	private ServerSocket serverSocket; 
	
	/**
	 * Thread pool to serve multiple Clients
	 */
	private ExecutorService threadPool;
	
	/**
	 * Constructs a ServerCommsController object by establishing connection with Client through socket
	 * initializes with the port number supplied by the given parameter and creates a thread pool
	 * @param port is the specified port number matching that of the Client
	 */
	public ServerCommsController(int port){
		try{
			serverSocket = new ServerSocket(port);
			threadPool = Executors.newCachedThreadPool(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Communicates with the Client by constructing a BackEnd object and executing the thread pool
	 * @throws IOException - I/O Exception
	 */
	public void communicateWithClient() throws IOException{
		try {
			while(true) {
				BackEnd theBackEnd = new BackEnd(serverSocket.accept());
				threadPool.execute(theBackEnd);
			}
		}catch(Exception e) {
			e.printStackTrace();
			threadPool.shutdown();
		}
	}

	public static void main(String args[]) {
		ServerCommsController myServerController = new ServerCommsController(9090);
		try {
			myServerController.communicateWithClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
