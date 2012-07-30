/*
 * File:         JsonableTestClassWithList.java
 * Author:       JSON.org
 */



package org.json.tests.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.Jsonable;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonableTestClassWithList.
 */
public class JsonableTestClassWithList extends Jsonable {
  
  /** The Jsonable list. */
  public List<ValueObject> JsonableList = new ArrayList<ValueObject>();
  
  /** The string array. */
  public List<String> stringArray = new ArrayList<String>();
  
  /** The Jsonable grid. */
  public List<List<ValueObject>> JsonableGrid = new ArrayList<List<ValueObject>>();
  
  /** The Jsonable list map. */
  public List<Map<String, ValueObject>> JsonableListMap = new ArrayList<Map<String, ValueObject>>();
  
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
