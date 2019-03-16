package com.utils;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
	
    public static void notNull(Object value, String propertyName) {
    	
        if (value == null) {
            throw new IllegalArgumentException(notNullMsg(propertyName));
        }
    }
    
    // extracted from notNull(Object,String) to aid hotspot inlining
    private static String notNullMsg(String propertyName) {
        return "Argument '" + propertyName + "' must not be null";
    }

    /**
     * Checks if the value is not blank, throwing an exception if it is.
     * <p>
     * Validate that the specified argument is not null and has at least one non-whitespace character.
     * 
     * @param value  the value to check, may be null
     * @param propertyName  the property name, should not be null
     * @throws IllegalArgumentException if the value is null or empty
     */
    public static void notBlank(String value, String propertyName) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(notBlank(propertyName));
        }
    }
    private static String notBlank(String propertyName) {
        return "Argument '" + propertyName + "' must not be empty";
	}
    
    //-----------------------------------------------------------------------
    /**
     * Checks if two objects are equal handling null.
     * 
     * @param obj1  the first object, may be null
     * @param obj2  the second object, may be null
     * @return true if equal
     */
    public static boolean equal(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        if (obj1.getClass().isArray()) {
            return equalsArray(obj1, obj2);
        }
        // this does not handle arrays embedded in objects, such as in lists/maps
        // but you shouldn't use arrays like that, should you?
        return obj1.equals(obj2);
    }

    // extracted from equal(Object,Object) to aid hotspot inlining
    private static boolean equalsArray(Object obj1, Object obj2) {
        if (obj1 instanceof Object[] && obj2 instanceof Object[] && obj1.getClass() == obj2.getClass()) {
            return Arrays.deepEquals((Object[]) obj1, (Object[]) obj2);
        } else if (obj1 instanceof int[] && obj2 instanceof int[]) {
            return Arrays.equals((int[]) obj1, (int[]) obj2);
        } else if (obj1 instanceof long[] && obj2 instanceof long[]) {
            return Arrays.equals((long[]) obj1, (long[]) obj2);
        } else if (obj1 instanceof byte[] && obj2 instanceof byte[]) {
            return Arrays.equals((byte[]) obj1, (byte[]) obj2);
        } else if (obj1 instanceof double[] && obj2 instanceof double[]) {
            return Arrays.equals((double[]) obj1, (double[]) obj2);
        } else if (obj1 instanceof float[] && obj2 instanceof float[]) {
            return Arrays.equals((float[]) obj1, (float[]) obj2);
        } else if (obj1 instanceof char[] && obj2 instanceof char[]) {
            return Arrays.equals((char[]) obj1, (char[]) obj2);
        } else if (obj1 instanceof short[] && obj2 instanceof short[]) {
            return Arrays.equals((short[]) obj1, (short[]) obj2);
        } else if (obj1 instanceof boolean[] && obj2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) obj1, (boolean[]) obj2);
        }
        // reachable if obj1 is an array and obj2 is not
        return false;
    }
    
    /**
     * Returns the {@code toString} value handling arrays.
     * 
     * @param obj  the object, may be null
     * @return the string, not null
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj.getClass().isArray()) {
            return toStringArray(obj);
        }
        return obj.toString();
    }

    // extracted from toString(Object) to aid hotspot inlining
    private static String toStringArray(Object obj) {
        if (obj instanceof Object[]) {
            return Arrays.deepToString((Object[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        }
        // unreachable?
        return obj.toString();
    }
    
    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if {@code expression} is false
     * @see Verify#verify Verify.verify()
     */
    public static void checkState(boolean expression) {
      if (!expression) {
        throw new IllegalStateException();
      }
  }

}
