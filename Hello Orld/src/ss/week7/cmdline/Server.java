
package ss.week7.cmdline;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Server. 
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Server {
    private static final String USAGE
        = "usage: " + Server.class.getName() + " <name> <port>";

    /** Starts a Server-application. */
    public static void main(String[] args) {
    	if (args.length != 2) {
    		System.out.println(USAGE);
    		System.exit(0);
    	}
    	
    	String name = args[0];
    	int port = 0;
    	Socket sock = null;
    	ServerSocket server = null;
    	
    	try {
    		port = Integer.parseInt(args[1]);
    	} catch (NumberFormatException e) {
    		System.out.println("ERROR: port " + args[1] + " is not a number!" );
    		System.exit(0);
    	}
    	
    	try {
    		server = new ServerSocket(port);
    		sock = server.accept();
    	} catch (IOException e) {
    		System.out.println("Error: could not create socket. " + port);
    	}
    	
    	try {
			Peer client = new Peer(name, sock);
			Thread streamInputHandler = new Thread(client);
			streamInputHandler.start();
			client.handleTerminalInput();
			client.shutDown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

} // end of class Server
