package pl.jordii.scoreboard.scheduler.synchronize;

/**
 * This interface is used when doing operations with the
 * {@link ServerMainThread.WaitForCompletion} class.
 *
 * method an instance of this class is requested as parameter, which implies the {@link #retrieve()} method.
 * It is similar to the normal {@link Runnable} interface with the only exception that it returns any object,
 * which represents the result of the calculation on the main thread.
 *
 */
@FunctionalInterface
public interface Retrievable {

  /**
   * Executes a calculation on the main thread and returns
   * the result as an object instance.
   *
   * @return The result of the operation
   */
  Object retrieve();

}
