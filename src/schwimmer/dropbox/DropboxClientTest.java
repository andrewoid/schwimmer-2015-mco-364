package schwimmer.dropbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Assert;
import org.junit.Test;

public class DropboxClientTest implements MessagePatterns, Constants {

	private ServerSocket serverSocket;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	@org.junit.Before
	public void before() {
		try {
			givenClientConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@org.junit.After
	public void after() {
		try {
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void givenClientConnection() throws IOException {
		serverSocket = new ServerSocket(PORT);
		socket = serverSocket.accept();
		writer = new PrintWriter(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
	}

	@Test
	public void testList() throws IOException {
		String line = reader.readLine();
		Assert.assertEquals("LIST", line);
	}

	@Test
	public void testDownload() throws IOException {
		String line = reader.readLine();
		Assert.assertEquals("LIST", line);
		String filename = new RandomString(8) + ".txt";
		long lastModified = System.currentTimeMillis();
		writer.println("FILES 1");
		writer.println("FILE "+ filename + " " +lastModified + " " + 0 );
		line = reader.readLine();
		Assert.assertTrue(line + " is not a valid DOWNLOAD message", DOWNLOAD
				.matcher(line).matches());
	}
	
	@Test
	public void testSync() throws IOException {
		String line = reader.readLine();
		Assert.assertEquals("LIST", line);
		writer.println("FILES 0");
		String filename = new RandomString(8) + ".txt";
		long lastModified = System.currentTimeMillis();
		writer.println("SYNC "+ filename + " " +lastModified + " " + 0 );
		line = reader.readLine();
		Assert.assertTrue(line + " is not a valid DOWNLOAD message", DOWNLOAD
				.matcher(line).matches());
	}

}
