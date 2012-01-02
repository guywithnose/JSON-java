/*
 * File: TestXML.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
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
 * The Class TestXML.
 */
public class TestXML extends TestCase
{

    /**
     * Tests the toJsonObject method using Simple xml.
     */
    public static void testToJsonObject_SimpleXML()
    {
        try
        {
            String XMLString = "<!--comment--><tag1><tag2><?Skip Me?><![CDATA[--comment--]]></tag2><tag3>!123321</tag3></tag1>";
            JSONObject jo = new JSONObject();
            JSONObject jo2 = new JSONObject();
            jo2.put("tag2", "--comment--");
            jo2.put("tag3", "!123321");
            jo.put("tag1", jo2);
            assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
        } catch (JSONException e)
        {
            assertEquals("Unterminated string at 20 [character 21 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObject method using Bad name.
     */
    public static void testToJsonObject_BadName()
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
     * Tests the toJsonObject method using Bad cdata.
     */
    public static void testToJsonObject_BadCDATA()
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
     * Tests the toJsonObject method using Empty cdata.
     */
    public static void testToJsonObject_EmptyCdata()
    {
        try
        {
            String XMLString = "<abc><![CDATA[]]></abc>";
            JSONObject jo = new JSONObject();
            jo.put("abc", "");
            assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
        } catch (JSONException e)
        {
            assertEquals("Unterminated string at 20 [character 21 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObject method using Bad meta.
     */
    public static void testToJsonObject_BadMeta()
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
     * Tests the toJsonObject method using Open cdata.
     */
    public static void testToJsonObject_OpenCDATA()
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
     * Tests the toJsonObject method using Mismatched tags.
     */
    public static void testToJsonObject_MismatchedTags()
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
     * Tests the toJsonObject method using Misshaped close tag.
     */
    public static void testToJsonObject_MisshapedCloseTag()
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
     * Tests the toJsonObject method using Misshaped tag.
     */
    public static void testToJsonObject_MisshapedTag()
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
     * Tests the toJsonObject method using Attributes.
     *
     * @throws Exception the exception
     */
    public static void testToJsonObject_Attributes() throws Exception
    {
        String XMLString = "<abc \"abc\"=\"123\">123</abc>";
        JSONObject jo = new JSONObject();
        JSONObject jo2 = new JSONObject();
        jo2.put("content", 123);
        jo2.put("abc", 123);
        jo.put("abc", jo2);
        assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
    }

    /**
     * Tests the toJsonObject method using Attributes open string.
     */
    public static void testToJsonObject_AttributesOpenString()
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
     * Tests the toJsonObject method using Attributes with ampersand.
     */
    public static void testToJsonObject_AttributesWithAmpersand()
    {

        try
        {
            String XMLString = "<abc \"abc&nbsp;\">123</abc>";
            JSONObject jo = new JSONObject();
            JSONObject jo2 = new JSONObject();
            jo2.put("content", 123);
            jo2.put("abc&nbsp;", "");
            jo.put("abc", jo2);
            assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
        } catch (JSONException e)
        {
            assertEquals("Unterminated string at 20 [character 21 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the toJsonObject method using Attributes missing value.
     */
    public static void testToJsonObject_AttributesMissingValue()
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