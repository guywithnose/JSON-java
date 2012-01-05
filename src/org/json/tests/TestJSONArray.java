/*
 * File: TestJSONArray.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;

import junit.framework.TestCase;

/**
 * The Class TestJSONArray.
 */
public class TestJSONArray extends TestCase {

    /** The jsonarray. */
    private JSONArray jsonarray;
    
    /** The string. */
    private String string;
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    @Before
    public void setUp()
    {
        jsonarray = new JSONArray();
        string = "";
    }
    
	/**
	 * Tests the jsonArray method using int with leading zeros.
	 */
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
	 * Tests the jsonArray method using scintific notation.
	 */
	public static void testJsonArray_ScintificNotation() {
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
	
	/**
	 * Tests the constructor method using missing value.
	 */
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
    
    /**
     * Tests the constructor method using nan.
     */
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
    
    /**
     * Tests the constructor method using negative infinity.
     */
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
    
    /**
     * Tests the constructor method using positive infinity.
     */
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
    
    /**
     * Tests the put method using positive infinity.
     */
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
    
    /**
     * Tests the getDouble method using empty array.
     */
    public void testGetDouble_EmptyArray()
    {

        try {
            jsonarray.getDouble(0);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSONArray[0] not found.", jsone.getMessage());
        }
    }
    
    /**
     * Tests the get method using negative index.
     */
    public void testGet_NegativeIndex()
    {

        try {
            jsonarray.get(-1);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("JSONArray[-1] not found.", jsone.getMessage());
        }
    }
    
    /**
     * Tests the put method using nan.
     */
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
    
    /**
     * Tests the constructor method using object.
     */
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
    
    /**
     * Tests the constructor method using bad json.
     */
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
    
    /**
     * Tests the toString method using locations.
     */
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