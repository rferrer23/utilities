package com.stream.utils.impl;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;



public class GeneralUnCheckedImplTest {
	
	public static GeneralUnCheckedImpl unchecket = new GeneralUnCheckedImpl(null);

	  //-------------------------------------------------------------------------
	  public void test_wrap_runnable1() {
	    // cannot use assertThrows() here
	    try {
	    	unchecket.wrap((Runnable) () -> {
	        throw new IOException();
	      });
	      fail();
	    } catch (UncheckedIOException ex) {
	      // success
	    }
	  }

	  public void test_wrap_runnable2() {
	    // cannot use assertThrows() here
	    try {
	      unchecket.wrap((Runnable) () -> {
	        throw new Exception();
	      });
	      fail();
	    } catch (RuntimeException ex) {
	      // success
	    }
	  }

	  //-------------------------------------------------------------------------
	  public void test_wrap_supplier() {
	    // cannot use assertThrows() here
	    try {
	      unchecket.wrap((Supplier<String>) () -> {
	        throw new IOException();
	      });
	      fail();
	    } catch (UncheckedIOException ex) {
	      // success
	    }
	  }

	  public void test_wrap_supplier2() {
	    // cannot use assertThrows() here
	    try {
	      unchecket.wrap((Supplier<String>) () -> {
	        throw new Exception();
	      });
	      fail();
	    } catch (RuntimeException ex) {
	      // success
	    }
	  }

	  //-------------------------------------------------------------------------
	  public void test_runnable_fail1() {
	    Runnable a = unchecket.runnable(() -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.run(), UncheckedIOException.class);
	  }

