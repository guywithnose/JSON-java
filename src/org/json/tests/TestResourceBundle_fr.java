package org.json.tests;

import java.util.*;
public class TestResourceBundle_fr extends ListResourceBundle {
    public Object[][] getContents() {
        return contents;
    }
    private Object[][] contents = {
            { "ASCII", "Number that represent chraracters" },
            { "JAVA", "The language you are running to see this" },
            { "JSON", "What are we testing?" },
        };
}