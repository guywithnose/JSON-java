


package org.json.tests.helpers;

import java.lang.reflect.Field;

import org.json.Jsonable;

public class JsonableTestClassWithMapping extends Jsonable {

  public ValueObject valueData = new ValueObject();

  /**
   * Handle pseudo field.
   *
   * @param fieldName the field name
   */
  @Override
  protected void handlePseudoField(String fieldName, Object value) {
    if (fieldName.equals("value")) {
      valueData.data = (String)value;
    }
  }
  
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

  @Override
  protected String[] getPseudoFields() {
    return new String[] {"value"};
  }
  
}
