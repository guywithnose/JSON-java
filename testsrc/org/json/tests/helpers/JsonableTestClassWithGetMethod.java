/*
 * File:         JsonableTestClassWithoutGetMethod.java
 * Author:       JSON.org
 */
package org.json.tests.helpers;

import java.lang.reflect.Field;

import org.json.Jsonable;

public class JsonableTestClassWithGetMethod extends Jsonable {

  public int publicInt;
  
  public float publicFloat;
  
  public double publicDouble;
  
  public String publicString;

  @SuppressWarnings("unused")
  private int privateInt;

  @SuppressWarnings("unused")
  private float privateFloat;

  @SuppressWarnings("unused")
  private double privateDouble;

  @SuppressWarnings("unused")
  private String privateString;

  /* (non-Javadoc)
   * @see org.json.Jsonable#get(java.lang.reflect.Field)
   */
  @Override
  protected Object get(Field field) throws IllegalArgumentException,
      IllegalAccessException {
    try {
      return field.get(this);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
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