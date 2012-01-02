/*
 * File:         TestSuite.java
 * Author:       JSON.org
 */
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The Class techTreeSuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
        TestCDL.class, TestCookie.class, TestCookieList.class, TestHTTP.class,
        TestHTTPTokener.class, TestJSONArray.class, TestJSONException.class,
        TestJSONML.class, TestJSONObject.class, TestJSONString.class,
        TestJSONStringer.class, TestJSONTokener.class, TestJSONWriter.class,
        TestXML.class, TestXMLTokener.class, Test.class
})
public class TestSuite
{
    // Do Nothing
}
