/**
 * 
 */
package org.json.jsonable;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dude
 * 
 */
public class objectIdentifier
{

    static final Pattern p = Pattern.compile("<(.*)>");

    /**
     * Checks if is list.
     * 
     * @param t
     *            the t
     * @return true, if is list
     */
    public static boolean isList(Class<?> t)
    {
        Type[] thing = t.getGenericInterfaces();
        for (int i = 0; i < thing.length; i++)
            if (thing[i].toString().contains("java.util.List"))
                return true;
        return false;
    }

    /**
     * Gets the param tree.
     * 
     * @param f
     *            the f
     * @return the param tree
     */
    public static List<Class<?>> getParamTree(Field f)
    {
        List<Class<?>> tree = new ArrayList<Class<?>>();
        String GenericType = (f.getGenericType().toString());
        GenericType = extractParam(GenericType);
        while (GenericType != "")
        {
            try
            {
                tree.add(Class.forName(removeParam(GenericType)));
            } catch (ClassNotFoundException e)
            {
                tree.add(Object.class);
            }
            GenericType = extractParam(GenericType);
        }
        return tree;
    }

    private static String extractParam(String GenericType)
    {
        Matcher result = p.matcher(GenericType);
        if (result.find())
        {
            return result.group(1);
        }
        return "";
    }

    /**
     * Removes the param.
     * 
     * @param GenericType
     *            the generic type
     * @return the string
     */
    private static String removeParam(String GenericType)
    {
        return p.split(GenericType)[0];
    }

    /**
     * Checks if is jsonable.
     * 
     * @param t
     *            the t
     * @return true, if is jsonable
     */
    public static boolean isJsonable(Class<?> t)
    {
        while (t.getGenericSuperclass() != null)
        {
            try
            {
                t = (Class<?>) t.getGenericSuperclass();
            } catch (Exception e)
            {
                return false;
            }
            if (t.equals(Jsonable.class))
                return true;
        }
        return false;
    }
    
    /**
     * Checks if is list of lists of jsonables.
     * 
     * @param field
     *            the field
     * @return true, if is list of lists of jsonables
     */
    public static boolean isListOfJsonables(Field field)
    {
        List<Class<?>> paramTree = objectIdentifier.getParamTree(field);
        return paramTree.size() == 1
                && objectIdentifier.isJsonable(paramTree.get(0));
    }
    
    /**
     * Checks if is list of lists of jsonables.
     * 
     * @param field
     *            the field
     * @return true, if is list of lists of jsonables
     */
    public static boolean isListOfListsOfJsonables(Field field)
    {
        List<Class<?>> paramTree = objectIdentifier.getParamTree(field);
        return paramTree.size() == 2
                && objectIdentifier.isList(paramTree.get(0))
                && objectIdentifier.isJsonable(paramTree.get(1));
    }

}
