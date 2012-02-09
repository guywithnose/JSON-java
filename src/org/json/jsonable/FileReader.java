package org.json.jsonable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Class FileReader.
 */
public class FileReader
{

    /**
     * Read directory file names.
     * 
     * @param dirName
     *            the dir name
     * @return the string[]
     */
    public static String[] readDirectoryFileNames(String dirName)
    {
        File[] dir;
        dir = new File("war/" + dirName).listFiles();
        if (dir == null) // When running as google app, the working directory is
                         // different
        {
            dir = new File(dirName).listFiles();
        }
        String[] fileNames = new String[dir.length];
        for (int i = 0; i < dir.length; i++)
        {
            fileNames[i] = dir[i].getName();
        }
        return fileNames;
    }

    /**
     * Gets the file contents.
     * 
     * @param fileName
     *            the file name
     * @return the file contents
     */
    public static String getFileContents(String fileName)

    {
        try
        {
            FileInputStream fis;
            try
            {
                fis = new FileInputStream(fileName);
            } catch (FileNotFoundException e)
            {
                fis = new FileInputStream("war/" + fileName);
            }
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
     *            the fis
     * @return the string
     * @throws IOException
     */
    private static String readFile(FileInputStream fis) throws IOException
    {
        StringBuffer returnValue = new StringBuffer();
        byte[] buf = new byte[500];
        int size = 0;
        while ((size = fis.read(buf, 0, 500)) > 0)
            for (int i = 0; i < size; i++)
                returnValue.append((char) buf[i]);
        return returnValue.toString();
    }

}
