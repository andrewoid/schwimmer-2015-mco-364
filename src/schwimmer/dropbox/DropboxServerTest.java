package schwimmer.dropbox;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Matcher;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class DropboxServerTest implements MessagePatterns, Constants {
	
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	@Test
	public void testFileMessages() throws IOException {
		
		givenServerConnection();
		
		writer.println("LIST");
		
		String filesMessages = reader.readLine();
		
		Matcher matcher = FILES.matcher(filesMessages);
		Assert.assertTrue(matcher.matches());
		int numFiles = Integer.parseInt( matcher.group(1) );
		for ( int i=0; i<numFiles; i++ ) {
			String fileMessage = reader.readLine();
			Assert.assertTrue(FILE.matcher(fileMessage).matches());
		}
		
	}

	private void givenServerConnection() throws IOException {
		socket = new Socket(HOSTNAME, PORT);
		writer = new PrintWriter(socket.getOutputStream());
		reader = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
	}
	
	@Test
	public void testSmallTextUpload() throws IOException {
		givenServerConnection();
		
		String text = "Hello Students";
		
		thenFileUploadsSuccessfully(text);
		
	}

	private void thenFileUploadsSuccessfully(String text) throws IOException {
		String filename = new RandomString(8).nextString() + ".txt";

		int filesize = text.length();
		long lastModified = System.currentTimeMillis();
		if ( filesize <= 512 ) {
			String encoded = Base64.encode(text.getBytes());
			writer.println("CHUNK " + filename + " " + 
					lastModified + " " + 
					text.length() + 
					" 0 " + 
					encoded);
		}
		else {
			for ( int i=0; i<filesize; i+=512 ) {
				int end = Math.min(i+512, filesize);
				String encoded = Base64.encode(text.substring(i, end).getBytes());
				writer.println("CHUNK " + filename + " " + 
						lastModified + " " + 
						text.length() + 
						" " + i + " " + 
						encoded);
			}
		}
		
		assetExistsInList(filename, lastModified, text.length());
	}
	
	private void assetExistsInList( String filename, long lastModified, int length ) throws IOException {
		writer.println("LIST");
		
		String filesMessages = reader.readLine();
		if ( SYNC.matcher(filesMessages).matches() ) {
			filesMessages = reader.readLine();
		}
		
		Matcher matcher = FILES.matcher(filesMessages);
		Assert.assertTrue(matcher.matches());
		String expected = "FILE " + filename + " " + lastModified + " " + length;
		int numFiles = Integer.parseInt( matcher.group(1) );
		for ( int i=0; i<numFiles; i++ ) {
			String fileMessage = reader.readLine();
			if ( expected.equals(fileMessage) ) {
				return;
			}
		}
		
		Assert.fail(expected + " was not found in LIST response");
	}
	
	@Test
	public void testMediumTextUpload() throws IOException {
		givenServerConnection();
		
		String text = "When in the course of human events, it becomes "
				+ "necessary for one people to dissolve the political bands which have connected "
				+ "them with another, and to assume among the powers of the earth, the separate "
				+ "and equal station to which the laws of nature and of nature's God entitle them, "
				+ "a decent respect to the opinions of mankind requires that they should declare the "
				+ "causes which impel them to the separation.";
		thenFileUploadsSuccessfully(text);
		
	}
	
	@Test
	public void testLargeTextUpload() throws IOException {
		givenServerConnection();
		
		String text = IOUtils.toString(new FileInputStream("hackerCrackdown.txt"));
		thenFileUploadsSuccessfully(text);
	}
	

	@Test
	public void testLargeBinaryUpload() throws IOException {
		givenServerConnection();
		
		byte bytes[] = IOUtils.toByteArray(new FileInputStream("javalogo.png"));
		String filename = new RandomString(8).nextString() + ".txt";

		int filesize = bytes.length;
		long lastModified = System.currentTimeMillis();
		
		for ( int i=0; i<filesize; i+=512 ) {
			int end = Math.min(i+512, filesize);
			byte sub[] = Arrays.copyOfRange(bytes, i, end);
			String encoded = Base64.encode(sub);
			writer.println("CHUNK " + filename + " " + 
					lastModified + " " + 
					filesize + 
					" " + i + " " + 
					encoded);
		}

		assetExistsInList(filename, lastModified, filesize);
	}
	
	@Test
	public void testSyncMessage() throws IOException {
		givenServerConnection();
		
		Socket anotherSocket = new Socket(HOSTNAME, PORT);
		BufferedReader anotherReader = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
		
		thenFileUploadsSuccessfully("Hi");
		
		String line = anotherReader.readLine();
		Assert.assertTrue(line + " is not a SYNC message", SYNC.matcher(line).matches());
	}
	
	
}
