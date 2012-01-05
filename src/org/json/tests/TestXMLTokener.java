/*
 * File:         TestXMLTokener.java
 * Author:       JSON.org <${Author_Email_Address}>
 */
package org.json.tests;

import org.json.JSONException;
import org.json.XMLTokener;

import junit.framework.TestCase;

/**
 * The Class TestXMLTokener.
 */
public class TestXMLTokener extends TestCase
{

    private XMLTokener xmltokener;

    /**
     * Tests the nextContent method.
     */
    public void testNextContent()
    {
        try
        {
            xmltokener = new XMLTokener("< abc><de&nbsp;f/></abc>");
            assertEquals('<', xmltokener.nextContent());
            assertEquals("abc>", xmltokener.nextContent());
            assertEquals('<', xmltokener.nextContent());
            assertEquals("de&nbsp;f/>", xmltokener.nextContent());
            assertEquals('<', xmltokener.nextContent());
            assertEquals("/abc>", xmltokener.nextContent());
            assertEquals(null, xmltokener.nextContent());
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    /**
     * Tests the nextCdata method.
     */
    public void testNextCdata()
    {
        try
        {
            xmltokener = new XMLTokener("<[CDATA[<abc/>]]>");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('[', xmltokener.next('['));
            assertEquals("CDATA", xmltokener.nextToken());
            assertEquals('[', xmltokener.next('['));
            assertEquals("<abc/>", xmltokener.nextCDATA());
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    /**
     * Tests the nextCdata method using broken cdata.
     */
    public void testNextCdata_BrokenCdata1()
    {
        try
        {
            xmltokener = new XMLTokener("<[CDATA[<abc/>]><abc>");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('[', xmltokener.next('['));
            assertEquals("CDATA", xmltokener.nextToken());
            assertEquals('[', xmltokener.next('['));
            xmltokener.nextCDATA();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Unclosed CDATA at 22 [character 23 line 1]", e.getMessage());
        }
    }
    
    /**
     * Tests the nextCdata method using broken cdata.
     */
    public void testNextCdata_BrokenCdata2()
    {
        try
        {
            xmltokener = new XMLTokener("<[CDATA[<abc/>]]<abc>");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('[', xmltokener.next('['));
            assertEquals("CDATA", xmltokener.nextToken());
            assertEquals('[', xmltokener.next('['));
            xmltokener.nextCDATA();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Unclosed CDATA at 22 [character 23 line 1]", e.getMessage());
        }
    }
    
    /**
     * Tests the nextCdata method using broken cdata.
     */
    public void testNextCdata_BrokenCdata3()
    {
        try
        {
            xmltokener = new XMLTokener("<[CDATA[<abc/>]]<abc>");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('[', xmltokener.next('['));
            assertEquals("CDATA", xmltokener.nextToken());
            assertEquals('[', xmltokener.next('['));
            xmltokener.nextCDATA();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Unclosed CDATA at 22 [character 23 line 1]", e.getMessage());
        }
    }
    
    /**
     * Tests the nextCdata method using broken cdata.
     */
    public void testNextCdata_BrokenCdata4()
    {
        try
        {
            xmltokener = new XMLTokener("<[CDATA[<abc/>");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('[', xmltokener.next('['));
            assertEquals("CDATA", xmltokener.nextToken());
            assertEquals('[', xmltokener.next('['));
            xmltokener.nextCDATA();
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Unclosed CDATA at 15 [character 16 line 1]", e.getMessage());
        }
    }
    
    /**
     * Tests the nextEntity method using ampersand.
     */
    public void testNextEntity_Ampersand()
    {
        try
        {
            xmltokener = new XMLTokener("<&amp;>");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('&', xmltokener.next('&'));
            assertEquals('&', xmltokener.nextEntity('&'));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }
    
    /**
     * Tests the nextEntity method using number entity.
     */
    public void testNextEntity_NumberEntity()
    {
        try
        {
            xmltokener = new XMLTokener("<&#60;>");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('&', xmltokener.next('&'));
            assertEquals("&#60;", xmltokener.nextEntity('&'));
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Tests the nextEntity method using broken entity.
     */
    public void testNextEntity_BrokenEntity()
    {
        try
        {
            xmltokener = new XMLTokener("<&nbsp");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('&', xmltokener.next('&'));
            assertEquals("&#60;", xmltokener.nextEntity('&'));
            fail("Should have thrown exception.");
        } catch (JSONException e)
        {
            assertEquals("Missing ';' in XML entity: &nbsp at 7 [character 8 line 1]", e.getMessage());
        }
    }

    /**
     * Tests the nextMeta method using string.
     */
    public void testNextMeta_String()
    {
        try
        {
            xmltokener = new XMLTokener("<! \"metaString\">");
            assertEquals('<', xmltokener.next('<'));
            assertEquals('!', xmltokener.next('!'));
            assertEquals(true, xmltokener.nextMeta());
        } catch (JSONException e)
        {
            fail(e.getMessage());
        }
    }

}