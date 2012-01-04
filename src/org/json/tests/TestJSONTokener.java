/*
 * File: TestJSONTokener.java Author: JSON.org
 */
package org.json.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONTokener;

import junit.framework.TestCase;

/**
 * The Class TestJSONTokener.
 */
public class TestJSONTokener extends TestCase
{
    
    /** The jsontokener. */
    JSONTokener jsontokener;

    
    /**
     * Tests the constructor method using input stream.
     */
    public void testConstructor_InputStream()
    {
        byte[] buf;
        String string = "{\"abc\":\"123\"}";
        buf = string.getBytes();
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        jsontokener = new JSONTokener(is);
        try
        {
            assertEquals('{', jsontokener.next());
            assertEquals("abc", jsontokener.nextValue());
            assertEquals(':', jsontokener.next());
            assertEquals("123", jsontokener.nextValue());
            assertEquals('}', jsontokener.next());
        } catch (JSONException e)
        {
            fail(e.toString());
        }
    }
    
    /**
     * Tests the back method.
     */
    public void testBack()
    {
        byte[] buf;
        String string = "{\"abc\":\"123\"}";
        buf = string.getBytes();
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        jsontokener = new JSONTokener(is);
        try
        {
            assertEquals('{', jsontokener.next());
            assertEquals("abc", jsontokener.nextValue());
            assertEquals(':', jsontokener.next());
            jsontokener.back();
            assertEquals(':', jsontokener.next());
            assertEquals("123", jsontokener.nextValue());
            assertEquals('}', jsontokener.next());
        } catch (JSONException e)
        {
            fail(e.toString());
        }
    }
    

    /**
     * Tests the back method using fails if used twice.
     */
    public void testBack_FailsIfUsedTwice()
    {
        byte[] buf;
        String string = "{\"abc\":\"123\"}";
        buf = string.getBytes();
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        jsontokener = new JSONTokener(is);
        try
        {
            assertEquals('{', jsontokener.next());
            assertEquals("abc", jsontokener.nextValue());
            assertEquals(':', jsontokener.next());
            jsontokener.back();
            jsontokener.back();
        } catch (JSONException e)
        {
            assertEquals("Stepping back two steps is not supported",e.getMessage());
        }
    }
    
    /**
     * Tests the next method using fake input stream to test ioexception.
     */
    public void testNext_FakeInputStreamToTestIoexception()
    {
        class MockInputStream extends InputStream
        {

            String data = "{\"abc\":\"123\"}";
            
            int position = 0;
            
            @Override
            public int read() throws IOException
            {
                char retVal = data.charAt(position);
                if(position < 3)
                    position++;
                else
                    throw new IOException("Mock IOException");
                return retVal;
            }            
        }
        
        jsontokener = new JSONTokener(new MockInputStream());
        try
        {
            assertEquals('{', jsontokener.next());
            assertEquals("abc", jsontokener.nextValue());
            assertEquals(':', jsontokener.next());
        } catch (JSONException e)
        {
            assertEquals("Mock IOException",e.getMessage());
        }
    }

    /**
     * Tests the next method using empty stream.
     */
    public void testNext_EmptyStream()
    {
        byte[] buf;
        String string = "";
        buf = string.getBytes();
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        jsontokener = new JSONTokener(is);
        try
        {
            assertEquals(0, jsontokener.next());
        } catch (JSONException e)
        {
            fail(e.toString());
        }
    }
    
}