/*
 * File: TestJSONObject.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONObject;

import junit.framework.TestCase;

/**
 * The Class TestJSONObject.
 */
public class TestJSONObject extends TestCase
{

    /**
     * Tests the null method.
     * 
     * @throws Exception
     *             the exception
     */
    @SuppressWarnings("static-method")
    public void testNull() throws Exception
    {
        JSONObject jsonobject;

        jsonobject = new JSONObject("{\"message\":\"null\"}");
        assertFalse(jsonobject.isNull("message"));
        assertEquals("null", jsonobject.getString("message"));

        jsonobject = new JSONObject("{\"message\":null}");
        assertTrue(jsonobject.isNull("message"));
    }
}