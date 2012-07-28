package org.json.tests;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Jsonable;
import org.json.tests.helpers.JsonableTestClassWithGetMethod;
import org.json.tests.helpers.JsonableTestClassWithList;
import org.json.tests.helpers.JsonableTestClassWithMapping;
import org.json.tests.helpers.JsonableTestClassWithMaps;
import org.json.tests.helpers.JsonableTestClassWithoutGetMethod;
import org.junit.Test;

@SuppressWarnings("static-method")
public class TestJsonable {

  @Test
  public void testEmptyStringWithoutGetMethod() {
    JsonableTestClassWithoutGetMethod jtc = Jsonable.loadFromJson("{}", JsonableTestClassWithoutGetMethod.class);
    assertEquals(0, jtc.publicDouble, 0);
    assertEquals(0, jtc.publicInt);
    assertEquals(0, jtc.publicFloat, 0);
    assertNull(jtc.publicString);
    assertEquals(0, jtc.getPrivateDouble(), 0);
    assertEquals(0, jtc.getPrivateFloat(), 0);
    assertEquals(0, jtc.getPrivateInt());
    assertNull(jtc.getPrivateString());
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"publicDouble\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicString\":\"\""));
    assertThat(jsonString, Matchers.containsString("\"privateInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateDouble\":0"));
    assertThat(jsonString, Matchers.not(Matchers.containsString("\"privateString\"")));
  }

  @Test
  public void testPublicValuesWithoutGetMethod() {
    JsonableTestClassWithoutGetMethod jtc = Jsonable.loadFromJson("{\"publicDouble\":4.765765,\"publicInt\":4765765,\"publicFloat\":4.7657,\"publicString\":\"String Value\"}", JsonableTestClassWithoutGetMethod.class);
    assertEquals(4.765765, jtc.publicDouble, 0);
    assertEquals(4765765, jtc.publicInt);
    assertEquals(4.7657, jtc.publicFloat, .00001);
    assertEquals("String Value", jtc.publicString);
    assertEquals(0, jtc.getPrivateDouble(), 0);
    assertEquals(0, jtc.getPrivateFloat(), 0);
    assertEquals(0, jtc.getPrivateInt());
    assertNull(jtc.getPrivateString());
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"publicDouble\":4.765765"));
    assertThat(jsonString, Matchers.containsString("\"publicFloat\":4.7657"));
    assertThat(jsonString, Matchers.containsString("\"publicInt\":4765765"));
    assertThat(jsonString, Matchers.containsString("\"publicString\":\"String Value\""));
    assertThat(jsonString, Matchers.containsString("\"privateInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateDouble\":0"));
    assertThat(jsonString, Matchers.not(Matchers.containsString("\"privateString\"")));
  }

  @Test
  public void testPrivateValuesWithoutGetMethod() {
    JsonableTestClassWithoutGetMethod jtc = Jsonable.loadFromJson("{\"privateDouble\":4.765765,\"privateInt\":4765765,\"privateFloat\":4.7657,\"privateString\":\"String Value\"}", JsonableTestClassWithoutGetMethod.class);
    assertEquals(0, jtc.publicDouble, 0);
    assertEquals(0, jtc.publicFloat, 0);
    assertEquals(0, jtc.publicInt);
    assertNull(jtc.publicString);
    assertEquals(4.765765, jtc.getPrivateDouble(), 0);
    assertEquals(4765765, jtc.getPrivateInt());
    assertEquals(4.7657, jtc.getPrivateFloat(), .00001);
    assertEquals("String Value", jtc.getPrivateString());
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"privateDouble\":4.765765"));
    assertThat(jsonString, Matchers.containsString("\"privateFloat\":4.7657"));
    assertThat(jsonString, Matchers.containsString("\"privateInt\":4765765"));
    assertThat(jsonString, Matchers.containsString("\"privateString\":\"String Value\""));
    assertThat(jsonString, Matchers.containsString("\"publicInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicDouble\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicString\":\"\""));
  }

  @Test
  public void testEmptyStringWithGetMethod() {
    JsonableTestClassWithGetMethod jtc = Jsonable.loadFromJson("{}", JsonableTestClassWithGetMethod.class);
    assertEquals(0, jtc.publicDouble, 0);
    assertEquals(0, jtc.publicInt);
    assertEquals(0, jtc.publicFloat, 0);
    assertNull(jtc.publicString);
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"publicDouble\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicString\":\"\""));
    assertThat(jsonString, Matchers.containsString("\"privateInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateDouble\":0"));
    assertThat(jsonString, Matchers.not(Matchers.containsString("\"privateString\":\"\"")));
  }

  @Test
  public void testPublicValuesWithGetMethod() {
    JsonableTestClassWithGetMethod jtc = Jsonable.loadFromJson("{\"publicDouble\":4.765765,\"publicInt\":4765765,\"publicFloat\":4.7657,\"publicString\":\"String Value\"}", JsonableTestClassWithGetMethod.class);
    assertEquals(4.765765, jtc.publicDouble, 0);
    assertEquals(4765765, jtc.publicInt);
    assertEquals(4.7657, jtc.publicFloat, .00001);
    assertEquals("String Value", jtc.publicString);
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"publicDouble\":4.765765"));
    assertThat(jsonString, Matchers.containsString("\"publicFloat\":4.7657"));
    assertThat(jsonString, Matchers.containsString("\"publicInt\":4765765"));
    assertThat(jsonString, Matchers.containsString("\"publicString\":\"String Value\""));
    assertThat(jsonString, Matchers.containsString("\"privateInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"privateDouble\":0"));
    assertThat(jsonString, Matchers.not(Matchers.containsString("\"privateString\":\"\"")));
  }

  @Test
  public void testPrivateValuesWithGetMethod() {
    JsonableTestClassWithGetMethod jtc = Jsonable.loadFromFile("testFiles/privateTest.json", JsonableTestClassWithGetMethod.class);
    assertEquals(0, jtc.publicDouble, 0);
    assertEquals(0, jtc.publicFloat, 0);
    assertEquals(0, jtc.publicInt);
    assertNull(jtc.publicString);
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"privateDouble\":4.765765"));
    assertThat(jsonString, Matchers.containsString("\"privateFloat\":4.7657"));
    assertThat(jsonString, Matchers.containsString("\"privateInt\":4765765"));
    assertThat(jsonString, Matchers.containsString("\"privateString\":\"String Value\""));
    assertThat(jsonString, Matchers.containsString("\"publicInt\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicFloat\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicDouble\":0"));
    assertThat(jsonString, Matchers.containsString("\"publicString\":\"\""));
  }

  @Test
  public void testMappedValues() throws JSONException {
    JSONObject jo = new JSONObject("{\"valueData\":{\"data\":\"Value Name\"}}");
    JsonableTestClassWithMapping jtc = Jsonable.loadFromJson(jo, JsonableTestClassWithMapping.class);
    assertEquals("Value Name", jtc.valueData.data);
    jo = new JSONObject("{\"value\":\"Value Name\"}");
    jtc = Jsonable.loadFromJson(jo, JsonableTestClassWithMapping.class);
    assertEquals("Value Name", jtc.valueData.data);
  }

  @Test
  public void testListOfJsonables() {
    JsonableTestClassWithList jtc = Jsonable.loadFromFile("testFiles/listTest.json", JsonableTestClassWithList.class);
    assertEquals(3, jtc.JsonableList.size());
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("{\"detail\":\"Detail Name\",\"data\":\"Value Name\"}"));
    assertThat(jsonString, Matchers.containsString("{\"detail\":\"\",\"data\":\"Value Name\"}"));
    assertThat(jsonString, Matchers.containsString("{\"detail\":\"Detail Name\",\"data\":\"\"}"));
  }

  @Test
  public void testListOfStrings() {
    JsonableTestClassWithList jtc = Jsonable.loadFromFile("testFiles/listTest.json", JsonableTestClassWithList.class);
    assertEquals(4, jtc.stringArray.size());
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"abc\""));
    assertThat(jsonString, Matchers.containsString("\"def\""));
    assertThat(jsonString, Matchers.containsString("\"123\""));
    assertThat(jsonString, Matchers.containsString("\"456\""));
  }

  @Test
  public void testListOfListsOfJsonables() {
    JsonableTestClassWithList jtc = Jsonable.loadFromFile("testFiles/listTest.json", JsonableTestClassWithList.class);
    assertEquals(3, jtc.JsonableGrid.size());
    String[] numberStrings = new String[] {"one", "two", "three"};
    for(int i = 0; i < 3; i++) {
      for(int j = 0; j < 3; j++) {
        assertEquals(numberStrings[i], jtc.JsonableGrid.get(i).get(j).data);
        assertEquals(numberStrings[j], jtc.JsonableGrid.get(i).get(j).detail);
      }
    }
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"JsonableGrid\":[[{\"detail\":\"one\",\"data\":\"one\"},{\"detail\":\"two\",\"data\":\"one\"},{\"detail\":\"three\",\"data\":\"one\"}],[{\"detail\":\"one\",\"data\":\"two\"},{\"detail\":\"two\",\"data\":\"two\"},{\"detail\":\"three\",\"data\":\"two\"}],[{\"detail\":\"one\",\"data\":\"three\"},{\"detail\":\"two\",\"data\":\"three\"},{\"detail\":\"three\",\"data\":\"three\"}]]"));
  }

  @Test
  public void testListOfMapsOfJsonables() {
    JsonableTestClassWithList jtc = Jsonable.loadFromFile("testFiles/listTest.json", JsonableTestClassWithList.class);
    assertEquals(3, jtc.JsonableListMap.size());
    String[] numberStrings = new String[] {"one", "two", "three"};
    for(int i = 0; i < 3; i++) {
      for(int j = 0; j < 3; j++) {
        assertEquals(numberStrings[i], jtc.JsonableListMap.get(i).get("value" + (j + 1)).data);
        assertEquals(numberStrings[j], jtc.JsonableListMap.get(i).get("value" + (j + 1)).detail);
      }
    }
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"JsonableListMap\":[{\"value3\":{\"detail\":\"three\",\"data\":\"one\"},\"value1\":{\"detail\":\"one\",\"data\":\"one\"},\"value2\":{\"detail\":\"two\",\"data\":\"one\"}},{\"value3\":{\"detail\":\"three\",\"data\":\"two\"},\"value1\":{\"detail\":\"one\",\"data\":\"two\"},\"value2\":{\"detail\":\"two\",\"data\":\"two\"}},{\"value3\":{\"detail\":\"three\",\"data\":\"three\"},\"value1\":{\"detail\":\"one\",\"data\":\"three\"},\"value2\":{\"detail\":\"two\",\"data\":\"three\"}}]"));
  }

  @Test
  public void testMapOfJsonables() {
    JsonableTestClassWithMaps jtc = Jsonable.loadFromFile("testFiles/mapTest.json", JsonableTestClassWithMaps.class);
    assertEquals(3, jtc.JsonableMap.size());
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"value1\":{\"detail\":\"\",\"data\":\"Value Name\"}"));
    assertThat(jsonString, Matchers.containsString("\"value2\":{\"detail\":\"Detail Name\",\"data\":\"\"}"));
    assertThat(jsonString, Matchers.containsString("\"value3\":{\"detail\":\"Detail Name\",\"data\":\"Value Name\"}"));
  }

  @Test
  public void testMapOfStrings() {
    JsonableTestClassWithMaps jtc = Jsonable.loadFromFile("testFiles/mapTest.json", JsonableTestClassWithMaps.class);
    assertEquals(4, jtc.stringMap.size());
    String jsonString = jtc.toJSON().toString();
    assertThat(jsonString, Matchers.containsString("\"value1\":\"abc\""));
    assertThat(jsonString, Matchers.containsString("\"value2\":\"123\""));
    assertThat(jsonString, Matchers.containsString("\"value3\":\"def\""));
    assertThat(jsonString, Matchers.containsString("\"value4\":\"456\""));
  }

  @Test
  public void testMapOfListsOfJsonables() {
    JsonableTestClassWithMaps jtc = Jsonable.loadFromFile("testFiles/mapTest.json", JsonableTestClassWithMaps.class);
    assertEquals(3, jtc.JsonableMapGrid.size());
    String[] numberStrings = new String[] { "one", "two", "three" };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(numberStrings[i],
            jtc.JsonableMapGrid.get("value" + (i + 1)).get(j).data);
        assertEquals(numberStrings[j],
            jtc.JsonableMapGrid.get("value" + (i + 1)).get(j).detail);
      }
    }
    String jsonString = jtc.toJSON().toString();
    assertThat(
        jsonString,
        Matchers
            .containsString("\"JsonableMapGrid\":{\"value3\":[{\"detail\":\"one\",\"data\":\"three\"},{\"detail\":\"two\",\"data\":\"three\"},{\"detail\":\"three\",\"data\":\"three\"}],\"value1\":[{\"detail\":\"one\",\"data\":\"one\"},{\"detail\":\"two\",\"data\":\"one\"},{\"detail\":\"three\",\"data\":\"one\"}],\"value2\":[{\"detail\":\"one\",\"data\":\"two\"},{\"detail\":\"two\",\"data\":\"two\"},{\"detail\":\"three\",\"data\":\"two\"}]}"));
  }

  @Test
  public void testMapOfMapsOfJsonables() {
    JsonableTestClassWithMaps jtc = Jsonable.loadFromFile("testFiles/mapTest.json", JsonableTestClassWithMaps.class);
    assertEquals(3, jtc.JsonableMapMap.size());
    String[] numberStrings = new String[] { "one", "two", "three" };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(numberStrings[i],
            jtc.JsonableMapMap.get("value" + (i + 1)).get("value" + (j + 1)).data);
        assertEquals(numberStrings[j],
            jtc.JsonableMapMap.get("value" + (i + 1)).get("value" + (j + 1)).detail);
      }
    }
    String jsonString = jtc.toJSON().toString();
    assertThat(
        jsonString,
        Matchers
            .containsString("\"JsonableMapMap\":{\"value3\":{\"value3\":{\"detail\":\"three\",\"data\":\"three\"},\"value1\":{\"detail\":\"one\",\"data\":\"three\"},\"value2\":{\"detail\":\"two\",\"data\":\"three\"}},\"value1\":{\"value3\":{\"detail\":\"three\",\"data\":\"one\"},\"value1\":{\"detail\":\"one\",\"data\":\"one\"},\"value2\":{\"detail\":\"two\",\"data\":\"one\"}},\"value2\":{\"value3\":{\"detail\":\"three\",\"data\":\"two\"},\"value1\":{\"detail\":\"one\",\"data\":\"two\"},\"value2\":{\"detail\":\"two\",\"data\":\"two\"}}}"));
  }
}
