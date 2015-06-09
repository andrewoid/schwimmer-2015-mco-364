package schwimmer.dropbox;

import java.util.regex.Pattern;

public interface MessagePatterns {

	public static final Pattern FILES = Pattern.compile("FILES (\\d+)");
	public static final Pattern FILE = Pattern.compile("FILE (.+?) (\\d+) (\\d+)");
	public static final Pattern SYNC = Pattern.compile("SYNC (.+?) (\\d+) (\\d+)");
	public static final Pattern CHUNK = Pattern.compile("CHUNK (.+?) (\\d+) (\\d+) (\\d+) ([0-9a-zA-Z=]+)");
	
}
