/*
 * File: TestJSONWriter.java Author: JSON.org
 */
package org.json.tests;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONStringer;
import org.json.JSONWriter;

import junit.framework.TestCase;

/**
 * The Class TestJSONWriter.
 */
public class TestJSONWriter extends TestCase
{
    JSONWriter jsonwriter;

    class BadWriterThrowsOnNonBrace extends Writer
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#write(char[], int, int)
         */
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            if (cbuf[0] != '{')
                throw new IOException("Test Message From Non-Brace");
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#flush()
         */
        @Override
        public void flush() throws IOException
        {

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#close()
         */
        @Override
        public void close() throws IOException
        {

        }

    }

    class BadWriterThrowsOnLeftBrace extends Writer
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#write(char[], int, int)
         */
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            if (cbuf[0] == '{')
                throw new IOException("Test Message From Left Brace");
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#flush()
         */
        @Override
        public void flush() throws IOException
        {

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#close()
         */
        @Override
        public void close() throws IOException
        {

        }

    }

    class BadWriterThrowsOnRightBrace extends Writer
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#write(char[], int, int)
         */
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException
        {
            if (cbuf[0] == '}')
                throw new IOException("Test Message From Right Brace");
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#flush()
         */
        @Override
        public void flush() throws IOException
        {

        }

        /*
         * (non-Javadoc)
         * 
         * @see java.io.Writer#close()
         */
        @Override
        public void close() throws IOException
        {

        }

    }

    class BadExtensionThatCausesNestingError extends JSONStringer
    {
        public JSONWriter changeMode(char c)
        {
            mode = c;
            return this;
        }
    }
    
    public void testKey()
    {
        try
        {
            jsonwriter = new JSONStringer();
            String result = jsonwriter.object().key("abc").value("123")
                    .key("abc2").value(60).key("abc3").value(20.98).key("abc4")
                    .value(true).endObject().toString();
            assertEquals(
                    "{\"abc\":\"123\",\"abc2\":60,\"abc3\":20.98,\"abc4\":true}",
                    result);
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testValue()
    {
        try
        {
            jsonwriter = new JSONStringer();
            String result = jsonwriter.array().value("123").value(10)
                    .value(30.45).value(false).endArray().toString();
            assertEquals("[\"123\",10,30.45,false]", result);
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testObject_StopsAtMaxDepth()
    {
        try
        {
            jsonwriter = new JSONStringer();
            int i = 0;
            while (i < 201)
            {
                jsonwriter.object().key("123");
                i++;
            }
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Nesting too deep.", e.getMessage());
        }
    }

    public void testArray_StopsAtMaxDepth()
    {
        try
        {
            jsonwriter = new JSONStringer();
            int i = 0;
            while (i < 201)
            {
                jsonwriter.array();
                i++;
            }
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Nesting too deep.", e.getMessage());
        }
    }

    public void testValue_OutOfSequence()
    {
        try
        {
            jsonwriter = new JSONStringer();
            jsonwriter.value(true);
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Value out of sequence.", e.getMessage());
        }
    }

    public void testObject_OutOfSequence()
    {
        try
        {
            jsonwriter = new JSONStringer();
            jsonwriter.object().object();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Misplaced object.", e.getMessage());
        }
    }

    public void testObject_TwoObjectsWithinArray()
    {
        try
        {
            jsonwriter = new JSONStringer();
            String result = jsonwriter.array().object().endObject().object()
                    .endObject().endArray().toString();
            assertEquals("[{},{}]", result);
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testObject_TwoStringsAndAnIntWithinObject()
    {
        try
        {
            jsonwriter = new JSONStringer();
            String result = jsonwriter.object().key("string1").value("abc")
                    .key("int").value(35).key("string2").value("123")
                    .endObject().toString();
            assertEquals(
                    "{\"string1\":\"abc\",\"int\":35,\"string2\":\"123\"}",
                    result);
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testKey_MisplacedKey()
    {
        try
        {
            jsonwriter = new JSONStringer();
            jsonwriter.key("123");
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Misplaced key.", e.getMessage());
        }
    }

    public void testKey_CatchesIoexception()
    {
        try
        {
            jsonwriter = new JSONWriter(new BadWriterThrowsOnNonBrace());
            jsonwriter.object().key("123");
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Test Message From Non-Brace", e.getMessage());
        }
    }

    public void testObject_CatchesIoexception()
    {
        try
        {
            jsonwriter = new JSONWriter(new BadWriterThrowsOnLeftBrace());
            jsonwriter.object();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Test Message From Left Brace", e.getMessage());
        }
    }

    public void testKey_NullKey()
    {
        try
        {
            jsonwriter = new JSONStringer();
            jsonwriter.key(null);
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Null key.", e.getMessage());
        }
    }

    public void testArray_TwoArraysWithinObject()
    {
        try
        {
            jsonwriter = new JSONStringer();
            String result = jsonwriter.object().key("123").array().endArray()
                    .key("1234").array().endArray().endObject().toString();
            assertEquals("{\"123\":[],\"1234\":[]}", result);
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testObject_TwoObjectsWithinObject()
    {
        try
        {
            jsonwriter = new JSONStringer();
            String result = jsonwriter.object().key("123").object().endObject()
                    .key("1234").object().endObject().endObject().toString();
            assertEquals("{\"123\":{},\"1234\":{}}", result);
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testArray_TwoArraysWithinArray()
    {
        try
        {
            jsonwriter = new JSONStringer();
            String result = jsonwriter.array().array().endArray().array()
                    .endArray().endArray().toString();
            assertEquals("[[],[]]", result);
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    public void testArray_MisplacedArray()
    {
        try
        {
            jsonwriter = new JSONStringer();
            jsonwriter.object().array();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Misplaced array.", e.getMessage());
        }
    }

    public void testEndArray_MisplacedEndArray()
    {
        try
        {
            jsonwriter = new JSONStringer();
            jsonwriter.endArray();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Misplaced endArray.", e.getMessage());
        }
    }

    public void testEndObject_MisplacedEndObject()
    {
        try
        {
            jsonwriter = new JSONStringer();
            jsonwriter.endObject();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Misplaced endObject.", e.getMessage());
        }
    }

    public void testEndObject_CatchesIoexception()
    {
        try
        {
            jsonwriter = new JSONWriter(new BadWriterThrowsOnRightBrace());
            jsonwriter.object().endObject();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Test Message From Right Brace", e.getMessage());
        }
    }

    public void testPop_BadExtensionThatCausesNestingError1()
    {
        try
        {
            BadExtensionThatCausesNestingError betcnw = new BadExtensionThatCausesNestingError();
            betcnw.object().endObject();
            betcnw.changeMode('k').endObject();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Nesting error.", e.getMessage());
        }
    }

    public void testPop_BadExtensionThatCausesNestingError2()
    {
        try
        {
            BadExtensionThatCausesNestingError betcnw = new BadExtensionThatCausesNestingError();
            betcnw.array();
            betcnw.changeMode('k').endObject();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Nesting error.", e.getMessage());
        }
    }

}