package org.json.jsonable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Class JsonListHelper.
 * 
 * @author Dude
 */
public class JsonableListHelper
{

    /**
     * Load string array.
     * 
     * @param ja
     *            the ja
     * @param list
     *            the list
     */
    public static void loadListOfStrings(JSONArray ja, List<String> list)
    {
        list.clear();
        if (ja.length() > 0)
        {
            for (int i = 0; i < ja.length(); i++)
            {
                try
                {
                    list.add(ja.getString(i));
                } catch (JSONException ignore)
                {
                    // Do Nothing
                }
            }
        }
    }

    /**
     * Load list of lists of jsonables.
     * 
     * @param ja
     *            the ja
     * @param list
     *            the list
     * @throws JSONException
     *             the jSON exception
     */
    public static void loadListOfListsOfJsonables(JSONArray ja,
            List<List<Jsonable>> list) throws JSONException
    {
        for (int i = 0; i < ja.length(); i++)
        {
            JSONObject jo = ja.getJSONObject(i);
            String[] names = JSONObject.getNames(jo);
            java.util.Arrays.sort(names);
            for (int j = 0; j < names.length; j++)
            {
                list.get(i).get(j).loadFromJson(jo.getJSONObject(names[j]), "");
            }
        }
    }

    /**
     * List to json.
     * 
     * @param list
     *            the list
     * @return the jSON array
     */
    public static JSONArray stringListToJSONArray(List<String> list)
    {
        JSONArray JA = new JSONArray();
        for (Iterator<String> i = list.iterator(); i.hasNext();)
        {
            JA.put(i.next());
        }
        return JA;
    }

    /**
     * List to json object.
     * 
     * @param list
     *            the list
     * @return the jSON object
     * @throws JSONException
     *             the jSON exception
     */
    public static JSONObject listOfJsonablesToJsonObject(List<Jsonable> list)
            throws JSONException
    {
        JSONObject JO = new JSONObject();
        Iterator<Jsonable> i = list.iterator();
        int index = 0;
        while (i.hasNext())
        {
            Jsonable item = i.next();
            if (item.name != "")
                JO.put(item.name, item.toJSON());
            else
                JO.put(String.valueOf(index), item.toJSON());
            index++;
        }
        return JO;
    }

    /**
     * Get List names.
     * 
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @return the list names
     */
    public static <T extends Jsonable> List<String> getListNames(
            List<T> list)
    {
        List<String> names = new ArrayList<String>();
        for (Iterator<T> i = list.iterator(); i.hasNext();)
            names.add(i.next().name);
        return names;
    }

    /**
     * Get Item by name.
     * 
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param name
     *            the name
     * @return the item by name
     */
    public static <T extends Jsonable> T getItemByName(List<T> list, String name)
    {
        T current;
        Iterator<T> i = list.iterator();
        while (i.hasNext())
        {
            current = i.next();
            if (current.name.equals(name))
                return current;
        }
        return null;
    }

    /**
     * Removes the from list.
     * 
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param name
     *            the name
     */
    public static <T extends Jsonable> void removeFromList(List<T> list,
            String name)
    {
        list.remove(getItemByName(list, name));
    }

    /**
     * Handle simple list.
     * 
     * @param listType
     *            the list type
     * @param object
     *            the object
     * @return the object
     * @throws JSONException
     *             the jSON exception
     */
    @SuppressWarnings("unchecked")
    public static Object handleSimpleList(Class<?> listType, Object object)
            throws JSONException
    {
        if (listType.equals(String.class))
            return stringListToJSONArray((List<String>) object);
        else if (objectIdentifier.isJsonable(listType))
        {
            return listOfJsonablesToJsonObject((List<Jsonable>) object);
        }
        return "List error";
    }

    /**
     * Handle list of lists of jsonables.
     * 
     * @param list
     *            the list
     * @return the object
     */
    public static Object handleListOfListsOfJsonables(List<List<Jsonable>> list)
    {
        JSONArray retVal = new JSONArray();
        for (int i = 0; i < list.size(); i++)
        {
            try
            {
                retVal.put(listOfJsonablesToJsonObject(list.get(i)));
            } catch (JSONException ignore)
            {
                // Do Nothing
            }
        }
        return retVal;
    }

    /**
     * Load list of jsonables.
     * 
     * @param jo
     *            the jo
     * @param list
     *            the list
     * @param paramTree
     *            the param tree
     */
    @SuppressWarnings("unchecked")
    public static void loadListOfJsonables(JSONObject jo, List<Jsonable> list,
            List<Class<?>> paramTree)
    {
        String[] names = JSONObject.getNames(jo);
        try
        {
            if (names != null)
            {
                for (int i = 0; i < names.length; i++)
                {
                    Jsonable item = JsonableHelper.loadFromJson(jo.getJSONObject(names[i]), "",
                                    (Class<? extends Jsonable>) paramTree.get(0));
                    item.name = names[i];
                    list.add(item);

                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
