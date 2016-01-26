package ss.week7.cmdline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Peer for a simple client-server application
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Peer implements Runnable {
    public static final String EXIT = "exit";

    protected String name;
    protected Socket sock;
    protected BufferedReader in;
    protected BufferedWriter out;


    /*@
       requires (nameArg != null) && (sockArg != null);
     */
    /**
     * Constructor. creates a peer object based in the given parameters.
     * @param   nameArg name of the Peer-proces
     * @param   sockArg Socket of the Peer-proces
     */
    public Peer(String nameArg, Socket sockArg) throws IOException {
    	name = nameArg;
    	sock = sockArg;
    	in = new BufferedReader(new InputStreamReader(
                sockArg.getInputStream()));
    	out = new BufferedWriter(new OutputStreamWriter(
                sockArg.getOutputStream()));
    }

    /**
     * Reads strings of the stream of the socket-connection and
     * writes the characters to the default output.
     */
    public void run() {
    	String text;
    	try {
    			text = in.readLine();
    			while (text != null) {
    				System.out.println("remote: " + text);
    				text = in.readLine();
    			}
    			shutDown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    /**
     * Reads a string from the console and sends this string over
     * the socket-connection to the Peer process.
     * On Peer.EXIT the method ends
     */
    public void handleTerminalInput() {
    	String message;
    	while (!(message = readString("Enter message:")).equals("EXIT")) {
	    	try {
				out.write(getName() + " " + message + "\n");
				out.flush();
			} catch (IOException e) {
				System.err.println("Sending the message failed: " + e.getMessage());
			}
    	}
    	shutDown();
    }

    /**
     * Closes the connection, the sockets will be terminated
     */
    public void shutDown() {
    	try {
			in.close();
			out.close();
	    	sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    /**  returns name of the peer object*/
    public String getName() {
        return name;
    }

    /** read a line from the default input */
    static public String readString(String tekst) {
        System.out.print(tekst);
        String antw = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));
            antw = in.readLine();
        } catch (IOException e) {
        }

        return (antw == null) ? "" : antw;
    }
}
