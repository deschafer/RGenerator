package com.gen.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReader implements Runnable
{

	private DataInputStream inputStream;
	private Socket clientSocket;
	private boolean running = true;

	public ClientReader(Socket clientSocket)
	{
		this.clientSocket = clientSocket;

		// create the input stream
		try
		{
			inputStream = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		String readLine = "";
		while (running)
		{
			// read from the socket, and go ahead and print it out
			try
			{
				readLine = inputStream.readUTF();
			} catch (IOException e)
			{
				e.printStackTrace();
				running = false;
			}

			Client.SetInputString(readLine);
		}
	}
}
