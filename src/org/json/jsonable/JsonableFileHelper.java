package org.json.jsonable;

import org.json.JSONObject;

/**
 * The Class JsonableFileHelper.
 */
public class JsonableFileHelper
{

    /**
     * Load from file.
     * 
     * @param pathToJson
     *            the path to json
     * @return the jSON object
     */
    public static JSONObject loadFromFile(String pathToJson)
    {
        try
        {
            String data;
            data = FileReader.getFileContents(pathToJson);
            return new JSONObject(data);
        } catch (Exception e)
        {
            return new JSONObject();
        }
    }
    
}
