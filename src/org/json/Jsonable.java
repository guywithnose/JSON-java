/*
 * File:         Jsonable.java
 * Author:       JSON.org
 */
package org.json;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.FileReader;

// TODO: Auto-generated Javadoc
/**
 * The Class Jsonable.
 */
public abstract class Jsonable implements JSONString {

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
    Collection<Field> allFields = getFields();
    for (Field field : allFields) {
      try {
        if (field.getName() != "pseudoFields") {
          jo.put(field.getName(), fieldToJson(field));
        }
      } catch (Exception ignore) {
        // Do Nothing
      }
    }
    return jo;
  }

  /**
   * Get all the fields of this object.
   * 
   * @return all the fields of this object.
   */
  public Collection<Field> getFields() {
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
    if (isMap(field.getType())) {
      return handleMap(field, getFieldValue(field));
    } else if (isCollection(field.getType())) {
      return handleCollection(field, getFieldValue(field));
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
    Object returnValue = null;
    try {
      returnValue = get(field);
    } catch (Exception e) {
      try {
        returnValue = field.get(this);
      } catch (Exception e1) {
        returnValue = getFieldValueByMethod(field);
      }
    }
    if (returnValue == null) {
      try {
        Object newInstance = field.getType().newInstance();
        field.set(this, newInstance);
        returnValue = newInstance;
      } catch (Exception e) {
        // Do Nothing
      }
    }
    return returnValue;
  }

  /**
   * This method is used to access private variables
   * In order for it to work it must be overloaded in the subclass
   * <pre>
   * protected Object get(Field field) throws IllegalArgumentException,
   * IllegalAccessException {
   * return field.get(this);
   * }</pre>
   *
   * @param field the field
   * @return The value of the parameter
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  protected Object get(Field field) throws IllegalArgumentException,
      IllegalAccessException {
    return field.get(this);
  }

  /**
   * This method is used to set private variables
   * In order for it to work it must be overloaded in the subclass
   * <pre>
   * protected void set(Field field, Object value) throws IllegalArgumentException,
   * IllegalAccessException {
   *   field.set(this, value);
   * }</pre>
   * 
   * @param field the field
   * @param value the value
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  protected void set(Field field, Object value) throws IllegalArgumentException,
      IllegalAccessException {
    field.set(this, value);
  }

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
      return null;
    }
  }

  /**
   * Load from json.
   *
   * @param jo the JSONObject we are loading
   * @return the Jsonable object we loaded
   */
  public Jsonable loadFromJson(JSONObject jo) {
    Collection<Field> allFields = getFields();
    for (Field field : allFields) {
      try {
        if (jo.has(field.getName())) {
          loadFieldFromJson(jo, field);
        }
      } catch (Exception ignore) {
        // Do Nothing
      }
    }
    for (String fieldName : getPseudoFields()) {
      if(jo.has(fieldName)) {
        try {
          handlePseudoField(fieldName, jo.get(fieldName));
        } catch (JSONException e) {
          // DO Nothing
        }
      }
    }
    return this;
  }

  /**
   * Gets the pseudo fields.
   *
   * @return the pseudo fields
   */
  @SuppressWarnings("static-method")
  protected String[] getPseudoFields() {
    return new String[0];
  }

  /**
   * Handle pseudo field.
   *
   * @param fieldName the field name
   * @param value the value
   */
  @SuppressWarnings({ "static-method", "unused" })
  protected void handlePseudoField(String fieldName, Object value) {
    return;
  }

  /**
   * Load field from json.
   *
   * @param jo the jo
   * @param field the field
   * @throws JSONException the jSON exception
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void loadFieldFromJson(JSONObject jo, Field field)
      throws JSONException, IllegalArgumentException, IllegalAccessException {
    String fieldName = field.getName();
    try {
      if (isMap(field.getType())) {
        loadMapFromJson(jo, field);
      } else if (isCollection(field.getType())) {
        loadCollectionFromJson(jo, field);
      } else if (isJsonable(field.getType())) {
        loadJsonable(jo, field);
      } else if (field.getType().isEnum()) {
        loadEnum(jo.get(fieldName).toString(), field);
      } else if (field.getType().equals(float.class)) {
        set(field, (float) jo.getDouble(fieldName));
      } else {
        set(field, jo.get(fieldName));
      }
    } catch (Exception e) {
      setFieldValueByMethod(jo, field);
    }
  }

  /**
   * Gets the field value by method.
   *
   * @param jo the jo
   * @param field the field
   * @return the field value by method
   */
  protected void setFieldValueByMethod(JSONObject jo, Field field) {
    try {
      Method m = this.getClass().getMethod(
          "set" + field.getName().substring(0, 1).toUpperCase()
              + field.getName().substring(1), field.getType());
      Object value = jo.get(field.getName());
      if (field.getType().equals(float.class)) {
        value =  (float) jo.getDouble(field.getName());
      }
      m.invoke(this, new Object[] {value});
    } catch (Exception e) {
      // Do Nothing
    }
  }

  /**
   * Load Collection from json.
   * 
   * @param jo
   *          the jo
   * @param field
   *          the field
   * @throws JSONException
   *           the jSON exception
   */
  @SuppressWarnings("unchecked")
  private void loadCollectionFromJson(JSONObject jo, Field field)
      throws JSONException {
    String fieldName = field.getName();
    if (hasCollectionsOfJsonables(field)) {
      loadCollectionOfCollectionsOfJsonables(jo.getJSONArray(fieldName),
          (Collection<Collection<Jsonable>>) getFieldValue(field), getParamTree(field));
    } else if (hasMapsOfJsonables(field)) {
      loadCollectionOfMapsOfJsonables(jo.getJSONArray(fieldName),
          (Collection<Map<String, Jsonable>>) getFieldValue(field), getParamTree(field));
    } else if (hasJsonables(field)) {
      loadCollectionOfJsonables(jo.getJSONArray(fieldName),
          (Collection<Jsonable>) getFieldValue(field), getParamTree(field));
    } else {
      loadCollectionOfStrings(jo.getJSONArray(fieldName),
          (Collection<String>) getFieldValue(field));
    }
  }

  /**
   * Load map from json.
   *
   * @param jo the jo
   * @param field the field
   * @throws JSONException the jSON exception
   */
  @SuppressWarnings("unchecked")
  private void loadMapFromJson(JSONObject jo, Field field)
      throws JSONException {
    String fieldName = field.getName();
    if (hasCollectionsOfJsonables(field)) {
      loadMapOfCollectionsOfJsonables(jo.getJSONObject(fieldName),
          (Map<String, Collection<Jsonable>>) getFieldValue(field), getParamTree(field));
    } else if (hasMapsOfJsonables(field)) {
      loadMapOfMapsOfJsonables(jo.getJSONObject(fieldName),
          (Map<String, Map<String, Jsonable>>) getFieldValue(field), getParamTree(field));
    } else if (hasJsonables(field)) {
      loadMapOfJsonables(jo.getJSONObject(fieldName),
          (Map<String, Jsonable>) getFieldValue(field), getParamTree(field));
    } else {
      loadMapOfStrings(jo.getJSONObject(fieldName),
          (Map<String, String>) getFieldValue(field));
    }
  }

  /**
   * Load jsonable.
   *
   * @param jo the jo
   * @param field the field
   * @throws JSONException the jSON exception
   */
  private void loadJsonable(JSONObject jo, Field field) throws JSONException {
    JSONObject JO = jo.getJSONObject(field.getName());
    Jsonable jsonable = (Jsonable) getFieldValue(field);
    jsonable.loadFromJson(JO);
  }

  /**
   * Load enum.
   *
   * @param value the value
   * @param field the field
   */
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
   * @param jo the jo
   */
  public void loadFromJson(String jo) {
    try {
      loadFromJson(new JSONObject(jo));
    } catch (JSONException ignore) {
      // Do Nothing
    }
  }

  /**
   * Gets the fields in this object of "type".
   * 
   * @param type
   *          The type of variable we want(i.e. int, String, Collection, etc.). Use
   *          null to get all fields.
   * @param includeParents
   *          Whether to include fields from superclasses.
   * @return the fields of this class that match type.
   */
  protected Collection<Field> getFields(Class<?> type, boolean includeParents) {
    return getFields(this.getClass(), type, includeParents);
  }

  /**
   * Handle Collection.
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
  private static Object handleCollection(Field field, Object object)
      throws JSONException {
    List<Class<?>> paramTree = getParamTree(field);
    if (paramTree.size() == 1) {
      return handleSimpleCollection(paramTree.get(0), object);
    } else if (hasCollectionsOfJsonables(field)) {
      return handleCollectionOfCollectionsOfJsonables((Collection<Collection<Jsonable>>) object);
    } else if (hasMapsOfJsonables(field)) {
      return handleCollectionOfMapsOfJsonables((Collection<Map<String, Jsonable>>) object);
    }
    return "Collection error";
  }
  

  /**
   * Handle map.
   *
   * @param field the field
   * @param object the object
   * @return the object
   * @throws JSONException the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static Object handleMap(Field field, Object object)
      throws JSONException {
    List<Class<?>> paramTree = getParamTree(field);
    if (paramTree.size() == 1) {
      return handleSimpleMap(paramTree.get(0), object);
    } else if (hasCollectionsOfJsonables(field)) {
      return handleMapOfCollectionsOfJsonables((Map<String, Collection<Jsonable>>) object);
    } else if (hasMapsOfJsonables(field)) {
      return handleMapOfMapsOfJsonables((Map<String, Map<String, Jsonable>>) object);
    }
    return "Collection error";
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
  private Collection<Field> getFields(Class<?> theClass, Class<?> type,
      boolean includeParents) {
    Field[] fields = theClass.getDeclaredFields();
    ArrayList<Field> fieldCollection = new ArrayList<Field>();
    for (int i = 0; i < fields.length; i++) {
      if ((fields[i].getType() == type || type == null)
          && goodFieldName(fields[i].getName()))
        fieldCollection.add(fields[i]);
    }
    if (includeParents && theClass != Jsonable.class) {
      fieldCollection.addAll(getFields(theClass.getSuperclass(), null, true));
    }
    return fieldCollection;
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
   * Classes whose constructors require parameters must override
   * this method.
   *
   * @param <T> the generic type
   * @param jo the jo
   * @param type the class name
   * @return the jsonable
   */
  @SuppressWarnings("unchecked")
  public static <T extends Jsonable> T loadFromJson(JSONObject jo,
      Class<T> type) {
    T jsonable = null;
    try {
      jsonable = type.newInstance();
      jsonable.loadFromJson(jo);
    } catch (Exception e) {
      try {
        jsonable = (T) type.getMethod("instantiateFromJson", JSONObject.class,
            Class.class).invoke(null, jo, type);
      } catch (Exception ignore) {
        // Do Nothing
      }
    }
    return jsonable;
  }

  /**
   * Load from json.
   *
   * @param <T> the generic type
   * @param jo the jo
   * @param className the class name
   * @return the jsonable
   */
  public static <T extends Jsonable> T loadFromJson(String jo,
      Class<T> className) {
    T jsonable = null;
    try {
      jsonable = loadFromJson(new JSONObject(jo), className);
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
   * @param Collection
   *          the Collection
   */
  private static void loadCollectionOfStrings(JSONArray ja, Collection<String> Collection) {
    Collection.clear();
    if (ja.length() > 0) {
      for (int i = 0; i < ja.length(); i++) {
        try {
          Collection.add(ja.getString(i));
        } catch (JSONException ignore) {
          // Do Nothing
        }
      }
    }
  }

  /**
   * Load string array.
   * 
   * @param jo
   *          the jo
   * @param map
   *          the Collection
   */
  private static void loadMapOfStrings(JSONObject jo, Map<String, String> map) {
    map.clear();
    if (jo.length() > 0) {
      String[] names = JSONObject.getNames(jo);
      for (int i = 0; i < names.length; i++) {
        try {
          map.put(names[i], jo.getString(names[i]));
        } catch (JSONException ignore) {
          // Do Nothing
        }
      }
    }
  }

  /**
   * Load Collection of Collections of jsonables.
   *
   * @param ja the ja
   * @param Collection the Collection
   * @param paramTree the param tree
   * @throws JSONException the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static void loadCollectionOfCollectionsOfJsonables(JSONArray ja,
      Collection<Collection<Jsonable>> Collection, List<Class<?>> paramTree) throws JSONException {
    for (int i = 0; i < ja.length(); i++) {
      JSONArray innerJa = ja.getJSONArray(i);
      Collection<Jsonable> newCollection = new ArrayList<Jsonable>(innerJa.length());
      Collection.add(newCollection);
      for (int j = 0; j < innerJa.length(); j++) {
        Jsonable item = loadFromJson(innerJa.getJSONObject(j),
            (Class<? extends Jsonable>) paramTree.get(1));
        newCollection.add(item);
      }
    }
  }

  /**
   * Load collection of maps of jsonables.
   *
   * @param ja the ja
   * @param Collection the collection
   * @param paramTree the param tree
   * @throws JSONException the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static void loadCollectionOfMapsOfJsonables(JSONArray ja,
      Collection<Map<String, Jsonable>> Collection, List<Class<?>> paramTree) throws JSONException {
    for (int i = 0; i < ja.length(); i++) {
      JSONObject jo = ja.getJSONObject(i);
      Map<String, Jsonable> newMap = new HashMap<String, Jsonable>(jo.length());
      Collection.add(newMap);
      for (String name : JSONObject.getNames(jo)) {
        Jsonable item = loadFromJson(jo.getJSONObject(name),
            (Class<? extends Jsonable>) paramTree.get(1));
        newMap.put(name, item);
      }
    }
  }

  /**
   * Load map of collections of jsonables.
   *
   * @param jo the jo
   * @param map the map
   * @param paramTree the param tree
   * @throws JSONException the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static void loadMapOfCollectionsOfJsonables(JSONObject jo,
      Map<String, Collection<Jsonable>> map, List<Class<?>> paramTree) throws JSONException {
    String[] names = JSONObject.getNames(jo);
    for (int i = 0; i < names.length; i++) {
      JSONArray ja = jo.getJSONArray(names[i]);
      Collection<Jsonable> newCollection = new ArrayList<Jsonable>(ja.length());
      map.put(names[i], newCollection);
      for (int j = 0; j < ja.length(); j++) {
        Jsonable item = loadFromJson(ja.getJSONObject(j),
            (Class<? extends Jsonable>) paramTree.get(1));
        newCollection.add(item);
      }
    }
  }

  /**
   * Load map of maps of jsonables.
   *
   * @param jo the jo
   * @param map the map
   * @param paramTree the param tree
   * @throws JSONException the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static void loadMapOfMapsOfJsonables(JSONObject jo,
      Map<String, Map<String, Jsonable>> map, List<Class<?>> paramTree) throws JSONException {
    for (String name : JSONObject.getNames(jo)) {
      JSONObject innerJo = jo.getJSONObject(name);
      Map<String, Jsonable> newMap = new HashMap<String, Jsonable>(innerJo.length());
      map.put(name, newMap);
      for (String innerName : JSONObject.getNames(innerJo)) {
        Jsonable item = loadFromJson(innerJo.getJSONObject(innerName),
            (Class<? extends Jsonable>) paramTree.get(1));
        newMap.put(innerName, item);
      }
    }
  }

  /**
   * Collection to json.
   * 
   * @param Collection
   *          the Collection
   * @return the jSON array
   */
  private static JSONArray stringCollectionToJSONArray(Collection<String> Collection) {
    JSONArray JA = new JSONArray();
    for (Iterator<String> i = Collection.iterator(); i.hasNext();) {
      JA.put(i.next());
    }
    return JA;
  }

  /**
   * String map to json object.
   *
   * @param map the map
   * @return the jSON object
   * @throws JSONException the jSON exception
   */
  private static JSONObject stringMapToJSONObject(Map<String, String> map) throws JSONException {
    JSONObject JO = new JSONObject();
    for (String name : map.keySet()) {
      JO.put(name, map.get(name));
    }
    return JO;
  }

  /**
   * Collection to json object.
   * 
   * @param Collection
   *          the Collection
   * @return the jSON object
   * @throws JSONException
   *           the jSON exception
   */
  private static JSONArray CollectionOfJsonablesToJsonArray(Collection<Jsonable> Collection)
      throws JSONException {
    JSONArray JO = new JSONArray();
    for (Jsonable item : Collection) {
      JO.put(item.toJSON());
    }
    return JO;
  }  

  /**
   * Map of jsonables to json object.
   *
   * @param map the map
   * @return the jSON object
   * @throws JSONException the jSON exception
   */
  private static JSONObject mapOfJsonablesToJsonObject(Map<String, Jsonable> map)
      throws JSONException {
    JSONObject JO = new JSONObject();
    for (String name : map.keySet()) {
      Jsonable item = map.get(name);
      JO.put(name, item.toJSON());
    }
    return JO;
  }

  /**
   * Handle simple Collection.
   * 
   * @param CollectionType
   *          the Collection type
   * @param object
   *          the object
   * @return the object
   * @throws JSONException
   *           the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static Object handleSimpleCollection(Class<?> CollectionType, Object object)
      throws JSONException {
    if (CollectionType.equals(String.class))
      return stringCollectionToJSONArray((Collection<String>) object);
    else if (isJsonable(CollectionType)) {
      return CollectionOfJsonablesToJsonArray((Collection<Jsonable>) object);
    }
    return "Collection error";
  }
  
  /**
   * Handle simple map.
   *
   * @param CollectionType the collection type
   * @param object the object
   * @return the object
   * @throws JSONException the jSON exception
   */
  @SuppressWarnings("unchecked")
  private static Object handleSimpleMap(Class<?> CollectionType, Object object)
      throws JSONException {
    if (CollectionType.equals(String.class))
      return stringMapToJSONObject((Map<String, String>) object);
    else if (isJsonable(CollectionType)) {
      return mapOfJsonablesToJsonObject((Map<String, Jsonable>) object);
    }
    return "Collection error";
  }

  /**
   * Handle Collection of Collections of jsonables.
   * 
   * @param collection
   *          the Collection
   * @return the object
   */
  private static JSONArray handleCollectionOfCollectionsOfJsonables(Collection<Collection<Jsonable>> collection) {
    JSONArray retVal = new JSONArray();
    for (Collection<Jsonable> innerCollection : collection) {
      try {
        retVal.put(CollectionOfJsonablesToJsonArray(innerCollection));
      } catch (JSONException ignore) {
        // Do Nothing
      }
    }
    return retVal;
  }

  /**
   * Handle collection of maps of jsonables.
   *
   * @param collection the collection
   * @return the jSON array
   */
  private static JSONArray handleCollectionOfMapsOfJsonables(Collection<Map<String, Jsonable>> collection) {
    JSONArray retVal = new JSONArray();
    for (Map<String, Jsonable> map : collection) {
      try {
        retVal.put(mapOfJsonablesToJsonObject(map));
      } catch (JSONException ignore) {
        // Do Nothing
      }
    }
    return retVal;
  }

  /**
   * Handle map of collections of jsonables.
   *
   * @param map the map
   * @return the jSON object
   */
  private static JSONObject handleMapOfCollectionsOfJsonables(Map<String, Collection<Jsonable>> map) {
    JSONObject retVal = new JSONObject();
    for(String name : map.keySet()) {
      try {
        retVal.put(name, CollectionOfJsonablesToJsonArray(map.get(name)));
      } catch (JSONException ignore) {
        // Do Nothing
      }
    }
    return retVal;
  }

  /**
   * Handle map of maps of jsonables.
   *
   * @param map the map
   * @return the jSON object
   */
  private static JSONObject handleMapOfMapsOfJsonables(Map<String, Map<String, Jsonable>> map) {
    JSONObject retVal = new JSONObject();
    for(String name : map.keySet()) {
      try {
        retVal.put(name, mapOfJsonablesToJsonObject(map.get(name)));
      } catch (JSONException ignore) {
        // Do Nothing
      }
    }
    return retVal;
  }

  /**
   * Load Collection of jsonables.
   * 
   * @param ja
   *          the jo
   * @param Collection
   *          the Collection
   * @param paramTree
   *          the param tree
   */
  @SuppressWarnings("unchecked")
  private static void loadCollectionOfJsonables(JSONArray ja, Collection<Jsonable> Collection,
      List<Class<?>> paramTree) {
    try {
      for (int i = 0; i < ja.length(); i++) {
        Jsonable item = loadFromJson(ja.getJSONObject(i),
            (Class<? extends Jsonable>) paramTree.get(0));
        Collection.add(item);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Load map of jsonables.
   *
   * @param jo the jo
   * @param map the map
   * @param paramTree the param tree
   */
  @SuppressWarnings("unchecked")
  private static void loadMapOfJsonables(JSONObject jo, Map<String, Jsonable> map,
      List<Class<?>> paramTree) {
    String[] names = JSONObject.getNames(jo);
    try {
      if (names != null) {
        for (int i = 0; i < names.length; i++) {
          Jsonable item = loadFromJson(jo.getJSONObject(names[i]),
              (Class<? extends Jsonable>) paramTree.get(0));
          map.put(names[i], item);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if is Collection.
   * 
   * @param t
   *          the t
   * @return true, if is Collection
   */
  private static boolean isCollection(Class<?> t) {
    return isInterfaceOf(t, "java.util.Collection");
  }

  /**
   * Checks if t is a map.
   *
   * @param t the t
   * @return true, if t is a map
   */
  private static boolean isMap(Class<?> t) {
    return isInterfaceOf(t, "java.util.Map");
  }

  /**
   * Checks if Interface is an interface of t.
   *
   * @param t the t
   * @param Interface the interface
   * @return true, if  Interface is an interface of t
   */
  private static boolean isInterfaceOf(Class<?> t, String Interface) {
    if(t.toString().contains(Interface)) {
      return true;
    }
    Type[] thing = t.getGenericInterfaces();
    for (int i = 0; i < thing.length; i++) {
      if (thing[i].toString().contains(Interface)) {
        return true;
      }
    }
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

  /**
   * Extract param.
   *
   * @param GenericType the generic type
   * @return the string
   */
  private static String extractParam(String GenericType) {
    final Pattern p = Pattern.compile("<([^,<]*, )?((.*))>");
    Matcher result = p.matcher(GenericType);
    if (result.find()) {
      return result.group(3);
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
    final Pattern p = Pattern.compile("<(.*)>");
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
   * Checks if is Collection of Collections of jsonables.
   * 
   * @param field
   *          the field
   * @return true, if is Collection of Collections of jsonables
   */
  private static boolean hasJsonables(Field field) {
    List<Class<?>> paramTree = getParamTree(field);
    return paramTree.size() == 1 && isJsonable(paramTree.get(0));
  }

  /**
   * Checks if is Collection of Collections of jsonables.
   * 
   * @param field
   *          the field
   * @return true, if is Collection of Collections of jsonables
   */
  private static boolean hasCollectionsOfJsonables(Field field) {
    List<Class<?>> paramTree = getParamTree(field);
    return paramTree.size() == 2 && isCollection(paramTree.get(0))
        && isJsonable(paramTree.get(1));
  }

  /**
   * Checks for maps of jsonables.
   *
   * @param field the field
   * @return true, if successful
   */
  private static boolean hasMapsOfJsonables(Field field) {
    List<Class<?>> paramTree = getParamTree(field);
    return paramTree.size() == 2 && isMap(paramTree.get(0))
        && isJsonable(paramTree.get(1));
  }

  /**
   * Load from file.
   *
   * @param <T> the generic type
   * @param file the file
   * @param className the class name
   * @return the t
   */
  public static <T extends Jsonable> T loadFromFile(File file, Class<T> className)
  {
    return loadFromJson(FileReader.getFileContents(file), className);
  }

  /**
   * Load from file.
   *
   * @param <T> the generic type
   * @param fileName the file name
   * @param className the class name
   * @return the t
   */
  public static <T extends Jsonable> T loadFromFile(String fileName, Class<T> className)
  {
    File file = new File(fileName);
    return loadFromFile(file, className);
  }
  
}