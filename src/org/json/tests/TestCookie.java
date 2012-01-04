/*
 * File: TestCookie.java Author: JSON.org
 */
package org.json.tests;

import org.json.Cookie;
import org.json.CookieList;
import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.TestCase;

/**
 * The Class TestCookie.
 */
public class TestCookie extends TestCase
{

    JSONObject jsonobject = new JSONObject();

    public void testToJsonObject_RandomCookieData()
    {
        try
        {
            jsonobject = Cookie
                    .toJSONObject("f%oo=blah; secure ;expires = April 24, 2002");
            assertEquals("{\n" + "  \"expires\": \"April 24, 2002\",\n"
                    + "  \"name\": \"f%oo\",\n" + "  \"secure\": true,\n"
                    + "  \"value\": \"blah\"\n" + "}", jsonobject.toString(2));
            assertEquals("f%25oo=blah;expires=April 24, 2002;secure",
                    Cookie.toString(jsonobject));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
}