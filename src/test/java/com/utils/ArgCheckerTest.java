package com.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.utils.TestHelper.assertThrowsIllegalArg;

import org.junit.Test;

public class ArgCheckerTest {
	public void test_isTrue_simple_ok() {
	    ArgChecker.isTrue(true);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_isTrue_simple_false() {
	    ArgChecker.isTrue(false);
	  }

	  public void test_isTrue_ok() {
	    ArgChecker.isTrue(true, "Message");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_isTrue_false() {
	    ArgChecker.isTrue(false, "Message");
	  }


	  //-------------------------------------------------------------------------
	  public void test_isFalse_ok() {
	    ArgChecker.isFalse(false, "Message");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_isFalse_true() {
	    ArgChecker.isFalse(true, "Message");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notNull_ok() {
	    assertEquals(ArgChecker.notNull("OG", "name"), "OG");
	    assertEquals(ArgChecker.notNull(1, "name"), Integer.valueOf(1));
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNull_null() {
	    ArgChecker.notNull(null, "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notNullItem_noText_ok() {
	    assertEquals(ArgChecker.notNullItem("OG"), "OG");
	    assertEquals(ArgChecker.notNullItem(1), Integer.valueOf(1));
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNullItem_noText_null() {
	    ArgChecker.notNullItem(null);
	  }

	  //-------------------------------------------------------------------------
	  public void test_matches_String_ok() {
	    assertEquals(ArgChecker.matches(Pattern.compile("[A-Z]+"), "OG", "name"), "OG");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_String_nullPattern() {
	    ArgChecker.matches(null, "", "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_String_nullString() {
	    ArgChecker.matches(Pattern.compile("[A-Z]+"), null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_String_empty() {
	    ArgChecker.matches(Pattern.compile("[A-Z]+"), "", "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_String_noMatch() {
	    ArgChecker.matches(Pattern.compile("[A-Z]+"), "123", "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_matches_CharMatcher_String_ok() {
	    assertEquals(ArgChecker.matches(CharMatcher.inRange('A', 'Z'), 1, Integer.MAX_VALUE, "OG", "name", "[A-Z]+"), "OG");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_CharMatcher_String_tooShort() {
	    ArgChecker.matches(CharMatcher.inRange('A', 'Z'), 1, 2, "", "name", "[A-Z]+");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_CharMatcher_String_tooLong() {
	    ArgChecker.matches(CharMatcher.inRange('A', 'Z'), 1, 2, "abc", "name", "[A-Z]+");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_CharMatcher_String_nullMatcher() {
	    ArgChecker.matches(null, 1, Integer.MAX_VALUE, "", "name", "[A-Z]+");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_CharMatcher_String_nullString() {
	    ArgChecker.matches(CharMatcher.inRange('A', 'Z'), 1, 2, null, "name", "[A-Z]+");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_matches_CharMatcher_String_noMatch() {
	    ArgChecker.matches(CharMatcher.inRange('A', 'Z'), 1, Integer.MAX_VALUE, "123", "name", "[A-Z]+");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notBlank_String_ok() {
	    assertEquals(ArgChecker.notBlank("OG", "name"), "OG");
	  }

	  public void test_notBlank_String_ok_notTrimmed() {
	    assertEquals(ArgChecker.notBlank(" OG ", "name"), " OG ");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notBlank_String_null() {
	    ArgChecker.notBlank(null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notBlank_String_empty() {
	    ArgChecker.notBlank("", "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notBlank_String_spaces() {
	    ArgChecker.notBlank("  ", "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notEmpty_String_ok() {
	    assertEquals(ArgChecker.notEmpty("OG", "name"), "OG");
	    assertEquals(ArgChecker.notEmpty(" ", "name"), " ");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_String_null() {
	    ArgChecker.notEmpty((String) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_String_empty() {
	    ArgChecker.notEmpty("", "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notEmpty_Array_ok() {
	    Object[] expected = new Object[] {"Element"};
	    Object[] result = ArgChecker.notEmpty(expected, "name");
	    assertEquals(result, expected);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Array_null() {
	    ArgChecker.notEmpty((Object[]) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Array_empty() {
	    ArgChecker.notEmpty(new Object[] {}, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_2DArray_null() {
	    ArgChecker.notEmpty((Object[][]) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_2DArray_empty() {
	    ArgChecker.notEmpty(new Object[0][0], "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notEmpty_intArray_ok() {
	    int[] expected = new int[] {6};
	    int[] result = ArgChecker.notEmpty(expected, "name");
	    assertEquals(result, expected);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_intArray_null() {
	    ArgChecker.notEmpty((int[]) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_intArray_empty() {
	    ArgChecker.notEmpty(new int[0], "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notEmpty_longArray_ok() {
	    long[] expected = new long[] {6L};
	    long[] result = ArgChecker.notEmpty(expected, "name");
	    assertEquals(result, expected);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_longArray_null() {
	    ArgChecker.notEmpty((long[]) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_longArray_empty() {
	    ArgChecker.notEmpty(new long[0], "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notEmpty_doubleArray_ok() {
	    double[] expected = new double[] {6.0d};
	    double[] result = ArgChecker.notEmpty(expected, "name");
	    assertEquals(result, expected);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_doubleArray_null() {
	    ArgChecker.notEmpty((double[]) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_doubleArray_empty() {
	    ArgChecker.notEmpty(new double[0], "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notEmpty_Iterable_ok() {
	    Iterable<String> expected = Arrays.asList("Element");
	    Iterable<String> result = ArgChecker.notEmpty((Iterable<String>) expected, "name");
	    assertEquals(result, expected);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Iterable_null() {
	    ArgChecker.notEmpty((Iterable<?>) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Iterable_empty() {
	    ArgChecker.notEmpty((Iterable<?>) Collections.emptyList(), "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notEmpty_Collection_ok() {
	    List<String> expected = Arrays.asList("Element");
	    List<String> result = ArgChecker.notEmpty(expected, "name");
	    assertEquals(result, expected);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Collection_null() {
	    ArgChecker.notEmpty((Collection<?>) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Collection_empty() {
	    ArgChecker.notEmpty(Collections.emptyList(), "name");
	  }



	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Map_null() {
	    ArgChecker.notEmpty((Map<?, ?>) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notEmpty_Map_empty() {
	    ArgChecker.notEmpty(Collections.emptyMap(), "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_noNulls_Array_ok() {
	    String[] expected = new String[] {"Element"};
	    String[] result = ArgChecker.noNulls(expected, "name");
	    assertEquals(result, expected);
	  }

	  public void test_noNulls_Array_ok_empty() {
	    Object[] array = new Object[] {};
	    ArgChecker.noNulls(array, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_noNulls_Array_null() {
	    ArgChecker.noNulls((Object[]) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_noNulls_Array_nullElement() {
	    ArgChecker.noNulls(new Object[] {null}, "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_noNulls_Iterable_ok() {
	    List<String> expected = Arrays.asList("Element");
	    List<String> result = ArgChecker.noNulls(expected, "name");
	    assertEquals(result, expected);
	  }

	  public void test_noNulls_Iterable_ok_empty() {
	    Iterable<?> coll = Arrays.asList();
	    ArgChecker.noNulls(coll, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_noNulls_Iterable_null() {
	    ArgChecker.noNulls((Iterable<?>) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_noNulls_Iterable_nullElement() {
	    ArgChecker.noNulls(Arrays.asList((Object) null), "name");
	  }


	  public void test_noNulls_Map_ok_empty() {
	    Map<Object, Object> map = new HashMap<>();
	    ArgChecker.noNulls(map, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_noNulls_Map_null() {
	    ArgChecker.noNulls((Map<Object, Object>) null, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_noNulls_Map_nullKey() {
	    Map<Object, Object> map = new HashMap<>();
	    map.put("A", "B");
	    map.put(null, "Z");
	    ArgChecker.noNulls(map, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_noNulls_Map_nullValue() {
	    Map<Object, Object> map = new HashMap<>();
	    map.put("A", "B");
	    map.put("Z", null);
	    ArgChecker.noNulls(map, "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notNegative_int_ok() {
	    assertEquals(ArgChecker.notNegative(0, "name"), 0);
	    assertEquals(ArgChecker.notNegative(1, "name"), 1);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegative_int_negative() {
	    ArgChecker.notNegative(-1, "name");
	  }

	  public void test_notNegative_long_ok() {
	    assertEquals(ArgChecker.notNegative(0L, "name"), 0L);
	    assertEquals(ArgChecker.notNegative(1L, "name"), 1L);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegative_long_negative() {
	    ArgChecker.notNegative(-1L, "name");
	  }

	  public void test_notNegative_double_ok() {
	    assertEquals(ArgChecker.notNegative(0d, "name"), 0d, 0.0001d);
	    assertEquals(ArgChecker.notNegative(1d, "name"), 1d, 0.0001d);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegative_double_negative() {
	    ArgChecker.notNegative(-1.0d, "name");
	  }

	  //-------------------------------------------------------------------------
	  public void test_notNegativeOrZero_int_ok() {
	    assertEquals(ArgChecker.notNegativeOrZero(1, "name"), 1);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegativeOrZero_int_zero() {
	    ArgChecker.notNegativeOrZero(0, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegativeOrZero_int_negative() {
	    ArgChecker.notNegativeOrZero(-1, "name");
	  }

	  public void test_notNegativeOrZero_long_ok() {
	    assertEquals(ArgChecker.notNegativeOrZero(1L, "name"), 1);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegativeOrZero_long_zero() {
	    ArgChecker.notNegativeOrZero(0L, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegativeOrZero_long_negative() {
	    ArgChecker.notNegativeOrZero(-1L, "name");
	  }

	  public void test_notNegativeOrZero_double_ok() {
	    assertEquals(ArgChecker.notNegativeOrZero(1d, "name"), 1d, 0.0001d);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegativeOrZero_double_zero() {
	    ArgChecker.notNegativeOrZero(0.0d, "name");
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notNegativeOrZero_double_negative() {
	    ArgChecker.notNegativeOrZero(-1.0d, "name");
	  }


	  //-------------------------------------------------------------------------
	  public void test_notZero_double_ok() {
	    assertEquals(ArgChecker.notZero(1d, "name"), 1d, 0.0001d);
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void test_notZero_double_zero() {
	    ArgChecker.notZero(0d, "name");
	  }

	  public void test_notZero_double_negative() {
	    ArgChecker.notZero(-1d, "name");
	  }


	  @SuppressWarnings("deprecation")
	public void test_double_inRange() {
	    double low = 0d;
	    double mid = 0.5d;
	    double high = 1d;
	    double small = 0.00000000001d;
	    assertEquals(ArgChecker.inRange(mid, low, high, "name"), mid);
	    assertEquals(ArgChecker.inRange(low, low, high, "name"), low);
	    assertEquals(ArgChecker.inRange(high - small, low, high, "name"), high - small);

	    assertEquals(ArgChecker.inRangeInclusive(mid, low, high, "name"), mid);
	    assertEquals(ArgChecker.inRangeInclusive(low, low, high, "name"), low);
	    assertEquals(ArgChecker.inRangeInclusive(high, low, high, "name"), high);

	    assertEquals(ArgChecker.inRangeExclusive(mid, low, high, "name"), mid);
	    assertEquals(ArgChecker.inRangeExclusive(small, low, high, "name"), small);
	    assertEquals(ArgChecker.inRangeExclusive(high - small, low, high, "name"), high - small);
	  }

	  public void test_double_inRange_outOfRange() {
	    double low = 0d;
	    double high = 1d;
	    double small = 0.00000000001d;
	    assertThrowsIllegalArg(() -> ArgChecker.inRange(low - small, low, high, "name"));
	    assertThrowsIllegalArg(() -> ArgChecker.inRange(high, low, high, "name"));

	    assertThrowsIllegalArg(() -> ArgChecker.inRangeInclusive(low - small, low, high, "name"));
	    assertThrowsIllegalArg(() -> ArgChecker.inRangeInclusive(high + small, low, high, "name"));

	    assertThrowsIllegalArg(() -> ArgChecker.inRangeExclusive(low, low, high, "name"));
	    assertThrowsIllegalArg(() -> ArgChecker.inRangeExclusive(high, low, high, "name"));
	  }

	  public void test_int_inRange() {
	    int low = 0;
	    int mid = 1;
	    int high = 2;
	    assertEquals(ArgChecker.inRange(mid, low, high, "name"), mid);
	    assertEquals(ArgChecker.inRange(low, low, high, "name"), low);

	    assertEquals(ArgChecker.inRangeInclusive(mid, low, high, "name"), mid);
	    assertEquals(ArgChecker.inRangeInclusive(low, low, high, "name"), low);
	    assertEquals(ArgChecker.inRangeInclusive(high, low, high, "name"), high);

	    assertEquals(ArgChecker.inRangeExclusive(mid, low, high, "name"), mid);
	  }

	  public void test_int_inRange_outOfRange() {
	    int low = 0;
	    int high = 1;
	    assertThrowsIllegalArg(() -> ArgChecker.inRange(low - 1, low, high, "name"));
	    assertThrowsIllegalArg(() -> ArgChecker.inRange(high, low, high, "name"));

	    assertThrowsIllegalArg(() -> ArgChecker.inRangeInclusive(low - 1, low, high, "name"));
	    assertThrowsIllegalArg(() -> ArgChecker.inRangeInclusive(high + 1, low, high, "name"));

	    assertThrowsIllegalArg(() -> ArgChecker.inRangeExclusive(low, low, high, "name"));
	    assertThrowsIllegalArg(() -> ArgChecker.inRangeExclusive(high, low, high, "name"));
	  }

	  @Test(expected = IllegalArgumentException.class)
	  public void testNotEmptyLongArray() {
	    ArgChecker.notEmpty(new double[0], "name");
	  }

}
