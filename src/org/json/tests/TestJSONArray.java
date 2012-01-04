/*
 * File: TestJSONArray.java Author: JSON.org
 */
package org.json.tests;

import org.json.JSONArray;
import junit.framework.TestCase;

/**
 * The Class TestJSONArray.
 */
public class TestJSONArray extends TestCase {

	/**
	 * Tests the stub method.
	 */
	public static void testJsonArray_IntWithLeadingZeros() {
		JSONArray jsonarray;
		String string;

		try {
			string = "[001122334455]";
			jsonarray = new JSONArray(string);
			assertEquals("[1122334455]", jsonarray.toString());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Tests the jsonArray method using hmmm.
	 */
	public static void testJsonArray_Hmmm() {
		JSONArray jsonarray;
		String string;

		try {
			string = "[666e666]";
			jsonarray = new JSONArray(string);
			assertEquals("[\"666e666\"]", jsonarray.toString());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	/**
	 * Tests the jsonArray method using double with leading and trailing zeros.
	 */
	public static void testJsonArray_DoubleWithLeadingAndTrailingZeros() {
		JSONArray jsonarray;
		String string;

		try {
			string = "[00.10]";
			jsonarray = new JSONArray(string);
			assertEquals("[0.1]", jsonarray.toString());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}