	  public void test_runnable_fail2() {
	    Runnable a = unchecket.runnable(() -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.run(), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_function_success() {
	    Function<String, String> a = unchecket.function((t) -> t);
	    assertEquals(a.apply("A"), "A");
	  }

	  public void test_function_fail1() {
	    Function<String, String> a = unchecket.function((t) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.apply("A"), UncheckedIOException.class);
	  }

	  public void test_function_fail2() {
	    Function<String, String> a = unchecket.function((t) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.apply("A"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_biFunction_success() {
	    BiFunction<String, String, String> a = unchecket.biFunction((t, u) -> t + u);
	    assertEquals(a.apply("A", "B"), "AB");
	  }

	  public void test_biFunction_fail1() {
	    BiFunction<String, String, String> a = unchecket.biFunction((t, u) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.apply("A", "B"), UncheckedIOException.class);
	  }

	  public void test_biFunction_fail2() {
	    BiFunction<String, String, String> a = unchecket.biFunction((t, u) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.apply("A", "B"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_unaryOperator_success() {
	    UnaryOperator<String> a = unchecket.unaryOperator((t) -> t);
	    assertEquals(a.apply("A"), "A");
	  }

	  public void test_unaryOperator_fail1() {
	    UnaryOperator<String> a = unchecket.unaryOperator((t) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.apply("A"), UncheckedIOException.class);
	  }

	  public void test_unaryOperator_fail2() {
	    UnaryOperator<String> a = unchecket.unaryOperator((t) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.apply("A"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_binaryOperator_success() {
	    BinaryOperator<String> a = unchecket.binaryOperator((t, u) -> t + u);
	    assertEquals(a.apply("A", "B"), "AB");
	  }

	  public void test_binaryOperator_fail1() {
	    BinaryOperator<String> a = unchecket.binaryOperator((t, u) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.apply("A", "B"), UncheckedIOException.class);
	  }

	  public void test_binaryOperator_fail2() {
	    BinaryOperator<String> a = unchecket.binaryOperator((t, u) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.apply("A", "B"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_predicate_success() {
	    Predicate<String> a = unchecket.predicate((t) -> true);
	    assertEquals(a.test("A"), true);
	  }

	  public void test_predicate_fail1() {
	    Predicate<String> a = unchecket.predicate((t) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.test("A"), UncheckedIOException.class);
	  }

	  public void test_predicate_fail2() {
	    Predicate<String> a = unchecket.predicate((t) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.test("A"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_biPredicate_success() {
	    BiPredicate<String, String> a = unchecket.biPredicate((t, u) -> true);
	    assertEquals(a.test("A", "B"), true);
	  }

	  public void test_biPredicate_fail1() {
	    BiPredicate<String, String> a = unchecket.biPredicate((t, u) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.test("A", "B"), UncheckedIOException.class);
	  }

	  public void test_biPredicate_fail2() {
	    BiPredicate<String, String> a = unchecket.biPredicate((t, u) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.test("A", "B"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_consumer_success() {
	    Consumer<String> a = unchecket.consumer((t) -> {});
	    a.accept("A");
	  }

	  public void test_consumer_fail1() {
	    Consumer<String> a = unchecket.consumer((t) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.accept("A"), UncheckedIOException.class);
	  }

	  public void test_consumer_fail2() {
	    Consumer<String> a = unchecket.consumer((t) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.accept("A"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_biConsumer_success() {
	    BiConsumer<String, String> a = unchecket.biConsumer((t, u) -> {});
	    a.accept("A", "B");
	  }

	  public void test_biConsumer_fail1() {
	    BiConsumer<String, String> a = unchecket.biConsumer((t, u) -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.accept("A", "B"), UncheckedIOException.class);
	  }

	  public void test_biConsumer_fail2() {
	    BiConsumer<String, String> a = unchecket.biConsumer((t, u) -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.accept("A", "B"), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_supplier_success() {
	    Supplier<String> a = unchecket.supplier(() -> "A");
	    assertEquals(a.get(), "A");
	  }

	  public void test_supplier_fail1() {
	    Supplier<String> a = unchecket.supplier(() -> {
	      throw new IOException();
	    });
	    assertThrows(() -> a.get(), UncheckedIOException.class);
	  }

	  public void test_supplier_fail2() {
	    Supplier<String> a = unchecket.supplier(() -> {
	      throw new Exception();
	    });
	    assertThrows(() -> a.get(), RuntimeException.class);
	  }

	  //-------------------------------------------------------------------------
	  public void test_validUtilityClass() {
	    assertUtilityClass(unchecket.class);
	  }

	  public void test_propagate() {
	    Error error = new Error("a");
	    IllegalArgumentException argEx = new IllegalArgumentException("b");
	    IOException ioEx = new IOException("c");
	    URISyntaxException namingEx = new URISyntaxException("d", "e");

	    // use old-style try-catch to ensure test really working
	    try {
	      unchecket.propagate(error);
	      fail();
	    } catch (Error ex) {
	      assertSame(ex, error);
	    }
	    try {
	      unchecket.propagate(argEx);
	      fail();
	    } catch (IllegalArgumentException ex) {
	      assertSame(ex, argEx);
	    }
	    try {
	      unchecket.propagate(ioEx);
	      fail();
	    } catch (UncheckedIOException ex) {
	      assertEquals(ex.getClass(), UncheckedIOException.class);
	      assertSame(ex.getCause(), ioEx);
	    }
	    try {
	      unchecket.propagate(namingEx);
	      fail();
	    } catch (RuntimeException ex) {
	      assertEquals(ex.getClass(), RuntimeException.class);
	      assertSame(ex.getCause(), namingEx);
	    }

	    try {
	      unchecket.propagate(new InvocationTargetException(error));
	      fail();
	    } catch (Error ex) {
	      assertSame(ex, error);
	    }
	    try {
	      unchecket.propagate(new InvocationTargetException(argEx));
	      fail();
	    } catch (IllegalArgumentException ex) {
	      assertSame(ex, argEx);
	    }
	    try {
	      unchecket.propagate(new InvocationTargetException(ioEx));
	      fail();
	    } catch (UncheckedIOException ex) {
	      assertEquals(ex.getClass(), UncheckedIOException.class);
	      assertSame(ex.getCause(), ioEx);
	    }
	    try {
	      unchecket.propagate(new InvocationTargetException(namingEx));
	      fail();
	    } catch (RuntimeException ex) {
	      assertEquals(ex.getClass(), RuntimeException.class);
	      assertSame(ex.getCause(), namingEx);
	    }
	}
}
