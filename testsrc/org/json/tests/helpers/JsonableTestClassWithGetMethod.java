/*
 * File:         JsonableTestClassWithGetMethod.java
 * Author:       JSON.org
 */
package org.json.tests.helpers;

import java.lang.reflect.Field;

import org.json.Jsonable;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonableTestClassWithGetMethod.
 */
public class JsonableTestClassWithGetMethod extends Jsonable {

  /** The public int. */
  public int publicInt;
  
  /** The public float. */
  public float publicFloat;
  
  /** The public double. */
  public double publicDouble;
  
  /** The public string. */
  public String publicString;

  /** The private int. */
  @SuppressWarnings("unused")
  private int privateInt;

  /** The private float. */
  @SuppressWarnings("unused")
  private float privateFloat;

  /** The private double. */
  @SuppressWarnings("unused")
  private double privateDouble;

  /** The private string. */
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