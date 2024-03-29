package pl.jordii.scoreboard.scheduler;

import java.util.UUID;

/**
 * This interface is used to provide the code, which should be
 * executed by a {@code Scheduler}. It is pretty similar to
 * a normal {@link Runnable} with the only difference that the
 * {@link #run(UUID)} method takes a {@link UUID} as a parameter.
 *
 * This parameter is the id of the scheduler within the {@link SchedulerRepository}.
 * This allows the developer to use methods such as {@link SchedulerRepository#interruptScheduler(UUID)}
 * which interrupts the scheduler with the given id.
 *
 */
@FunctionalInterface
public interface SchedulerRunnable {

  /**
   * The method which is executed on each scheduler iteration.
   *
   * @param taskId The id of the scheduler under which it is
   *               identified in the scheduler repository.
   */
  void run(UUID taskId);

}
