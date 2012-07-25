


package org.json.tests.helpers;

import org.json.Jsonable;

public class JsonableTestClassWithoutGetMethod extends Jsonable {

  public int publicInt;
  
  public float publicFloat;
  
  public double publicDouble;
  
  public String publicString;

  private int privateInt;
  
  private float privateFloat;
  
  private double privateDouble;
  
  private String privateString;

  public int getPrivateInt() {
    return privateInt;
  }

  public void setPrivateInt(int PrivateInt) {
    privateInt = PrivateInt;
  }

  public float getPrivateFloat() {
    return privateFloat;
  }

  public void setPrivateFloat(float PrivateFLoat) {
    privateFloat = PrivateFLoat;
  }

  public double getPrivateDouble() {
    return privateDouble;
  }

  public void setPrivateDouble(double PrivateDouble) {
    privateDouble = PrivateDouble;
  }

  public String getPrivateString() {
    return privateString;
  }

  public void setPrivateString(String PrivateString) {
    privateString = PrivateString;
  }
  
}
