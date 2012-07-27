


package org.json.tests.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.Jsonable;

public class JsonableTestClassWithList extends Jsonable {
  
  public List<ValueObject> valueData = new ArrayList<ValueObject>();
  
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
