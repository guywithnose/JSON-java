/*
 * File: TestXML.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

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
 * The Class TestXML.
 */
public class TestXML extends TestCase
{

    JSONObject jsonobject = new JSONObject();
	
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
            String XMLString = "<abc><![CDATA[--comment--]></abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Unclosed CDATA at 34 [character 35 line 1]",
                    e.getMessage());
        }
        
        try
        {
            String XMLString = "<abc><![CDATA[--comment--]]?></abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Unclosed CDATA at 36 [character 37 line 1]",
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
    
    public static void testToJsonObject_NullCharacter()
    {
        try
        {
            String XMLString = "\0";
            JSONObject jo = new JSONObject();
            assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
        } catch (JSONException e)
        {
            fail(e.toString());
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
     * Tests the toJsonObject method using unclosed tag.
     */
    public static void testToJsonObject_UnclosedTag()
    {
        try
        {
            String XMLString = "<abc>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Unclosed tag abc at 6 [character 7 line 1]",
                    e.getMessage());
        }
    }

    /**
     * Tests the stringToValue method using true.
     */
    public static void testStringToValue_true()
    {
        assertEquals(Boolean.TRUE, XML.stringToValue("true"));
        assertEquals(Boolean.TRUE, XML.stringToValue("tRUe"));
        assertEquals(Boolean.TRUE, XML.stringToValue("TruE"));
        assertEquals(Boolean.TRUE, XML.stringToValue("TRUE"));
    }

    /**
     * Tests the stringToValue method using false.
     */
    public static void testStringToValue_false()
    {
        assertEquals(Boolean.FALSE, XML.stringToValue("false"));
        assertEquals(Boolean.FALSE, XML.stringToValue("fALSe"));
        assertEquals(Boolean.FALSE, XML.stringToValue("FalsE"));
        assertEquals(Boolean.FALSE, XML.stringToValue("FALSE"));
    }

    /**
     * Tests the stringToValue method using blank.
     */
    public static void testStringToValue_blank()
    {
        assertEquals("", XML.stringToValue(""));
    }

    /**
     * Tests the stringToValue method using null.
     */
    public static void testStringToValue_null()
    {
        assertEquals(JSONObject.NULL, XML.stringToValue("null"));
    }

    /**
     * Tests the stringToValue method using numbers.
     */
    public static void testStringToValue_Numbers()
    {
        assertEquals((int)0, XML.stringToValue("0"));
        assertEquals((int)10, XML.stringToValue("10"));
        assertEquals((int)-10, XML.stringToValue("-10"));
        assertEquals((double)34.5, XML.stringToValue("34.5"));
        assertEquals((double)-34.5, XML.stringToValue("-34.5"));
        assertEquals(34054535455454355L, XML.stringToValue("34054535455454355"));
        assertEquals(-34054535455454355L, XML.stringToValue("-34054535455454355"));
        assertEquals("00123", XML.stringToValue("00123"));
        assertEquals("-00123", XML.stringToValue("-00123"));
        assertEquals((int)123, XML.stringToValue("0123"));
        assertEquals((int)-123, XML.stringToValue("-0123"));
        assertEquals("-", XML.stringToValue("-"));
        assertEquals("-0abc", XML.stringToValue("-0abc"));
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
        try
        {
            String XMLString = "<abc=>123<abc=>";
            System.out.println(XML.toJSONObject(XMLString));
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Misshaped tag at 5 [character 6 line 1]",
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
     * Tests the toJsonObject method using attributes with open string.
     */
    public static void testToJsonObject_AttributesWithOpenString()
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

    /**
     * Tests the toJsonObject method using empty tag.
     */
    public static void testToJsonObject_EmptyTag()
    {
        try
        {
            String XMLString = "<abc />";
            JSONObject jo = new JSONObject();
            jo.put("abc", "");
            assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Tests the toJsonObject method using empty tag with attributes.
     */
    public static void testToJsonObject_EmptyTagWithAttributes()
    {
        try
        {
            String XMLString = "<abc 'def'='jkk' />";
            JSONObject jo = new JSONObject();
            JSONObject jo2 = new JSONObject();
            jo2.put("def","jkk");
            jo.put("abc", jo2);
            assertEquals(jo.toString(), XML.toJSONObject(XMLString).toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Tests the toJsonObject method using broken empty tag.
     */
    public static void testToJsonObject_BrokenEmptyTag()
    {
        try
        {
            String XMLString = "<abc /?>";
            XML.toJSONObject(XMLString).toString();
            fail("Should have failed");
        } catch (JSONException e)
        {
            assertEquals("Misshaped tag at 7 [character 8 line 1]",
                    e.getMessage());
        }
    }
    
    /**
     * Tests the toString method using jSON object.
     */
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put("abc", "123");
            assertEquals("<abc>123</abc>", XML.toString(jo));
        } catch (JSONException e)
        {
        	e.printStackTrace();
        }
    }

    public static void testToString_EmptyJsonObject()
    {
        try
        {
            JSONObject jo = new JSONObject();
            assertEquals("", XML.toString(jo));
        } catch (JSONException e)
        {
        	e.printStackTrace();
        }
    }
    
    /**
     * Tests the toString method using jSON object and name.
     */
    public static void testToString_JsonObjectAndName()
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put("abc", "123");
            assertEquals("<my name><abc>123</abc></my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	e.printStackTrace();
        }
    }
    
    public static void testToString_EmptyJsonObjectAndName()
    {
        try
        {
            JSONObject jo = new JSONObject();
            assertEquals("<my name></my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	e.printStackTrace();
        }
    }
    
    public static void testToString_EmptyJsonObjectAndEmptyName()
    {
        try
        {
            JSONObject jo = new JSONObject();
            assertEquals("<></>", XML.toString(jo, ""));
        } catch (JSONException e)
        {
        	e.printStackTrace();
        }
    }
    
    public static void testToString_JsonObjectWithNullStringValue()
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put("abc", "null");
            assertEquals("<my name><abc>null</abc></my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
        
    public static void testToString_JsonObjectWithJSONObjectNullValue()
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put("abc", JSONObject.NULL);
            assertEquals("<my name><abc/></my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public void testToString_JsonObjectWithNullKey()
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put(null, "abc");
            XML.toString(jo, "my name");
            fail("Should have thrown Exception");
        } catch (JSONException e)
        {
        	assertEquals("Null key.", e.getMessage());
        }
    }
    
    public static void testToString_JsonObjectWithInteger()
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put("abc", 45);
            assertEquals("<my name><abc>45</abc></my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_JsonObjectWithContentKeyIntValue()
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put("content", 45);
            assertEquals("<my name>45</my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_JsonObjectWithContentKeyJsonArrayValue()
    {
        try
        {
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            ja.put("123");
            ja.put(72);
            jo.put("content", ja);
            assertEquals("<my name>123\n72</my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    /**
     * Tests the toString method using json object with content key string value.
     */
    public static void testToString_JsonObjectWithContentKeyStringValue()
    {
        try
        {
            JSONObject jo = new JSONObject();
            jo.put("content", "42");
            assertEquals("<my name>42</my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_JsonObjectWithJsonArrayValue()
    {
        try
        {
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            ja.put("123");
            ja.put(72);
            jo.put("abc", ja);
            assertEquals("<my name><abc>123</abc><abc>72</abc></my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_JsonObjectWithJsonArrayOfJsonArraysValue()
    {
        try
        {
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            JSONArray ja2 = new JSONArray();
            JSONArray ja3 = new JSONArray();
            ja2.put("cat");
            ja.put(ja2);
            ja.put(ja3);
            jo.put("abc", ja);
            assertEquals("<my name><abc><array>cat</array></abc><abc></abc></my name>", XML.toString(jo, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_Array()
    {
        try
        {
            String[] strings = {"abc", "123"};
            assertEquals("<my name>abc</my name><my name>123</my name>", XML.toString(strings, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_JsonArray()
    {
        try
        {
            JSONArray ja = new JSONArray();
            ja.put("hi");
            ja.put("bye");
            assertEquals("<my name>hi</my name><my name>bye</my name>", XML.toString(ja, "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_EmptyString()
    {
        try
        {
            assertEquals("<my name/>", XML.toString("", "my name"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testToString_StringNoName()
    {
        try
        {
            assertEquals("\"123\"", XML.toString("123"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testEscape()
    {
        try
        {
            assertEquals("\"&amp;&lt;&gt;&quot;&apos;\"", XML.toString("&<>\"'"));
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testNoSpace_EmptyString()
    {
        try
        {
            XML.noSpace("");
            fail("Should have thrown exception");
        } catch (JSONException e)
        {
        	assertEquals("Empty string.", e.getMessage());
        }
    }
    
    public static void testNoSpace_StringWithNoSpaces()
    {
        try
        {
            XML.noSpace("123");
        } catch (JSONException e)
        {
        	fail(e.toString());
        }
    }
    
    public static void testNoSpace_StringWithSpaces()
    {
        try
        {
            XML.noSpace("1 23");
            fail("Should have thrown exception");
        } catch (JSONException e)
        {
        	assertEquals("'1 23' contains a space character.", e.getMessage());
        }
    }
    
    public static void test()
    {
        try
        {
            XML.noSpace("1 23");
            fail("Should have thrown exception");
        } catch (JSONException e)
        {
        	assertEquals("'1 23' contains a space character.", e.getMessage());
        }
    }
    
    @SuppressWarnings("static-method")
    public void testXML() throws Exception
    {
        String string;

        jsonobject = XML
                .toJSONObject("<![CDATA[This is a collection of test patterns and examples for json.]]>  Ignore the stuff past the end.  ");
        assertEquals(
                "{\"content\":\"This is a collection of test patterns and examples for json.\"}",
                jsonobject.toString());
        assertEquals(
                "This is a collection of test patterns and examples for json.",
                jsonobject.getString("content"));

        string = "<test><blank></blank><empty/></test>";
        jsonobject = XML.toJSONObject(string);
        assertEquals("{\"test\": {\n  \"blank\": \"\",\n  \"empty\": \"\"\n}}",
                jsonobject.toString(2));
        assertEquals("<test><blank/><empty/></test>", XML.toString(jsonobject));

        string = "<subsonic-response><playlists><playlist id=\"476c65652e6d3375\" int=\"12345678901234567890123456789012345678901234567890213991133777039355058536718668104339937\"/><playlist id=\"50617274792e78737066\"/></playlists></subsonic-response>";
        jsonobject = XML.toJSONObject(string);
        assertEquals(
                "{\"subsonic-response\":{\"playlists\":{\"playlist\":[{\"id\":\"476c65652e6d3375\",\"int\":\"12345678901234567890123456789012345678901234567890213991133777039355058536718668104339937\"},{\"id\":\"50617274792e78737066\"}]}}}",
                jsonobject.toString());
    }
    
    public void testXML2()
    {
    	try {
			jsonobject = XML
			        .toJSONObject("<?xml version='1.0' encoding='UTF-8'?>"
			                + "\n\n"
			                + "<SOAP-ENV:Envelope"
			                + " xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\""
			                + " xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\""
			                + " xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\">"
			                + "<SOAP-ENV:Body><ns1:doGoogleSearch"
			                + " xmlns:ns1=\"urn:GoogleSearch\""
			                + " SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
			                + "<key xsi:type=\"xsd:string\">GOOGLEKEY</key> <q"
			                + " xsi:type=\"xsd:string\">'+search+'</q> <start"
			                + " xsi:type=\"xsd:int\">0</start> <maxResults"
			                + " xsi:type=\"xsd:int\">10</maxResults> <filter"
			                + " xsi:type=\"xsd:boolean\">true</filter> <restrict"
			                + " xsi:type=\"xsd:string\"></restrict> <safeSearch"
			                + " xsi:type=\"xsd:boolean\">false</safeSearch> <lr"
			                + " xsi:type=\"xsd:string\"></lr> <ie"
			                + " xsi:type=\"xsd:string\">latin1</ie> <oe"
			                + " xsi:type=\"xsd:string\">latin1</oe>"
			                + "</ns1:doGoogleSearch>"
			                + "</SOAP-ENV:Body></SOAP-ENV:Envelope>");
		

        assertEquals(
                "{\"SOAP-ENV:Envelope\": {\n  \"SOAP-ENV:Body\": {\"ns1:doGoogleSearch\": {\n    \"oe\": {\n      \"content\": \"latin1\",\n      \"xsi:type\": \"xsd:string\"\n    },\n    \"SOAP-ENV:encodingStyle\": \"http://schemas.xmlsoap.org/soap/encoding/\",\n    \"lr\": {\"xsi:type\": \"xsd:string\"},\n    \"start\": {\n      \"content\": 0,\n      \"xsi:type\": \"xsd:int\"\n    },\n    \"q\": {\n      \"content\": \"'+search+'\",\n      \"xsi:type\": \"xsd:string\"\n    },\n    \"ie\": {\n      \"content\": \"latin1\",\n      \"xsi:type\": \"xsd:string\"\n    },\n    \"safeSearch\": {\n      \"content\": false,\n      \"xsi:type\": \"xsd:boolean\"\n    },\n    \"xmlns:ns1\": \"urn:GoogleSearch\",\n    \"restrict\": {\"xsi:type\": \"xsd:string\"},\n    \"filter\": {\n      \"content\": true,\n      \"xsi:type\": \"xsd:boolean\"\n    },\n    \"maxResults\": {\n      \"content\": 10,\n      \"xsi:type\": \"xsd:int\"\n    },\n    \"key\": {\n      \"content\": \"GOOGLEKEY\",\n      \"xsi:type\": \"xsd:string\"\n    }\n  }},\n  \"xmlns:xsd\": \"http://www.w3.org/1999/XMLSchema\",\n  \"xmlns:xsi\": \"http://www.w3.org/1999/XMLSchema-instance\",\n  \"xmlns:SOAP-ENV\": \"http://schemas.xmlsoap.org/soap/envelope/\"\n}}",
                jsonobject.toString(2));

        assertEquals(
                "<SOAP-ENV:Envelope><SOAP-ENV:Body><ns1:doGoogleSearch><oe>latin1<xsi:type>xsd:string</xsi:type></oe><SOAP-ENV:encodingStyle>http://schemas.xmlsoap.org/soap/encoding/</SOAP-ENV:encodingStyle><lr><xsi:type>xsd:string</xsi:type></lr><start>0<xsi:type>xsd:int</xsi:type></start><q>&apos;+search+&apos;<xsi:type>xsd:string</xsi:type></q><ie>latin1<xsi:type>xsd:string</xsi:type></ie><safeSearch>false<xsi:type>xsd:boolean</xsi:type></safeSearch><xmlns:ns1>urn:GoogleSearch</xmlns:ns1><restrict><xsi:type>xsd:string</xsi:type></restrict><filter>true<xsi:type>xsd:boolean</xsi:type></filter><maxResults>10<xsi:type>xsd:int</xsi:type></maxResults><key>GOOGLEKEY<xsi:type>xsd:string</xsi:type></key></ns1:doGoogleSearch></SOAP-ENV:Body><xmlns:xsd>http://www.w3.org/1999/XMLSchema</xmlns:xsd><xmlns:xsi>http://www.w3.org/1999/XMLSchema-instance</xmlns:xsi><xmlns:SOAP-ENV>http://schemas.xmlsoap.org/soap/envelope/</xmlns:SOAP-ENV></SOAP-ENV:Envelope>",
                XML.toString(jsonobject));
    	} catch (JSONException e) {
			fail(e.toString());
		}
    }

    /**
     * Tests the constructor method.
     */
    public static void testConstructor()
    {
        XML xml = new XML();
        assertEquals("XML", xml.getClass().getSimpleName());
    }

}
