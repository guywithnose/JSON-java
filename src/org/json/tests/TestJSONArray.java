/*
 * File: TestJSONArray.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.XML;
import org.junit.Before;

import junit.framework.TestCase;

/**
 * The Class TestJSONArray.
 */
public class TestJSONArray extends TestCase {

    private JSONArray jsonarray;
    
    private String string;
    
    @Before
    public void setUp()
    {
        jsonarray = new JSONArray();
        string = "";
    }
    
	public static void testJsonArray_IntWithLeadingZeros() {
		JSONArray jsonarray;
		String string;

		try {
			string = "[001122334455]";
			jsonarray = new JSONArray(string);
			assertEquals("[1122334455]", jsonarray.toString());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Tests the jsonArray method using hmmm.
	 */
	public static void testJsonArray_Hmmm() {
		JSONArray jsonarray;
		String string;

		try {
			string = "[666e666]";
			jsonarray = new JSONArray(string);
			assertEquals("[\"666e666\"]", jsonarray.toString());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Tests the jsonArray method using double with leading and trailing zeros.
	 */
	public static void testJsonArray_DoubleWithLeadingAndTrailingZeros() {
		JSONArray jsonarray;
		String string;

		try {
			string = "[00.10]";
			jsonarray = new JSONArray(string);
			assertEquals("[0.1]", jsonarray.toString());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testConstructor_MissingValue()
	{
        try {
            jsonarray = new JSONArray("[\n\r\n\r}");
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("Missing value at 5 [character 0 line 4]",
                    jsone.getMessage());
        }
	}
    
    public void testConstructor_Nan()
    {
        try {
            jsonarray = new JSONArray();
            jsonarray.put(Double.NaN);
            jsonarray.toString();
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSON does not allow non-finite numbers.",
                    jsone.getMessage());
        }
    }
    
    public void testConstructor_NegativeInfinity()
    {
        try {
            jsonarray = new JSONArray();
            jsonarray.put(Double.NEGATIVE_INFINITY);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSON does not allow non-finite numbers.",
                    jsone.getMessage());
        }
    }
    
    public void testConstructor_PositiveInfinity()
    {
        try {
            jsonarray = new JSONArray();
            jsonarray.put(Double.POSITIVE_INFINITY);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSON does not allow non-finite numbers.",
                    jsone.getMessage());
        }
    }
    
    public void testPut_PositiveInfinity()
    {
        try {
            jsonarray = new JSONArray();
            jsonarray.put(Double.POSITIVE_INFINITY);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSON does not allow non-finite numbers.",
                    jsone.getMessage());
        }
    }
    
    public void testGetDouble_EmptyArray()
    {

        try {
            jsonarray.getDouble(0);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSONArray[0] not found.", jsone.getMessage());
        }
    }
    
    public void testGet_NegativeIndex()
    {

        try {
            jsonarray.get(-1);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSONArray[-1] not found.", jsone.getMessage());
        }
    }
    
    public void testPut_Nan()
    {
        try {
            jsonarray.put(Double.NaN);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSON does not allow non-finite numbers.",
                    jsone.getMessage());
        }
    }
    
    public void testConstructor_Object()
    {
        try {
            jsonarray = new JSONArray(new Object());
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals(
                    "JSONArray initial value should be a string or collection or array.",
                    jsone.getMessage());
        }
    }
    
    public void testConstructor_BadJson()
    {

        try {
            string = "[)";
            jsonarray = new JSONArray(string);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("Expected a ',' or ']' at 3 [character 4 line 1]",
                    jsone.getMessage());
        }
    }
    
    public void testToString_Locations()
    {
        try
        {
            string = " [\"San Francisco\", \"New York\", \"Seoul\", \"London\", \"Seattle\", \"Shanghai\"]";
            jsonarray = new JSONArray(string);
            assertEquals(
                    "[\"San Francisco\",\"New York\",\"Seoul\",\"London\",\"Seattle\",\"Shanghai\"]",
                    jsonarray.toString());
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
	
}