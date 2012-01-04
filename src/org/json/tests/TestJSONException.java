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

	JSONException jsonexception;
    
	public void testConstructor_String() {
	    jsonexception = new JSONException("test String");
        assertEquals("test String", jsonexception.getMessage());
	}
	
    public void testConstructor_Exception() {
        Exception e = new Exception();
        jsonexception = new JSONException(e);
        assertEquals(e, jsonexception.getCause());
    }
    
}