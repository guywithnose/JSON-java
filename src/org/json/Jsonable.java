/*
 * File: Jsonable.java Author: Robert Bittle <guywithnose@gmail.com>
 */
package org.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Jsonable.
 */
public abstract class Jsonable implements JSONString {

  /** The name. */
  public String name = "";

  /**
   * Convert this object to a JsonString.
   * 
   * @param indent
   *          the level of indentation to use.
   * @return the json string representing this object
   */
  public String toJSON(int indent) {
    String retVal = "";
    try {
      retVal = toJSON().toString(indent);
    } catch (JSONException ignore) {
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
  public String toJSONString() {
    return toJSON().toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return toJSON(4);
  }

  /**
   * Convert this object to a JSONObject.
   * 
   * @return the JSONObject representing this object.
   */
  public JSONObject toJSON() {
    JSONObject jo = new JSONObject();
    List<Field> allFields = getFields();
    for (int i = 0; i < allFields.size(); i++) {
      try {
        if (allFields.get(i).getName() != "name")
          jo.put(allFields.get(i).getName(), fieldToJson(allFields.get(i)));
      } catch (Exception ignore) {
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
  public List<Field> getFields() {
    return getFields(null, true);
  }

  /**
   * Converts the value of a field to JSONObject, JSONArray, or a primitive,
   * based on its type.
   * 
   * @param field
   *          the field to process.
   * @return the JSON-compatible value of the field.
   * @throws IllegalArgumentException
   *           the illegal argument exception
   * @throws JSONException
   *           the jSON exception
   */
  public Object fieldToJson(Field field) throws IllegalArgumentException,
      JSONException {
    if (isList(field.getType())) {
      return handleList(field, getFieldValue(field));
    } else if (isJsonable(field.getType())) {
      return ((Jsonable) getFieldValue(field)).toJSON();
    } else
      return getFieldValue(field);
  }

  /**
   * Gets the field value.
   * 
   * @param field
   *          the field to get.
   * @return the value of this field
   */
  protected Object getFieldValue(Field field) {
    try {
      if (Modifier.isPrivate(field.getModifiers()))
        throw new Exception();
      return get(field);
    } catch (Exception e) {
      return getFieldValueByMethod(field);
    }
  }

  abstract protected Object get(Field field) throws IllegalArgumentException,
      IllegalAccessException;

  /**
   * Get Field value by method.
   * 
   * @param field
   *          the field
   * @return the field value by method
   */
  protected Object getFieldValueByMethod(Field field) {
    try {
      Method m = this.getClass().getMethod(
          "get" + field.getName().substring(0, 1).toUpperCase()
              + field.getName().substring(1));
      return m.invoke(this, new Object[0]);
    } catch (Exception e1) {
      return "Inaccessible";
    }
  }

  /**
   * Load from json.
   * 
   * @param jo
   *          the JSONObject we are loading
   * @param Name
   *          the name to give this object
   * @return the Jsonable object we loaded
   */
  public Jsonable loadFromJson(JSONObject jo, String Name) {
    name = Name;
    List<Field> allFields = getFields();
    for (int i = 0; i < allFields.size(); i++) {
      try {
        if (jo.has(allFields.get(i).getName()))
          loadFieldFromJson(jo, allFields.get(i));

      } catch (Exception ignore) {
        // Do Nothing
      }
    }
    return this;
  }

  /**
   * Load field from json.
   * 
   * @param jo
   *          the jo
   * @param field
   *          the field
   * @throws IllegalArgumentException
   *           the illegal argument exception
   * @throws JSONException
   *           the jSON exception
   * @throws IllegalAccessException
   *           the illegal access exception
   */
  private void loadFieldFromJson(JSONObject jo, Field field)
      throws JSONException, IllegalArgumentException, IllegalAccessException {
    String fieldName = field.getName();
    if (isList(field.getType())) {
      loadListFromJson(jo, field);
    } else if (isJsonable(field.getType())) {
      loadJsonable(jo, field);
    } else if (field.getType().isEnum()) {
      loadEnum(jo.get(fieldName).toString(), field);
    } else
      field.set(this, jo.get(fieldName));
  }

  /**
   * Load list from json.
   * 
   * @param jo
   *          the jo
   * @param field
   *          the field
   * @throws JSONException
   *           the jSON exception
   */
  @SuppressWarnings("unchecked")
  private void loadListFromJson(JSONObject jo, Field field)
      throws JSONException {
    String fieldName = field.getName();
    if (isListOfListsOfJsonables(field)) {
      loadListOfListsOfJsonables(jo.getJSONArray(fieldName),
          (List<List<Jsonable>>) getFieldValue(field));
    } else if (isListOfJsonables(field)) {
      loadListOfJsonables(jo.getJSONObject(fieldName),
          (List<Jsonable>) getFieldValue(field), getParamTree(field));
    } else {
      loadListOfStrings(jo.getJSONArray(fieldName),
          (List<String>) getFieldValue(field));
    }
  }

  /**
   * Load jsonable.
   * 
   * @param jo
   *          the jo
   * @param field
   *          the field
   * @throws JSONException
   *           the jSON exception
   * @throws IllegalArgumentException
   *           the illegal argument exception
   * @throws IllegalAccessException
   *           the illegal access exception
   */
  private void loadJsonable(JSONObject jo, Field field) throws JSONException {
    JSONObject JO = jo.getJSONObject(field.getName());
    Jsonable jsonable = (Jsonable) getFieldValue(field);
    jsonable.loadFromJson(JO, field.getName());
  }

  private void loadEnum(String value, Field field) {
    try {
      Method m = field.getType().getMethod("getValueOf", String.class);
      field.set(this, m.invoke(null, value));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Load from json.
   * 
   * @param jo
   *          the jo
   * @param Name
   *          the name
   */
  public void loadFromJson(String jo, String Name) {
    try {
      loadFromJson(new JSONObject(jo), Name);
    } catch (JSONException ignore) {
      // Do Nothing
    }
  }

  /**
   * Load from json.
   * 
   * @param jo
   *          the jo
   */
  public void loadFromJson(String jo) {
    loadFromJson(jo, "");
  }

  /**
   * Gets the fields in this object of "type".
   * 
   * @param type
   *          The type of variable we want(i.e. int, String, List, etc.). Use
   *          null to get all fields.
   * @param includeParents
   *          Whether to include fields from superclasses.
   * @return the fields of this class that match type.
   */
  protected List<Field> getFields(Class<?> type, boolean includeParents) {
    return getFields(this.getClass(), type, includeParents);
  }

  /**
   * Load from json.
   * 
   * @param jo
   *          the jo
   * @return the jSO nable
   */
  public Jsonable loadFromJson(JSONObject jo) {
    return loadFromJson(jo, "");
  }

  /**
   * Handle list.
   * 
   * @param field
   *          the field
   * @param object
   *          the object
   * @return the object
   * @throws JSONException
   *           the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static Object handleList(Field field, Object object)
      throws JSONException {
    List<Class<?>> paramTree = getParamTree(field);
    if (paramTree.size() == 1) {
      return handleSimpleList(paramTree.get(0), object);
    } else if (isListOfListsOfJsonables(field)) {
      return handleListOfListsOfJsonables((List<List<Jsonable>>) object);
    }
    return "List error";
  }

  /**
   * Gets the fields.
   * 
   * @param theClass
   *          the the class
   * @param type
   *          the type
   * @param includeParents
   *          the include parents
   * @return the fields
   */
  private List<Field> getFields(Class<?> theClass, Class<?> type,
      boolean includeParents) {
    Field[] fields = theClass.getDeclaredFields();
    ArrayList<Field> fieldList = new ArrayList<Field>();
    for (int i = 0; i < fields.length; i++) {
      if ((fields[i].getType() == type || type == null)
          && goodFieldName(fields[i].getName()))
        fieldList.add(fields[i]);
    }
    if (includeParents && theClass != Jsonable.class) {
      fieldList.addAll(getFields(theClass.getSuperclass(), null, true));
    }
    return fieldList;
  }

  /**
   * Good field name.
   * 
   * @param name
   *          the name
   * @return true, if successful
   */
  private static boolean goodFieldName(String name) {
    return name.charAt(0) != '$' && !name.equals("serialVersionUID");
  }

  /**
   * Load from json.
   * 
   * @param <T>
   *          the generic type
   * @param jo
   *          the jo
   * @param Name
   *          the name
   * @param type
   *          the class name
   * @return the jsonable
   */
  @SuppressWarnings("unchecked")
  public static <T extends Jsonable> T loadFromJson(JSONObject jo, String Name,
      Class<T> type) {
    T jsonable = null;
    try {
      jsonable = type.newInstance();
      jsonable.loadFromJson(jo, Name);
    } catch (Exception e) {
      try {// Classes whose constructors require parameters must implement
           // their own loadFromJson Method
        jsonable = (T) type.getMethod("loadFromJson", JSONObject.class,
            String.class, Class.class).invoke(null, jo, Name, type);
      } catch (Exception ignore) {
        // Do Nothing
      }
    }
    return jsonable;
  }

  /**
   * Load from json.
   * 
   * @param jo
   *          the jo
   * @param Name
   *          the name
   * @param className
   *          the class name
   * @return the jsonable
   */
  public static <T extends Jsonable> T loadFromJson(String jo, String Name,
      Class<T> className) {
    T jsonable = null;
    try {
      jsonable = loadFromJson(new JSONObject(jo), Name, className);
    } catch (JSONException ignore) {
      // Do Nothing
    }
    return jsonable;
  }

  /**
   * Load string array.
   * 
   * @param ja
   *          the ja
   * @param list
   *          the list
   */
  private static void loadListOfStrings(JSONArray ja, List<String> list) {
    list.clear();
    if (ja.length() > 0) {
      for (int i = 0; i < ja.length(); i++) {
        try {
          list.add(ja.getString(i));
        } catch (JSONException ignore) {
          // Do Nothing
        }
      }
    }
  }

  /**
   * Load list of lists of jsonables.
   * 
   * @param ja
   *          the ja
   * @param list
   *          the list
   * @throws JSONException
   *           the jSON exception
   */
  private static void loadListOfListsOfJsonables(JSONArray ja,
      List<List<Jsonable>> list) throws JSONException {
    for (int i = 0; i < ja.length(); i++) {
      JSONObject jo = ja.getJSONObject(i);
      String[] names = JSONObject.getNames(jo);
      java.util.Arrays.sort(names);
      for (int j = 0; j < names.length; j++) {
        list.get(i).get(j).loadFromJson(jo.getJSONObject(names[j]), "");
      }
    }
  }

  /**
   * List to json.
   * 
   * @param list
   *          the list
   * @return the jSON array
   */
  private static JSONArray stringListToJSONArray(List<String> list) {
    JSONArray JA = new JSONArray();
    for (Iterator<String> i = list.iterator(); i.hasNext();) {
      JA.put(i.next());
    }
    return JA;
  }

  /**
   * List to json object.
   * 
   * @param list
   *          the list
   * @return the jSON object
   * @throws JSONException
   *           the jSON exception
   */
  private static JSONObject listOfJsonablesToJsonObject(List<Jsonable> list)
      throws JSONException {
    JSONObject JO = new JSONObject();
    Iterator<Jsonable> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
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
   * Handle simple list.
   * 
   * @param listType
   *          the list type
   * @param object
   *          the object
   * @return the object
   * @throws JSONException
   *           the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static Object handleSimpleList(Class<?> listType, Object object)
      throws JSONException {
    if (listType.equals(String.class))
      return stringListToJSONArray((List<String>) object);
    else if (isJsonable(listType)) {
      return listOfJsonablesToJsonObject((List<Jsonable>) object);
    }
    return "List error";
  }

  /**
   * Handle list of lists of jsonables.
   * 
   * @param list
   *          the list
   * @return the object
   */
  private static Object handleListOfListsOfJsonables(List<List<Jsonable>> list) {
    JSONArray retVal = new JSONArray();
    for (int i = 0; i < list.size(); i++) {
      try {
        retVal.put(listOfJsonablesToJsonObject(list.get(i)));
      } catch (JSONException ignore) {
        // Do Nothing
      }
    }
    return retVal;
  }

  /**
   * Load list of jsonables.
   * 
   * @param jo
   *          the jo
   * @param list
   *          the list
   * @param paramTree
   *          the param tree
   */
  @SuppressWarnings("unchecked")
  private static void loadListOfJsonables(JSONObject jo, List<Jsonable> list,
      List<Class<?>> paramTree) {
    String[] names = JSONObject.getNames(jo);
    try {
      if (names != null) {
        for (int i = 0; i < names.length; i++) {
          Jsonable item = loadFromJson(jo.getJSONObject(names[i]), "",
              (Class<? extends Jsonable>) paramTree.get(0));
          item.name = names[i];
          list.add(item);

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static final Pattern p = Pattern.compile("<(.*)>");

  /**
   * Checks if is list.
   * 
   * @param t
   *          the t
   * @return true, if is list
   */
  private static boolean isList(Class<?> t) {
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
   *          the f
   * @return the param tree
   */
  private static List<Class<?>> getParamTree(Field f) {
    List<Class<?>> tree = new ArrayList<Class<?>>();
    String GenericType = (f.getGenericType().toString());
    GenericType = extractParam(GenericType);
    while (GenericType != "") {
      try {
        tree.add(Class.forName(removeParam(GenericType)));
      } catch (ClassNotFoundException e) {
        tree.add(Object.class);
      }
      GenericType = extractParam(GenericType);
    }
    return tree;
  }

  private static String extractParam(String GenericType) {
    Matcher result = p.matcher(GenericType);
    if (result.find()) {
      return result.group(1);
    }
    return "";
  }

  /**
   * Removes the param.
   * 
   * @param GenericType
   *          the generic type
   * @return the string
   */
  private static String removeParam(String GenericType) {
    return p.split(GenericType)[0];
  }

  /**
   * Checks if is jsonable.
   * 
   * @param t
   *          the t
   * @return true, if is jsonable
   */
  private static boolean isJsonable(Class<?> t) {
    Class<?> type = t;
    while (type.getGenericSuperclass() != null) {
      try {
        type = (Class<?>) type.getGenericSuperclass();
      } catch (Exception e) {
        return false;
      }
      if (type.equals(Jsonable.class))
        return true;
    }
    return false;
  }

  /**
   * Checks if is list of lists of jsonables.
   * 
   * @param field
   *          the field
   * @return true, if is list of lists of jsonables
   */
  private static boolean isListOfJsonables(Field field) {
    List<Class<?>> paramTree = getParamTree(field);
    return paramTree.size() == 1 && isJsonable(paramTree.get(0));
  }

  /**
   * Checks if is list of lists of jsonables.
   * 
   * @param field
   *          the field
   * @return true, if is list of lists of jsonables
   */
  private static boolean isListOfListsOfJsonables(Field field) {
    List<Class<?>> paramTree = getParamTree(field);
    return paramTree.size() == 2 && isList(paramTree.get(0))
        && isJsonable(paramTree.get(1));
  }

}