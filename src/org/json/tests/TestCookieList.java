/*
 * File: TestCookieList.java Author: JSON.org
 */
package org.json.tests;

import org.json.CookieList;
import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class TestCookieList.
 */
public class TestCookieList extends TestCase
{

    /** The jsonobject. */
    JSONObject jsonobject = new JSONObject();

    
    /**
     * Tests the toJsonObject method using random cookie list.
     */
    public void testToJsonObject_RandomCookieList()
    {
        try
        {
            jsonobject = CookieList
                    .toJSONObject("  f%oo = b+l=ah  ; o;n%40e = t.wo ");
            assertEquals("{\n  \"o;n@e\": \"t.wo\",\n  \"f%oo\": \"b l=ah\"\n}",
                    jsonobject.toString(2));
            assertEquals("o%3bn@e=t.wo;f%25oo=b l%3dah",
                    CookieList.toString(jsonobject));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
}