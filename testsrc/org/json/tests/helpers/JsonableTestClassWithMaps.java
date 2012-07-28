package org.json.tests.helpers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.Jsonable;

public class JsonableTestClassWithMaps extends Jsonable {
  
  public Map<String, ValueObject> JsonableMap = new HashMap<String, ValueObject>();
  
  public Map<String, String> stringMap = new HashMap<String, String>();
  
  public Map<String, List<ValueObject>> JsonableMapGrid = new HashMap<String, List<ValueObject>>();
  
  public Map<String, Map<String, ValueObject>> JsonableMapMap = new HashMap<String, Map<String, ValueObject>>();
  
  /* (non-Javadoc)
   * @see org.json.Jsonable#get(java.lang.reflect.Field)
   */
  @Override
  protected Object get(Field field) throws IllegalArgumentException,
      IllegalAccessException {
    return field.get(this);
  }

  /* (non-Javadoc)
   * @see org.json.Jsonable#get(java.lang.reflect.Field)
   */
  @Override
  protected void set(Field field, Object value) throws IllegalArgumentException,
      IllegalAccessException {
    field.set(this, value);
  }
  
}
