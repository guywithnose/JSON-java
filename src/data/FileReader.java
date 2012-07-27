package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The Class FileReader.
 */
public class FileReader {
  public static String getFileContents(String fileName) {
    return getFileContents(new File(fileName));
  }

  /**
   * Gets the file contents.
   * 
   * @param fileName
   *          the file name
   * @return the file contents
   */
  public static String getFileContents(File file)
    {
        try
        {
            FileInputStream fis;
            fis = new FileInputStream(file);
            return readFile(fis);
        } catch (IOException e)
        {
            return null;
        }
    }

  /**
   * Read file.
   * 
   * @param fis
   *          the fis
   * @return the string
   * @throws IOException
   */
  private static String readFile(FileInputStream fis) throws IOException {
    StringBuffer returnValue = new StringBuffer();
    byte[] buf = new byte[500];
    int size = 0;
    while ((size = fis.read(buf, 0, 500)) > 0)
      for (int i = 0; i < size; i++)
        returnValue.append((char) buf[i]);
    return returnValue.toString();
  }
}
