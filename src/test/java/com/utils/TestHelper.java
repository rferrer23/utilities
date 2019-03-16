package com.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.stream.utils.Worker;

public class TestHelper {
	  /**
	   * Asserts that the object can be serialized and deserialized to an equal form.
	   * 
	   * @param base  the object to be tested
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	   */
	  public static void assertSerialization(Object base) throws IOException, ClassNotFoundException {
	    assertNotNull(base);
	      try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
	          oos.writeObject(base);
	          oos.close();
	          try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
	            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
	              assertEquals(ois.readObject(), base);
	            }
	          }
	        }
	      }
	  }

	  //-------------------------------------------------------------------------
	  /**
	   * Asserts that the lambda-based code throws the specified exception.
	   * <p>
	   * For example:
	   * <pre>
	   *  assertThrows(() -> bean.property(""), NoSuchElementException.class);
	   * </pre>
	   * 
	   * @param runner  the lambda containing the code to test
	   * @param expected  the expected exception
	   */
	  public static void assertThrows(Worker runner, Class<? extends Throwable> expected) {
	    assertThrowsImpl(runner, expected, null);
	}
	  
	  /**
	   * Asserts that the lambda-based code throws an {@code IllegalArgumentException}.
	   * <p>
	   * For example:
	   * <pre>
	   *  assertThrowsIllegalArg(() -> new Foo(null));
	   * </pre>
	   * 
	   * @param runner  the lambda containing the code to test
	   */
	  public static void assertThrowsIllegalArg(Worker runner) {
	    assertThrows(runner, IllegalArgumentException.class);
	}
	  
	  
	  private static void assertThrowsImpl( Worker runner,Class<? extends Throwable> expected, String regex) {

		  ObjectUtils.notNull(runner, "assertThrows() called with null AssertRunnable");
		  ObjectUtils.notNull(expected, "assertThrows() called with null expected Class");

		    try {
		      runner.run();
		      fail("Expected " + expected.getSimpleName() + " but code succeeded normally");
		    } catch (AssertionError ex) {
		      throw ex;
		    } catch (Throwable ex) {
		      if (expected.isInstance(ex)) {
		        String message = ex.getMessage();
		        if (regex == null || message.matches(regex)) {
		          return;
		        } else {
		          fail("Expected exception message to match: [" + regex + "] but received: " + message);
		        }
		      }
		     throw new RuntimeException ("Expected " + expected.getSimpleName() + " but received " + ex.getClass().getSimpleName(), ex);
		    }
		}
	  
}
