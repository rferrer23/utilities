package com.stream.utils;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Static utility methods that convert  exceptions to un.
 * <p>
 * Two {@code wrap()} methods are provided that can wrap an arbitrary piece of logic
 * and convert  exceptions to un.
 * <p>
 * A number of other methods are provided that allow a lambda block to be decorated
 * to avoid handling  exceptions.
 * For example, the method {@link File#getCanonicalFile()} throws an {@link IOException}
 * which can be handled as follows:
 * <pre>
 *  stream.map(Un.function(file -&gt; file.getCanonicalFile())
 * </pre>
 * <p>
 * Each method accepts a functional interface that is defined to throw {@link Throwable}.
 * Catching {@code Throwable} means that any method can be wrapped.
 * Any {@code InvocationTargetException} is extracted and processed recursively.
 * Any {@link IOException} is converted to an {@link UnIOException}.
 * Any {@link ReflectiveOperationException} is converted to an {@link UnReflectiveOperationException}.
 * Any {@link Error} or {@link RuntimeException} is re-thrown without alteration.
 * Any other exception is wrapped in a {@link RuntimeException}.
 */
public interface UnChecked {

  //-------------------------------------------------------------------------
  /**
   * Wraps a block of code, converting  exceptions to un.
   * <pre>
   *   Un.wrap(() -&gt; {
   *     // any code that throws a  exception
   *   }
   * </pre>
   * <p>
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   *
   * @param block  the code block to wrap
   * @throws UnIOException if an IO exception occurs
   * @throws RuntimeException if an exception occurs
   */
  public default void wrap(Runnable block) {
    try {
      block.run();
    } catch (Throwable ex) {
      throw propagate(ex);
    }
  }

  /**
   * Wraps a block of code, converting  exceptions to un.
   * <pre>
   *   Un.wrap(() -&gt; {
   *     // any code that throws a  exception
   *   }
   * </pre>
   * <p>
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   *
   * @param <T> the type of the result
   * @param block  the code block to wrap
   * @return the result of invoking the block
   * @throws UnIOException if an IO exception occurs
   * @throws RuntimeException if an exception occurs
   */
  public default <T> T wrap(Supplier<T> block) {
    try {
      return block.get();
    } catch (Throwable ex) {
      throw propagate(ex);
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Converts  exceptions to un based on the {@code Runnable} interface.
   * <p>
   * This wraps the specified runnable returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param runnable  the runnable to be decorated
   * @return the runnable instance that handles  exceptions
   */
  public default Runnable runnable(Runnable runnable) {
    return () -> {
      try {
        runnable.run();
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts  exceptions to un based on the {@code Function} interface.
   * <p>
   * This wraps the specified function returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the input type of the function
   * @param <R>  the return type of the function
   * @param function  the function to be decorated
   * @return the function instance that handles  exceptions
   */
  public default <T, R> Function<T, R> function(Function<T, R> function) {
    return (t) -> {
      try {
        return function.apply(t);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  /**
   * Converts  exceptions to un based on the {@code BiFunction} interface.
   * <p>
   * This wraps the specified function returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the first input type of the function
   * @param <U>  the second input type of the function
   * @param <R>  the return type of the function
   * @param function  the function to be decorated
   * @return the function instance that handles  exceptions
   */
  public default <T, U, R> BiFunction<T, U, R> biFunction(BiFunction<T, U, R> function) {
    return (t, u) -> {
      try {
        return function.apply(t, u);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts  exceptions to un based on the {@code UnaryOperator} interface.
   * <p>
   * This wraps the specified operator returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the operator
   * @param function  the function to be decorated
   * @return the function instance that handles  exceptions
   */
  public default <T> UnaryOperator<T> unaryOperator(UnaryOperator<T> function) {
    return (t) -> {
      try {
        return function.apply(t);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  /**
   * Converts  exceptions to un based on the {@code BinaryOperator} interface.
   * <p>
   * This wraps the specified operator returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the operator
   * @param function  the function to be decorated
   * @return the function instance that handles  exceptions
   */
  public default <T> BinaryOperator<T> binaryOperator(BinaryOperator<T> function) {
    return (t, u) -> {
      try {
        return function.apply(t, u);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts  exceptions to un based on the {@code Predicate} interface.
   * <p>
   * This wraps the specified predicate returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the predicate
   * @param predicate  the predicate to be decorated
   * @return the predicate instance that handles  exceptions
   */
  public default <T> Predicate<T> predicate(Predicate<T> predicate) {
    return (t) -> {
      try {
        return predicate.test(t);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  /**
   * Converts  exceptions to un based on the {@code BiPredicate} interface.
   * <p>
   * This wraps the specified predicate returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the first type of the predicate
   * @param <U>  the second type of the predicate
   * @param predicate  the predicate to be decorated
   * @return the predicate instance that handles  exceptions
   */
  public default <T, U> BiPredicate<T, U> biPredicate(BiPredicate<T, U> predicate) {
    return (t, u) -> {
      try {
        return predicate.test(t, u);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts  exceptions to un based on the {@code Consumer} interface.
   * <p>
   * This wraps the specified consumer returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the consumer
   * @param consumer  the consumer to be decorated
   * @return the consumer instance that handles  exceptions
   */
  public default <T> Consumer<T> consumer(Consumer<T> consumer) {
    return (t) -> {
      try {
        consumer.accept(t);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  /**
   * Converts  exceptions to un based on the {@code BiConsumer} interface.
   * <p>
   * This wraps the specified consumer returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the first type of the consumer
   * @param <U>  the second type of the consumer
   * @param consumer  the consumer to be decorated
   * @return the consumer instance that handles  exceptions
   */
  public default <T, U> BiConsumer<T, U> biConsumer(BiConsumer<T, U> consumer) {
    return (t, u) -> {
      try {
        consumer.accept(t, u);
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts  exceptions to un based on the {@code Supplier} interface.
   * <p>
   * This wraps the specified supplier returning an instance that handles  exceptions.
   * If a  exception is thrown it is converted to an {@link UnIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <R>  the result type of the supplier
   * @param supplier  the supplier to be decorated
   * @return the supplier instance that handles  exceptions
   */
  public default <R> Supplier<R> supplier(Supplier<R> supplier) {
    return () -> {
      try {
        return supplier.get();
      } catch (Throwable ex) {
        throw propagate(ex);
      }
    };
  }

  /**
   * Propagates {@code throwable} as-is if possible, or by wrapping in a {@code RuntimeException} if not.
   * <ul>
   *   <li>If {@code throwable} is an {@code InvocationTargetException} the cause is extracted and processed recursively.</li>
   *   <li>If {@code throwable} is an {@code Error} or {@code RuntimeException} it is propagated as-is.</li>
   *   <li>If {@code throwable} is an {@code IOException} it is wrapped in {@code UnIOException} and thrown.</li>
   *   <li>If {@code throwable} is an {@code ReflectiveOperationException} it is wrapped in
   *     {@code UnReflectiveOperationException} and thrown.</li>
   *   <li>Otherwise {@code throwable} is wrapped in a {@code RuntimeException} and thrown.</li>
   * </ul>
   * This method always throws an exception. The return type is a convenience to satisfy the type system
   * when the enclosing method returns a value. For example:
   * <pre>
   *   T foo() {
   *     try {
   *       return methodWithException();
   *     } catch (Exception e) {
   *       throw Un.propagate(e);
   *     }
   *   }
   * </pre>
   *
   * @param throwable the {@code Throwable} to propagate
   * @return nothing; this method always throws an exception
   */
  public default RuntimeException propagate(Throwable throwable) {
	    if (throwable instanceof InvocationTargetException) {
	      throw propagate(((InvocationTargetException) throwable).getCause()); /// posible bucle infinito
	    } else if (throwable instanceof IOException) {
	      throw new UncheckedIOException((IOException) throwable);
	    }else {
	    	 throw new RuntimeException(throwable);
	    }
    }
}