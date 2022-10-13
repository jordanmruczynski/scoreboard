package pl.jordii.scoreboard.scheduler;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * This repository class contains information about the schedulers
 * you create with kelp. Every scheduler is assigned a {@link UUID}
 * once it has started. This uuid is used to identify them in the cache
 * and for example interrupt them if needed.
 *
 */
public class SchedulerRepository {

  private static SchedulerRepository instance = new SchedulerRepository();

  public static SchedulerRepository getInstance() {
    return instance;
  }

  // stores all async schedulers using a ScheduledExecutorService
  // wrapping a ScheduledFuture
  private ConcurrentMap<UUID, ScheduledFuture<?>> scheduledFutures;

  // Stores all sync schedulers using the normal bukkit executor.
  // The bukkit executor uses plain integers to identify tasks.
  private ConcurrentMap<UUID, Integer> bukkitTasks;

  public SchedulerRepository() {
    this.scheduledFutures = new ConcurrentHashMap<>();
    this.bukkitTasks = new ConcurrentHashMap<>();
  }

  /**
   * Registers a new async scheduler with a {@link ScheduledFuture} mostly wrapped
   * in a {@link ScheduledExecutorService}.
   *
   * This method should be called in the {@code run(UUID)} method of every kelp
   * scheduler.
   *
   * @param id                The {@link UUID} which has been assigned to the scheduler.
   * @param scheduledFuture   The {@link ScheduledFuture} which can be used to interrupt
   *                          the scheduler later
   * @return The kelp {@link UUID} of the task
   */
  public UUID registerScheduler(UUID id, ScheduledFuture<?> scheduledFuture) {
    this.scheduledFutures.put(id, scheduledFuture);
    return id;
  }

  /**
   * Registers a new sync scheduler with an integer, which is equivalent to the
   * bukkit task id.
   *
   * This method should be called in the {@code run(UUID)} method of every kelp
   * scheduler.
   *
   * @param id       The {@link UUID} which has been assigned to the scheduler by kelp.
   * @param taskId   The {@code Integer} which has been assigned to the scheduler by
   *                 bukkit
   * @return The kelp {@link UUID} of the task
   */
  public UUID registerScheduler(UUID id, int taskId) {
    this.bukkitTasks.put(id, taskId);
    return id;
  }

  /**
   * Interrupts a specific kelp scheduler with the given {@link UUID}.
   * This method automatically checks whether the task is sync
   * or async and either refers to the {@link ScheduledFuture} or
   * bukkit scheduler manager class.
   *
   * If the given scheduler is not registered, nothing will happen.
   *
   * @param uuid The id of the scheduler you want to cancel.
   */
  public void interruptScheduler(UUID uuid) {
    if (this.scheduledFutures.containsKey(uuid)) {
      this.scheduledFutures.get(uuid).cancel(true);
      this.scheduledFutures.remove(uuid);
      return;
    }
    if (this.bukkitTasks.containsKey(uuid)) {
      Bukkit.getScheduler().cancelTask(this.bukkitTasks.get(uuid));
      this.bukkitTasks.remove(uuid);
    }
  }

  /**
   * Interrupts all kelp schedulers which are currently active.
   *
   * NOTE: This method affects all schedulers, even those of other
   * kelp applications. So be careful when using it in your application.
   *
   * This method is called once on every server shutdown by the
   * kelp main class.
   */
  public void interruptAll() {
    Maps.newHashMap(scheduledFutures).forEach((id, task) -> {
      task.cancel(true);
      scheduledFutures.remove(id);
    });
    Maps.newHashMap(bukkitTasks).forEach((id, task) -> {
      Bukkit.getScheduler().cancelTask(this.bukkitTasks.get(id));
      this.bukkitTasks.remove(id);
    });
  }

}
