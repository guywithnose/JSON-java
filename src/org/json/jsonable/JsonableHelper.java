/*
 * File: JsonHelper.java Author: Robert Bittle <guywithnose@gmail.com>
 */
package org.json.jsonable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dude
 * 
 */
public class JsonableHelper
{

    /**
     * Gets the fields.
     * 
     * @param theClass
     *            the the class
     * @param type
     *            the type
     * @param includeParents
     *            the include parents
     * @return the fields
     */
    public static List<Field> getFields(Class<?> theClass, Class<?> type,
            boolean includeParents)
    {
        Field[] fields = theClass.getDeclaredFields();
        ArrayList<Field> fieldList = new ArrayList<Field>();
        for (int i = 0; i < fields.length; i++)
        {
            if ((fields[i].getType() == type || type == null)
                    && goodFieldName(fields[i].getName()))
                fieldList.add(fields[i]);
        }
        if (includeParents && theClass != Jsonable.class)
        {
            fieldList.addAll(getFields(theClass.getSuperclass(), null, true));
        }
        return fieldList;
    }

    /**
     * Good field name.
     * 
     * @param name
     *            the name
     * @return true, if successful
     */
    public static boolean goodFieldName(String name)
    {
        return name.charAt(0) != '$' && !name.equals("serialVersionUID");
    }

    /**
     * Load from json.
     * 
     * @param <T>
     *            the generic type
     * @param jo
     *            the jo
     * @param Name
     *            the name
     * @param type
     *            the class name
     * @return the jsonable
     */
    @SuppressWarnings("unchecked")
    public static <T extends Jsonable> T loadFromJson(JSONObject jo,
            String Name, Class<T> type)
    {
        T jsonable = null;
        try
        {
            jsonable = type.newInstance();
            jsonable.loadFromJson(jo, Name);
        } catch (Exception e)
        {
            try
            {// Classes whose constructors require parameters must implement
             // their own loadFromJson Method
                jsonable = (T) type.getMethod("loadFromJson", JSONObject.class,
                        String.class, Class.class).invoke(null, jo, Name, type);
            } catch (Exception ignore)
            {
                // Do Nothing
            }
        }
        return jsonable;
    }

    /**
     * Load from json.
     * 
     * @param jo
     *            the jo
     * @param Name
     *            the name
     * @param className
     *            the class name
     * @return the jsonable
     */
    public static <T extends Jsonable> T loadFromJson(String jo, String Name,
            Class<T> className)
    {
        T jsonable = null;
        try
        {
            jsonable = loadFromJson(new JSONObject(jo), Name, className);
        } catch (JSONException ignore)
        {
            // Do Nothing
        }
        return jsonable;
    }

}
