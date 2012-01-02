/*
 * File:         TestXML.java
 * Author:       JSON.org
 */
package tests;

import json.JSONException;
import json.JSONObject;
import json.XML;
import junit.framework.TestCase;

/*
 * Copyright (c) 2002 JSON.org
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * The Software shall be used for Good, not Evil.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Test class. This file is not formally a member of the json library. It is
 * just a test tool.
 * 
 * Issue: JSONObject does not specify the ordering of keys, so simple-minded
 * comparisons of .toString to a string literal are likely to fail.
 * 
 * @author JSON.org
 * @version 2011-10-25
 */
public class TestXML extends TestCase
{

    /**
     * Tests the toJsonObjectSimpleXML method.
     *
     * @throws Exception the exception
     */
    public static void testToJsonObjectSimpleXML() throws Exception
    {
        String XMLString = "<!--comment--><tag1><tag2><?Skip Me?><![CDATA[--comment--]]></tag2><tag3>!123321</tag3></tag1>";
        JSONObject jo = new JSONObject();
        JSONObject jo2 = new JSONObject();
        jo2.put("tag2", "--comment--");
        jo2.put("tag3", "!123321");
        jo.put("tag1", jo2);
        assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
    }

    /**
     * Tests the toJsonObjectBadName method.
     */
    public static void testToJsonObjectBadName()
    {
        try
        {
            String XMLString = "<!-abc>123</!-abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Mismatched close tag ! at 13 [character 14 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectBadCDATA method.
     */
    public static void testToJsonObjectBadCDATA()
    {
        try
        {
            String XMLString = "<abc><![CDATA?[--comment--]]></abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Expected 'CDATA[' at 14 [character 15 line 1]",
                    e.getMessage());
        }

        try
        {
            String XMLString = "<abc><![CDAT[--comment--]]></abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Expected 'CDATA[' at 12 [character 13 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectEmptyCDATA method.
     *
     * @throws Exception the exception
     */
    public static void testToJsonObjectEmptyCDATA() throws Exception
    {
        String XMLString = "<abc><![CDATA[]]></abc>";
        JSONObject jo = new JSONObject();
        jo.put("abc", "");
        assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
    }

    /**
     * Tests the toJsonObjectBadMeta method.
     */
    public static void testToJsonObjectBadMeta()
    {
        try
        {
            String XMLString = "<! abc";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Misshaped meta tag at 7 [character 8 line 1]",
                    e.getMessage());
        }

        try
        {
            String XMLString = "<!-<abc";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Misshaped meta tag at 8 [character 9 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectOpenCDATA method.
     */
    public static void testToJsonObjectOpenCDATA()
    {
        try
        {
            String XMLString = "<abc><![CDATA[";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Unclosed CDATA at 15 [character 16 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectMismatchedTags method.
     */
    public static void testToJsonObjectMismatchedTags()
    {
        try
        {
            String XMLString = "<abc>123</def>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Mismatched abc and def at 13 [character 14 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectMisshapedCloseTag method.
     */
    public static void testToJsonObjectMisshapedCloseTag()
    {
        try
        {
            String XMLString = "<abc>123</abc?";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Misshaped close tag at 14 [character 15 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectMisshapedTag method.
     */
    public static void testToJsonObjectMisshapedTag()
    {
        try
        {
            String XMLString = "<=abc>123<=abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Misshaped tag at 2 [character 3 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectAttributes method.
     *
     * @throws Exception the exception
     */
    public static void testToJsonObjectAttributes() throws Exception
    {
        String XMLString = "<abc \"abc\"=\"123\">123</abc>";
        JSONObject jo = new JSONObject();
        JSONObject jo2 = new JSONObject();
        jo2.put("content", 123);
        jo2.put("abc", 123);
        jo.put("abc", jo2);
        assertEquals(jo.toString(), XML
                .toJSONObject(XMLString).toString());
    }

    /**
     * Tests the toJsonObjectAttributesOpenString method.
     */
    public static void testToJsonObjectAttributesOpenString()
    {
        try
        {
            String XMLString = "<abc \"abc>123</abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Unterminated string at 20 [character 21 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObjectAttributesWithAmpersand method.
     *
     * @throws Exception the exception
     */
    public static void testToJsonObjectAttributesWithAmpersand() throws Exception
    {
        String XMLString = "<abc \"abc&nbsp;\">123</abc>";
        JSONObject jo = new JSONObject();
        JSONObject jo2 = new JSONObject();
        jo2.put("content", 123);
        jo2.put("abc&nbsp;", "");
        jo.put("abc", jo2);
        assertEquals(jo.toString(), XML
                .toJSONObject(XMLString).toString());
    }
    
    /**
     * Tests the toJsonObjectAttributesMissingValue method.
     */
    public static void testToJsonObjectAttributesMissingValue()
    {
        try
        {
            String XMLString = "<abc \"abc\"=>123</abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Missing value at 12 [character 13 line 1]",
                    e.getMessage());
        }
    }

}