package com.gen.networking;

import javax.management.ObjectName;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

public class Client implements Runnable
{
	// initialize socket and input output streams
	private Socket socket = null;
	private Socket socketTwo = null;		// the read socket
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private DataInputStream inputTwo = null;
	private DataOutputStream outTwo = null;
	static private String outString = "";
	static private String inString = "";
	static private Object outStringLock = new Object();
	static private Object inStringLock = new Object();

	public void run()
	{
		String ID = UUID.randomUUID().toString();

		// establish a connection
		try
		{
			socket = new Socket(InetAddress.getLocalHost(), 15);

			input = new DataInputStream(socket.getInputStream());
			// sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());

			// write out our unique ID
			out.writeUTF(ID);

			socketTwo = new Socket(InetAddress.getLocalHost(), 15);

			// takes input from terminal
			inputTwo = new DataInputStream(socketTwo.getInputStream());
			// sends output to the socket
			outTwo = new DataOutputStream(socketTwo.getOutputStream());

			// write out our unique ID
			outTwo.writeUTF(ID);

			// then we wait for the first socket to get the ID back

			String result = inputTwo.readUTF();

			if (!result.equals(ID))
			{
				System.out.println("Critical error: ID received does not match made ID");
				System.exit(-1);
			}
			else
			{
				System.out.println("Connection made");
			}

			ClientReader clientReader = new ClientReader(socketTwo);
			new Thread(clientReader).start();

			System.out.println("S");

			while(true)
			{
				synchronized (outStringLock)
				{
					// if there is a string set, and not just blank space
					if (!outString.equals(""))
					{
						out.writeUTF(outString);
						outString = "";
					}
				}
			}
		} catch (UnknownHostException u)
		{
			System.out.println(u);
		} catch (IOException i)
		{
			System.out.println(i);
		}
	}

	static public void SetOutputString(String string)
	{
		synchronized (outStringLock)
		{
			outString = string;
		}
	}

	static public void SetInputString(String string)
	{
		synchronized (inStringLock)
		{
			inString = "->" + string;
		}
	}

	static public String GetInputString()
	{
		synchronized (inStringLock)
		{
			String result = inString;
			inString = "";
			return result;
		}
	}
}
