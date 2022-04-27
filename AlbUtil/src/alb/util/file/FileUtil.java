package alb.util.file;

import java.io.InputStream;

public class FileUtil {

	/**
	 * Return the full path of a file on the classpath
	 * @param fileName
	 * @return
	 */
	public static String fromClassPath(String fileName) {
		return FileUtil.class.getClassLoader().getResource( fileName ).getFile();
	}

	/**
	 * Return an InputStream to a file on the classpath
	 * @param fileName
	 * @return
	 */
	public static InputStream fromClassPathAsStream(String fileName) {
		return FileUtil.class.getClassLoader().getResourceAsStream( fileName );
	}

}
