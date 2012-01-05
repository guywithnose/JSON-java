package org.json.tests;

import java.util.*;
public class TestResourceBundle_en_US extends ListResourceBundle {
    public Object[][] getContents() {
        return contents;
    }
    private Object[][] contents = {
        { "ASCII", "American Standard Code for Information Interchange" },
        { "JAVA.desc", "Just Another Vague Acronym" },
        { "JAVA.data", "Sweet language" },
        { "JSON", "JavaScript Object Notation" },
    };
}