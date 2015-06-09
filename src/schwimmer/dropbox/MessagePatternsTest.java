package schwimmer.dropbox;

import org.junit.Assert;
import org.junit.Test;

public class MessagePatternsTest implements MessagePatterns {

	@Test
	public void testFiles() {
		Assert.assertTrue( FILES.matcher("FILES 10").matches() );
		Assert.assertTrue( FILES.matcher("FILES 0").matches() );
		Assert.assertFalse( FILES.matcher("FILES ").matches() );
		Assert.assertFalse( FILES.matcher("FILE 10").matches() );
	}
	
	@Test
	public void testFile() {
		Assert.assertTrue( FILE.matcher("FILE helloWorld.txt 100 100").matches() );
		Assert.assertFalse( FILE.matcher("FILE helloWorld.txt 100").matches() );
	}
	
	@Test
	public void testSync() {
		Assert.assertTrue( SYNC.matcher("SYNC helloWorld.txt 100 100").matches() );
		Assert.assertFalse( SYNC.matcher("SYNC helloWorld.txt 100").matches() );
	}
	
	@Test
	public void testChunk() {
		Assert.assertTrue( CHUNK.matcher("CHUNK Hello.txt 1234 11 0 SGVsbG8gV29ybGQ=").matches() );
		Assert.assertFalse( CHUNK.matcher("CHUNK Hello.txt 1234 11 0").matches() );
	}
	
}
