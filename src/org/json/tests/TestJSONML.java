/*
 * File: TestJSONML.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;
import org.json.XML;

import junit.framework.TestCase;

/**
 * The Class TestJSONML.
 */
public class TestJSONML extends TestCase
{

    private JSONObject jsonobject;
    
    private JSONArray jsonarray;
    
    private String string;
    
    public void testToJsonArray_OpenXmlTag()
    {
        try {
            string = "<xml";
            jsonarray = JSONML.toJSONArray(string);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("Misshaped element at 6 [character 7 line 1]",
                    jsone.getMessage());
        }
    }
    
    public void testToJsonArray_MismatchedTags()
    {
        try {
            string = "<right></wrong>";
            jsonarray = JSONML.toJSONArray(string);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals(
                    "Mismatched 'right' and 'wrong' at 15 [character 16 line 1]",
                    jsone.getMessage());
        }
    }
    
    public void testToJsonArray_TextString()
    {
        try {
            string = "This ain't XML.";
            jsonarray = JSONML.toJSONArray(string);
            fail("expecting JSONException here.");
        } catch (JSONException jsone) {
            assertEquals("Bad XML at 17 [character 18 line 1]",
                    jsone.getMessage());
        }
    }
    
    public void testToString_XmlRecipeAsJsonObject()
    {
        try
        {
            string = "<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ";

            jsonobject = JSONML.toJSONObject(string);
            assertEquals(
                    "{\"cook_time\":\"3 hours\",\"name\":\"bread\",\"tagName\":\"recipe\",\"childNodes\":[{\"tagName\":\"title\",\"childNodes\":[\"Basic bread\"]},{\"amount\":8,\"unit\":\"dL\",\"tagName\":\"ingredient\",\"childNodes\":[\"Flour\"]},{\"amount\":10,\"unit\":\"grams\",\"tagName\":\"ingredient\",\"childNodes\":[\"Yeast\"]},{\"amount\":4,\"unit\":\"dL\",\"tagName\":\"ingredient\",\"state\":\"warm\",\"childNodes\":[\"Water\"]},{\"amount\":1,\"unit\":\"teaspoon\",\"tagName\":\"ingredient\",\"childNodes\":[\"Salt\"]},{\"tagName\":\"instructions\",\"childNodes\":[{\"tagName\":\"step\",\"childNodes\":[\"Mix all ingredients together.\"]},{\"tagName\":\"step\",\"childNodes\":[\"Knead thoroughly.\"]},{\"tagName\":\"step\",\"childNodes\":[\"Cover with a cloth, and leave for one hour in warm room.\"]},{\"tagName\":\"step\",\"childNodes\":[\"Knead again.\"]},{\"tagName\":\"step\",\"childNodes\":[\"Place in a bread baking tin.\"]},{\"tagName\":\"step\",\"childNodes\":[\"Cover with a cloth, and leave for one hour in warm room.\"]},{\"tagName\":\"step\",\"childNodes\":[\"Bake in the oven at 180(degrees)C for 30 minutes.\"]}]}],\"prep_time\":\"5 mins\"}",
                    jsonobject.toString());
            assertEquals(
                    "<recipe cook_time=\"3 hours\" name=\"bread\" prep_time=\"5 mins\"><title>Basic bread</title><ingredient amount=\"8\" unit=\"dL\">Flour</ingredient><ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient><ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient><ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient><instructions><step>Mix all ingredients together.</step><step>Knead thoroughly.</step><step>Cover with a cloth, and leave for one hour in warm room.</step><step>Knead again.</step><step>Place in a bread baking tin.</step><step>Cover with a cloth, and leave for one hour in warm room.</step><step>Bake in the oven at 180(degrees)C for 30 minutes.</step></instructions></recipe>",
                    JSONML.toString(jsonobject));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testToString_XmlRecipeAsJsonArray()
    {
        try
        {
            string = "<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ";

            jsonarray = JSONML.toJSONArray(string);
            assertEquals(
                    "[\n    \"recipe\",\n    {\n        \"cook_time\": \"3 hours\",\n        \"name\": \"bread\",\n        \"prep_time\": \"5 mins\"\n    },\n    [\n        \"title\",\n        \"Basic bread\"\n    ],\n    [\n        \"ingredient\",\n        {\n            \"amount\": 8,\n            \"unit\": \"dL\"\n        },\n        \"Flour\"\n    ],\n    [\n        \"ingredient\",\n        {\n            \"amount\": 10,\n            \"unit\": \"grams\"\n        },\n        \"Yeast\"\n    ],\n    [\n        \"ingredient\",\n        {\n            \"amount\": 4,\n            \"unit\": \"dL\",\n            \"state\": \"warm\"\n        },\n        \"Water\"\n    ],\n    [\n        \"ingredient\",\n        {\n            \"amount\": 1,\n            \"unit\": \"teaspoon\"\n        },\n        \"Salt\"\n    ],\n    [\n        \"instructions\",\n        [\n            \"step\",\n            \"Mix all ingredients together.\"\n        ],\n        [\n            \"step\",\n            \"Knead thoroughly.\"\n        ],\n        [\n            \"step\",\n            \"Cover with a cloth, and leave for one hour in warm room.\"\n        ],\n        [\n            \"step\",\n            \"Knead again.\"\n        ],\n        [\n            \"step\",\n            \"Place in a bread baking tin.\"\n        ],\n        [\n            \"step\",\n            \"Cover with a cloth, and leave for one hour in warm room.\"\n        ],\n        [\n            \"step\",\n            \"Bake in the oven at 180(degrees)C for 30 minutes.\"\n        ]\n    ]\n]",
                    jsonarray.toString(4));
            assertEquals(
                    "<recipe cook_time=\"3 hours\" name=\"bread\" prep_time=\"5 mins\"><title>Basic bread</title><ingredient amount=\"8\" unit=\"dL\">Flour</ingredient><ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient><ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient><ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient><instructions><step>Mix all ingredients together.</step><step>Knead thoroughly.</step><step>Cover with a cloth, and leave for one hour in warm room.</step><step>Knead again.</step><step>Place in a bread baking tin.</step><step>Cover with a cloth, and leave for one hour in warm room.</step><step>Bake in the oven at 180(degrees)C for 30 minutes.</step></instructions></recipe>",
                    JSONML.toString(jsonarray));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testToJSONObjectHtml()
    {
        try
        {
            string = "<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between <b>JSON</b> and <b>XML</b> that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>";
            jsonobject = JSONML.toJSONObject(string);
            assertEquals(
                    "{\n    \"id\": \"demo\",\n    \"tagName\": \"div\",\n    \"class\": \"JSONML\",\n    \"childNodes\": [\n        {\n            \"tagName\": \"p\",\n            \"childNodes\": [\n                \"JSONML is a transformation between\",\n                {\n                    \"tagName\": \"b\",\n                    \"childNodes\": [\"JSON\"]\n                },\n                \"and\",\n                {\n                    \"tagName\": \"b\",\n                    \"childNodes\": [\"XML\"]\n                },\n                \"that preserves ordering of document features.\"\n            ]\n        },\n        {\n            \"tagName\": \"p\",\n            \"childNodes\": [\"JSONML can work with JSON arrays or JSON objects.\"]\n        },\n        {\n            \"tagName\": \"p\",\n            \"childNodes\": [\n                \"Three\",\n                {\"tagName\": \"br\"},\n                \"little\",\n                {\"tagName\": \"br\"},\n                \"words\"\n            ]\n        }\n    ]\n}",
                    jsonobject.toString(4));
            assertEquals(
                    "<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between<b>JSON</b>and<b>XML</b>that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>",
                    JSONML.toString(jsonobject));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testToJSONArrayHtml()
    {
        try
        {
            string = "<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between <b>JSON</b> and <b>XML</b> that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>";
            
            jsonarray = JSONML.toJSONArray(string);
            assertEquals(
                    "[\n    \"div\",\n    {\n        \"id\": \"demo\",\n        \"class\": \"JSONML\"\n    },\n    [\n        \"p\",\n        \"JSONML is a transformation between\",\n        [\n            \"b\",\n            \"JSON\"\n        ],\n        \"and\",\n        [\n            \"b\",\n            \"XML\"\n        ],\n        \"that preserves ordering of document features.\"\n    ],\n    [\n        \"p\",\n        \"JSONML can work with JSON arrays or JSON objects.\"\n    ],\n    [\n        \"p\",\n        \"Three\",\n        [\"br\"],\n        \"little\",\n        [\"br\"],\n        \"words\"\n    ]\n]",
                    jsonarray.toString(4));
            assertEquals(
                    "<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between<b>JSON</b>and<b>XML</b>that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>",
                    JSONML.toString(jsonarray));

            string = "{\"xmlns:soap\":\"http://www.w3.org/2003/05/soap-envelope\",\"tagName\":\"soap:Envelope\",\"childNodes\":[{\"tagName\":\"soap:Header\"},{\"tagName\":\"soap:Body\",\"childNodes\":[{\"tagName\":\"ws:listProducts\",\"childNodes\":[{\"tagName\":\"ws:delay\",\"childNodes\":[1]}]}]}],\"xmlns:ws\":\"http://warehouse.acme.com/ws\"}";
            jsonobject = new JSONObject(string);
            assertEquals(
                    "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ws=\"http://warehouse.acme.com/ws\"><soap:Header/><soap:Body><ws:listProducts><ws:delay>1</ws:delay></ws:listProducts></soap:Body></soap:Envelope>",
                    JSONML.toString(jsonobject));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testToJsonArray_JsonInformation()
    {
        try
        {
        string = "<xml one = 1 two=' \"2\" '><five></five>First \u0009&lt;content&gt;<five></five> This is \"content\". <three>  3  </three>JSON does not preserve the sequencing of elements and contents.<three>  III  </three>  <three>  T H R E E</three><four/>Content text is an implied structure in XML. <six content=\"6\"/>JSON does not have implied structure:<seven>7</seven>everything is explicit.<![CDATA[CDATA blocks<are><supported>!]]></xml>";

        
        jsonarray = JSONML.toJSONArray(string);
        assertEquals(
                "[\n    \"xml\",\n    {\n        \"two\": \" \\\"2\\\" \",\n        \"one\": 1\n    },\n    [\"five\"],\n    \"First \\t<content>\",\n    [\"five\"],\n    \"This is \\\"content\\\".\",\n    [\n        \"three\",\n        3\n    ],\n    \"JSON does not preserve the sequencing of elements and contents.\",\n    [\n        \"three\",\n        \"III\"\n    ],\n    [\n        \"three\",\n        \"T H R E E\"\n    ],\n    [\"four\"],\n    \"Content text is an implied structure in XML.\",\n    [\n        \"six\",\n        {\"content\": 6}\n    ],\n    \"JSON does not have implied structure:\",\n    [\n        \"seven\",\n        7\n    ],\n    \"everything is explicit.\",\n    \"CDATA blocks<are><supported>!\"\n]",
                jsonarray.toString(4));
        assertEquals(
                "<xml two=\" &quot;2&quot; \" one=\"1\"><five/>First \t&lt;content&gt;<five/>This is &quot;content&quot;.<three></three>JSON does not preserve the sequencing of elements and contents.<three>III</three><three>T H R E E</three><four/>Content text is an implied structure in XML.<six content=\"6\"/>JSON does not have implied structure:<seven></seven>everything is explicit.CDATA blocks&lt;are&gt;&lt;supported&gt;!</xml>",
                JSONML.toString(jsonarray));

        
        }catch(JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testToJsonArray_SpanishNumbers()
    {
        try
        {
            string = "<xml do='0'>uno<a re='1' mi='2'>dos<b fa='3'/>tres<c>true</c>quatro</a>cinqo<d>seis<e/></d></xml>";
            jsonarray = JSONML.toJSONArray(string);
            assertEquals(
                    "[\n    \"xml\",\n    {\"do\": 0},\n    \"uno\",\n    [\n        \"a\",\n        {\n            \"re\": 1,\n            \"mi\": 2\n        },\n        \"dos\",\n        [\n            \"b\",\n            {\"fa\": 3}\n        ],\n        \"tres\",\n        [\n            \"c\",\n            true\n        ],\n        \"quatro\"\n    ],\n    \"cinqo\",\n    [\n        \"d\",\n        \"seis\",\n        [\"e\"]\n    ]\n]",
                    jsonarray.toString(4));
            assertEquals(
                    "<xml do=\"0\">uno<a re=\"1\" mi=\"2\">dos<b fa=\"3\"/>tres<c></c>quatro</a>cinqo<d>seis<e/></d></xml>",
                    JSONML.toString(jsonarray));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testToJsonArray_MusicNotes()
    {
        try
        {
            string = "<a ichi='1' ni='2'><b>The content of b</b> and <c san='3'>The content of c</c><d>do</d><e></e><d>re</d><f/><d>mi</d></a>";
            jsonobject = XML.toJSONObject(string);
            assertEquals(
                    "{\"a\":{\"f\":\"\",\"content\":\"and\",\"d\":[\"do\",\"re\",\"mi\"],\"ichi\":1,\"e\":\"\",\"b\":\"The content of b\",\"c\":{\"content\":\"The content of c\",\"san\":3},\"ni\":2}}",
                    jsonobject.toString());
            assertEquals(
                    "<a><f/>and<d>do</d><d>re</d><d>mi</d><ichi>1</ichi><e/><b>The content of b</b><c>The content of c<san>3</san></c><ni>2</ni></a>",
                    XML.toString(jsonobject));
            jsonarray = JSONML.toJSONArray(string);
            assertEquals("[\n" + "    \"a\",\n" + "    {\n"
                    + "        \"ichi\": 1,\n" + "        \"ni\": 2\n" + "    },\n"
                    + "    [\n" + "        \"b\",\n"
                    + "        \"The content of b\"\n" + "    ],\n"
                    + "    \"and\",\n" + "    [\n" + "        \"c\",\n"
                    + "        {\"san\": 3},\n" + "        \"The content of c\"\n"
                    + "    ],\n" + "    [\n" + "        \"d\",\n"
                    + "        \"do\"\n" + "    ],\n" + "    [\"e\"],\n"
                    + "    [\n" + "        \"d\",\n" + "        \"re\"\n"
                    + "    ],\n" + "    [\"f\"],\n" + "    [\n"
                    + "        \"d\",\n" + "        \"mi\"\n" + "    ]\n" + "]",
                    jsonarray.toString(4));
            assertEquals(
                    "<a ichi=\"1\" ni=\"2\"><b>The content of b</b>and<c san=\"3\">The content of c</c><d>do</d><e/><d>re</d><f/><d>mi</d></a>",
                    JSONML.toString(jsonarray));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testToJsonArray_TableOfContents()
    {
        try
        {
            string = "<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter      <chapter>Content of the first subchapter</chapter>      <chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>";
            
            jsonarray = JSONML.toJSONArray(string);
            assertEquals("[\n" + "    \"book\",\n" + "    [\n"
                    + "        \"chapter\",\n"
                    + "        \"Content of the first chapter\"\n" + "    ],\n"
                    + "    [\n" + "        \"chapter\",\n"
                    + "        \"Content of the second chapter\",\n"
                    + "        [\n" + "            \"chapter\",\n"
                    + "            \"Content of the first subchapter\"\n"
                    + "        ],\n" + "        [\n" + "            \"chapter\",\n"
                    + "            \"Content of the second subchapter\"\n"
                    + "        ]\n" + "    ],\n" + "    [\n"
                    + "        \"chapter\",\n" + "        \"Third Chapter\"\n"
                    + "    ]\n" + "]", jsonarray.toString(4));
            assertEquals(
                    "<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter<chapter>Content of the first subchapter</chapter><chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>",
                    JSONML.toString(jsonarray));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    
    }
    

    public void testToJsonObject_MessageXml()
    {
        try
        {
            string = "<Root><MsgType type=\"node\"><BatchType type=\"string\">111111111111111</BatchType></MsgType></Root>";
            jsonobject = JSONML.toJSONObject(string);
            assertEquals(
                    "{\"tagName\":\"Root\",\"childNodes\":[{\"tagName\":\"MsgType\",\"childNodes\":[{\"tagName\":\"BatchType\",\"childNodes\":[111111111111111],\"type\":\"string\"}],\"type\":\"node\"}]}",
                    jsonobject.toString());
            jsonarray = JSONML.toJSONArray(string);
            assertEquals(
                    "[\"Root\",[\"MsgType\",{\"type\":\"node\"},[\"BatchType\",{\"type\":\"string\"},111111111111111]]]",
                    jsonarray.toString());
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }    

    public void testToJsonArray_TableMapping()
    {
        try
        {
            string = "<mapping><empty/>   <class name = \"Customer\">      <field name = \"ID\" type = \"string\">         <bind-xml name=\"ID\" node=\"attribute\"/>      </field>      <field name = \"FirstName\" type = \"FirstName\"/>      <field name = \"MI\" type = \"MI\"/>      <field name = \"LastName\" type = \"LastName\"/>   </class>   <class name = \"FirstName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"MI\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"LastName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class></mapping>";
            
            jsonarray = JSONML.toJSONArray(string);
            assertEquals(
                    "[\n    \"mapping\",\n    [\"empty\"],\n    [\n        \"class\",\n        {\"name\": \"Customer\"},\n        [\n            \"field\",\n            {\n                \"name\": \"ID\",\n                \"type\": \"string\"\n            },\n            [\n                \"bind-xml\",\n                {\n                    \"node\": \"attribute\",\n                    \"name\": \"ID\"\n                }\n            ]\n        ],\n        [\n            \"field\",\n            {\n                \"name\": \"FirstName\",\n                \"type\": \"FirstName\"\n            }\n        ],\n        [\n            \"field\",\n            {\n                \"name\": \"MI\",\n                \"type\": \"MI\"\n            }\n        ],\n        [\n            \"field\",\n            {\n                \"name\": \"LastName\",\n                \"type\": \"LastName\"\n            }\n        ]\n    ],\n    [\n        \"class\",\n        {\"name\": \"FirstName\"},\n        [\n            \"field\",\n            {\"name\": \"text\"},\n            [\n                \"bind-xml\",\n                {\n                    \"node\": \"text\",\n                    \"name\": \"text\"\n                }\n            ]\n        ]\n    ],\n    [\n        \"class\",\n        {\"name\": \"MI\"},\n        [\n            \"field\",\n            {\"name\": \"text\"},\n            [\n                \"bind-xml\",\n                {\n                    \"node\": \"text\",\n                    \"name\": \"text\"\n                }\n            ]\n        ]\n    ],\n    [\n        \"class\",\n        {\"name\": \"LastName\"},\n        [\n            \"field\",\n            {\"name\": \"text\"},\n            [\n                \"bind-xml\",\n                {\n                    \"node\": \"text\",\n                    \"name\": \"text\"\n                }\n            ]\n        ]\n    ]\n]",
                    jsonarray.toString(4));
            assertEquals(
                    "<mapping><empty/><class name=\"Customer\"><field name=\"ID\" type=\"string\"><bind-xml node=\"attribute\" name=\"ID\"/></field><field name=\"FirstName\" type=\"FirstName\"/><field name=\"MI\" type=\"MI\"/><field name=\"LastName\" type=\"LastName\"/></class><class name=\"FirstName\"><field name=\"text\"><bind-xml node=\"text\" name=\"text\"/></field></class><class name=\"MI\"><field name=\"text\"><bind-xml node=\"text\" name=\"text\"/></field></class><class name=\"LastName\"><field name=\"text\"><bind-xml node=\"text\" name=\"text\"/></field></class></mapping>",
                    JSONML.toString(jsonarray));            
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
}