package com.vitvar.ctu.mdw;

import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
 * Simple protocol example. The class starts a server that listens on the port 8080,
 * and parses the input in a form add a b, where a and b are integer values, adds the two
 * numbers and sends the result back to the client. The communication ends when the client
 * sends bye command.
 * 
 * @author tomas@vitvar.com
 *
 */
public class SimpleProtocol {

	public static void main(String[] args) throws IOException {
		// info message to the console
		System.out.println("Listening on port 8080...");
				
		// listen on port 8080
		ServerSocket serverSocket = new ServerSocket(8080);
		
		// create a client socket when client connects
		Socket clientSocket = serverSocket.accept();
		
		// create reader and writer to read from and write to the socket
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String message;
		
		// print information to the client
		out.println("verbs: add a b, bye");
		
		// grammar definition
		Pattern p = Pattern.compile("^add ([0-9]+) ([0-9]+)$");
		Matcher m;		
		
		// read input from the client and process the input
		while ((message = in.readLine()) != null) {   
			if ((m = p.matcher(message)).matches())
				out.println("Result: " + (Integer.parseInt(m.group(1)) + Integer.parseInt(m.group(2))));
			else
				if (message.equals("bye")) {
					out.println("Goodbye!");
					break;
				} else
					out.println("Do not understand: " + message);
		}
	}
	
}