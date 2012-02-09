/*
 * File: Jsonable.java Author: Robert Bittle <guywithnose@gmail.com>
 */
package org.json.jsonable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.jsonable.JsonableHelper;
import org.json.jsonable.JsonableListHelper;
import org.json.jsonable.objectIdentifier;

/**
 * The Class Jsonable.
 */
public abstract class Jsonable implements JSONString
{

    /** The name. */
    public String name = "";

    /**
     * Convert this object to a JsonString.
     * 
     * @param indent
     *            the level of indentation to use.
     * @return the json string representing this object
     */
    public String toJSON(int indent)
    {
        String retVal = "";
        try
        {
            retVal = toJSON().toString(indent);
        } catch (JSONException ignore)
        {
            // Do Nothing
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.json.JSONString#toJSONString()
     */
    @Override
    public String toJSONString()
    {
        return toJSON().toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return toJSON(4);
    }

    /**
     * Convert this object to a JSONObject.
     * 
     * @return the JSONObject representing this object.
     */
    public JSONObject toJSON()
    {
        JSONObject jo = new JSONObject();
        List<Field> allFields = getFields();
        for (int i = 0; i < allFields.size(); i++)
        {
            try
            {
                if (allFields.get(i).getName() != "name")
                    jo.put(allFields.get(i).getName(),
                            fieldToJson(allFields.get(i)));
            } catch (Exception ignore)
            {
                ignore.printStackTrace();
            }
        }
        return jo;
    }

    /**
     * Get all the fields of this object.
     * 
     * @return all the fields of this object.
     */
    public List<Field> getFields()
    {
        return getFields(null, true);
    }

    /**
     * Converts the value of a field to JSONObject, JSONArray, or a primitive,
     * based on its type.
     * 
     * @param field
     *            the field to process.
     * @return the JSON-compatible value of the field.
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @throws JSONException
     *             the jSON exception
     */
    public Object fieldToJson(Field field) throws IllegalArgumentException,
            JSONException
    {
        if (objectIdentifier.isList(field.getType()))
        {
            return handleList(field, getFieldValue(field));
        } else if (objectIdentifier.isJsonable(field.getType()))
        {
            return ((Jsonable) getFieldValue(field)).toJSON();
        } else
            return getFieldValue(field);
    }

    /**
     * Gets the field value.
     * 
     * @param field
     *            the field to get.
     * @return the value of this field
     */
    protected Object getFieldValue(Field field)
    {
        try
        {
            if (Modifier.isPrivate(field.getModifiers()))
                throw new Exception();
            return get(field);
        } catch (Exception e)
        {
            return getFieldValueByMethod(field);
        }
    }
    
    abstract protected Object get(Field field) throws IllegalArgumentException, IllegalAccessException;
    
    /**
     * Get Field value by method.
     * 
     * @param field
     *            the field
     * @return the field value by method
     */
    protected Object getFieldValueByMethod(Field field)
    {
        try
        {
            Method m = this.getClass().getMethod(
                    "get" + field.getName().substring(0, 1).toUpperCase()
                            + field.getName().substring(1));
            return m.invoke(this, new Object[0]);
        } catch (Exception e1)
        {
            return "Inaccessible";
        }
    }

    /**
     * Load from json.
     * 
     * @param jo
     *            the JSONObject we are loading
     * @param Name
     *            the name to give this object
     * @return the Jsonable object we loaded
     */
    public Jsonable loadFromJson(JSONObject jo, String Name)
    {
        name = Name;
        List<Field> allFields = getFields();
        for (int i = 0; i < allFields.size(); i++)
        {
            try
            {
                if (jo.has(allFields.get(i).getName()))
                    loadFieldFromJson(jo, allFields.get(i));

            } catch (Exception ignore)
            {
                // Do Nothing
            }
        }
        return this;
    }

    /**
     * Load field from json.
     * 
     * @param jo
     *            the jo
     * @param field
     *            the field
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @throws JSONException
     *             the jSON exception
     * @throws IllegalAccessException
     *             the illegal access exception
     */
    private void loadFieldFromJson(JSONObject jo, Field field)
            throws JSONException, IllegalArgumentException,
            IllegalAccessException
    {
        String fieldName = field.getName();
        if (objectIdentifier.isList(field.getType()))
        {
            loadListFromJson(jo, field);
        } else if (objectIdentifier.isJsonable(field.getType()))
        {
            loadJsonable(jo, field);
        } else if (field.getType().isEnum())
        {
            loadEnum(jo.get(fieldName).toString(), field);
        } else
            field.set(this, jo.get(fieldName));
    }

    /**
     * Load list from json.
     * 
     * @param jo
     *            the jo
     * @param field
     *            the field
     * @throws JSONException
     *             the jSON exception
     */
    @SuppressWarnings("unchecked")
    private void loadListFromJson(JSONObject jo, Field field)
            throws JSONException
    {
        String fieldName = field.getName();
        if (objectIdentifier.isListOfListsOfJsonables(field))
        {
            JsonableListHelper.loadListOfListsOfJsonables(
                    jo.getJSONArray(fieldName),
                    (List<List<Jsonable>>) getFieldValue(field));
        } else if (objectIdentifier.isListOfJsonables(field))
        {
            JsonableListHelper.loadListOfJsonables(jo.getJSONObject(fieldName),
                    (List<Jsonable>) getFieldValue(field),
                    objectIdentifier.getParamTree(field));
        } else
        {
            JsonableListHelper.loadListOfStrings(jo.getJSONArray(fieldName),
                    (List<String>) getFieldValue(field));
        }
    }

    /**
     * Load jsonable.
     * 
     * @param jo
     *            the jo
     * @param field
     *            the field
     * @throws JSONException
     *             the jSON exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @throws IllegalAccessException
     *             the illegal access exception
     */
    private void loadJsonable(JSONObject jo, Field field) throws JSONException
    {
        JSONObject JO = jo.getJSONObject(field.getName());
        Jsonable jsonable = (Jsonable) getFieldValue(field);
        jsonable.loadFromJson(JO, field.getName());
    }

    private void loadEnum(String value, Field field)
    {
        try
        {
            Method m = field.getType().getMethod("getValueOf", String.class);
            field.set(this, m.invoke(null, value));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Load from json.
     * 
     * @param jo
     *            the jo
     * @param Name
     *            the name
     */
    public void loadFromJson(String jo, String Name)
    {
        try
        {
            loadFromJson(new JSONObject(jo), Name);
        } catch (JSONException ignore)
        {
            // Do Nothing
        }
    }

    /**
     * Load from json.
     * 
     * @param jo
     *            the jo
     */
    public void loadFromJson(String jo)
    {
        loadFromJson(jo, "");
    }

    /**
     * Gets the fields in this object of "type".
     * 
     * @param type
     *            The type of variable we want(i.e. int, String, List, etc.).
     *            Use null to get all fields.
     * @param includeParents
     *            Whether to include fields from superclasses.
     * @return the fields of this class that match type.
     */
    protected List<Field> getFields(Class<?> type, boolean includeParents)
    {
        return JsonableHelper.getFields(this.getClass(), type, includeParents);
    }

    /**
     * Load from json.
     * 
     * @param jo
     *            the jo
     * @return the jSO nable
     */
    public Jsonable loadFromJson(JSONObject jo)
    {
        return loadFromJson(jo, "");
    }

    /**
     * Handle list.
     * 
     * @param field
     *            the field
     * @param object
     *            the object
     * @return the object
     * @throws JSONException
     *             the jSON exception
     */
    @SuppressWarnings("unchecked")
    private static Object handleList(Field field, Object object)
            throws JSONException
    {
        List<Class<?>> paramTree = objectIdentifier.getParamTree(field);
        if (paramTree.size() == 1)
        {
            return JsonableListHelper
                    .handleSimpleList(paramTree.get(0), object);
        } else if (objectIdentifier.isListOfListsOfJsonables(field))
        {
            return JsonableListHelper
                    .handleListOfListsOfJsonables((List<List<Jsonable>>) object);
        }
        return "List error";
    }

}
