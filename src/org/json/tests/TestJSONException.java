/*
 * File: TestJSONException.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.XML;

import junit.framework.TestCase;

/**
 * The Class TestJSONException.
 */
public class TestJSONException extends TestCase {

	/**
	 * Tests the exceptions method.
	 */
	@SuppressWarnings("static-method")
	public void testExceptions() {
		JSONArray jsonarray = null;
		JSONObject jsonobject;
		String string;

		try {
			jsonarray = new JSONArray("[\n\r\n\r}");
			System.out.println(jsonarray.toString());
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Missing value at 5 [character 0 line 4]",
					jsone.getMessage());
		}

		try {
			jsonarray = new JSONArray("<\n\r\n\r      ");
			System.out.println(jsonarray.toString());
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals(
					"A JSONArray text must start with '[' at 1 [character 2 line 1]",
					jsone.getMessage());
		}

		try {
			jsonarray = new JSONArray();
			jsonarray.put(Double.NEGATIVE_INFINITY);
			jsonarray.put(Double.NaN);
			System.out.println(jsonarray.toString());
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("JSON does not allow non-finite numbers.",
					jsone.getMessage());
		}

		jsonobject = new JSONObject();
		try {
			System.out.println(jsonobject.getDouble("stooge"));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("JSONObject[\"stooge\"] not found.",
					jsone.getMessage());
		}

		try {
			System.out.println(jsonobject.getDouble("howard"));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("JSONObject[\"howard\"] not found.",
					jsone.getMessage());
		}

		try {
			System.out.println(jsonobject.put(null, "howard"));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Null key.", jsone.getMessage());
		}

		try {
			System.out.println(jsonarray.getDouble(0));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("JSONArray[0] not found.", jsone.getMessage());
		}

		try {
			System.out.println(jsonarray.get(-1));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("JSONArray[-1] not found.", jsone.getMessage());
		}

		try {
			System.out.println(jsonarray.put(Double.NaN));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("JSON does not allow non-finite numbers.",
					jsone.getMessage());
		}

		try {
			jsonobject = XML.toJSONObject("<a><b>    ");
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Unclosed tag b at 11 [character 12 line 1]",
					jsone.getMessage());
		}

		try {
			jsonobject = XML.toJSONObject("<a></b>    ");
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Mismatched a and b at 6 [character 7 line 1]",
					jsone.getMessage());
		}

		try {
			jsonobject = XML.toJSONObject("<a></a    ");
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Misshaped element at 11 [character 12 line 1]",
					jsone.getMessage());
		}

		try {
			jsonarray = new JSONArray(new Object());
			System.out.println(jsonarray.toString());
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals(
					"JSONArray initial value should be a string or collection or array.",
					jsone.getMessage());
		}

		try {
			string = "[)";
			jsonarray = new JSONArray(string);
			System.out.println(jsonarray.toString());
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Expected a ',' or ']' at 3 [character 4 line 1]",
					jsone.getMessage());
		}

		try {
			string = "<xml";
			jsonarray = JSONML.toJSONArray(string);
			System.out.println(jsonarray.toString(4));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Misshaped element at 6 [character 7 line 1]",
					jsone.getMessage());
		}

		try {
			string = "<right></wrong>";
			jsonarray = JSONML.toJSONArray(string);
			System.out.println(jsonarray.toString(4));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals(
					"Mismatched 'right' and 'wrong' at 15 [character 16 line 1]",
					jsone.getMessage());
		}

		try {
			string = "This ain't XML.";
			jsonarray = JSONML.toJSONArray(string);
			System.out.println(jsonarray.toString(4));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Bad XML at 17 [character 18 line 1]",
					jsone.getMessage());
		}

		try {
			string = "{\"koda\": true, \"koda\": true}";
			jsonobject = new JSONObject(string);
			System.out.println(jsonobject.toString(4));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Duplicate key \"koda\"", jsone.getMessage());
		}

		try {
			JSONStringer jj = new JSONStringer();
			string = jj.object().key("bosanda").value("MARIE HAA'S")
					.key("bosanda").value("MARIE HAA\\'S").endObject()
					.toString();
			System.out.println(jsonobject.toString(4));
			fail("expecting JSONException here.");
		} catch (JSONException jsone) {
			assertEquals("Duplicate key \"bosanda\"", jsone.getMessage());
		}
	}
}