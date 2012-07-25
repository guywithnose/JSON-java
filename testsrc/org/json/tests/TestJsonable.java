package org.json.tests;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Jsonable;
import org.json.tests.helpers.JsonableTestClassWithGetMethod;
import org.json.tests.helpers.JsonableTestClassWithMapping;
import org.json.tests.helpers.JsonableTestClassWithoutGetMethod;
import org.junit.Test;

@SuppressWarnings("static-method")
public class TestJsonable {

  @Test
  public void testEmptyStringWithoutGetMethod() {
    JsonableTestClassWithoutGetMethod jtc = Jsonable.loadFromJson("{}", "", JsonableTestClassWithoutGetMethod.class);
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
    JsonableTestClassWithoutGetMethod jtc = Jsonable.loadFromJson("{\"publicDouble\":4.765765,\"publicInt\":4765765,\"publicFloat\":4.7657,\"publicString\":\"String Value\"}", "", JsonableTestClassWithoutGetMethod.class);
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
    JsonableTestClassWithoutGetMethod jtc = Jsonable.loadFromJson("{\"privateDouble\":4.765765,\"privateInt\":4765765,\"privateFloat\":4.7657,\"privateString\":\"String Value\"}", "", JsonableTestClassWithoutGetMethod.class);
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
    JsonableTestClassWithGetMethod jtc = Jsonable.loadFromJson("{}", "", JsonableTestClassWithGetMethod.class);
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
    JsonableTestClassWithGetMethod jtc = Jsonable.loadFromJson("{\"publicDouble\":4.765765,\"publicInt\":4765765,\"publicFloat\":4.7657,\"publicString\":\"String Value\"}", "", JsonableTestClassWithGetMethod.class);
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
    JsonableTestClassWithGetMethod jtc = Jsonable.loadFromJson("{\"privateDouble\":4.765765,\"privateInt\":4765765,\"privateFloat\":4.7657,\"privateString\":\"String Value\"}", "", JsonableTestClassWithGetMethod.class);
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
    JsonableTestClassWithMapping jtc = Jsonable.loadFromJson(jo, "", JsonableTestClassWithMapping.class);
    assertEquals("Value Name", jtc.valueData.data);
    jo = new JSONObject("{\"value\":\"Value Name\"}");
    jtc = Jsonable.loadFromJson(jo, "", JsonableTestClassWithMapping.class);
    assertEquals("Value Name", jtc.valueData.data);
  }

}