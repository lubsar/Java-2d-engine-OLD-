package svk.sglubos.engine.io;

import java.io.File;
import java.net.URI;

//TODO document
public class FilePort {
	public static File loadFile(String path) {
		return new File(path);
	}
	
	public static File loadFile(URI uri) {
		return new File(uri);
	}
}
