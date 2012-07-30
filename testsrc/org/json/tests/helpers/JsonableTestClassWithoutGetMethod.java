/*
 * File:         JsonableTestClassWithoutGetMethod.java
 * Author:       JSON.org
 */



package org.json.tests.helpers;

import org.json.Jsonable;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonableTestClassWithoutGetMethod.
 */
public class JsonableTestClassWithoutGetMethod extends Jsonable {

  /** The public int. */
  public int publicInt;
  
  /** The public float. */
  public float publicFloat;
  
  /** The public double. */
  public double publicDouble;
  
  /** The public string. */
  public String publicString;

  /** The private int. */
  private int privateInt;
  
  /** The private float. */
  private float privateFloat;
  
  /** The private double. */
  private double privateDouble;
  
  /** The private string. */
  private String privateString;

  /**
   * Gets the private int.
   *
   * @return the private int
   */
  public int getPrivateInt() {
    return privateInt;
  }

  /**
   * Sets the private int.
   *
   * @param PrivateInt the new private int
   */
  public void setPrivateInt(int PrivateInt) {
    privateInt = PrivateInt;
  }

  /**
   * Gets the private float.
   *
   * @return the private float
   */
  public float getPrivateFloat() {
    return privateFloat;
  }

  /**
   * Sets the private float.
   *
   * @param PrivateFLoat the new private float
   */
  public void setPrivateFloat(float PrivateFLoat) {
    privateFloat = PrivateFLoat;
  }

  /**
   * Gets the private double.
   *
   * @return the private double
   */
  public double getPrivateDouble() {
    return privateDouble;
  }

  /**
   * Sets the private double.
   *
   * @param PrivateDouble the new private double
   */
  public void setPrivateDouble(double PrivateDouble) {
    privateDouble = PrivateDouble;
  }

  /**
   * Gets the private string.
   *
   * @return the private string
   */
  public String getPrivateString() {
    return privateString;
  }

  /**
   * Sets the private string.
   *
   * @param PrivateString the new private string
   */
  public void setPrivateString(String PrivateString) {
    privateString = PrivateString;
  }
  
}
