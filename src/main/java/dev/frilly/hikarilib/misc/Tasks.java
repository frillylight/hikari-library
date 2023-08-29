package dev.frilly.hikarilib.misc;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

/**
 * Utility class for running tasks.
 */
@UtilityClass
public final class Tasks {

    /**
     * Runs a task synchronously and instantly.
     *
     * @param plugin   The plugin to run the task on.
     * @param consumer The task to run.
     */
    public void run(@NonNull JavaPlugin plugin, @NonNull Consumer<BukkitRunnable> consumer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        }.runTask(plugin);
    }

    /**
     * Runs a task asynchronously and instantly.
     *
     * @param plugin   The plugin to run the task on.
     * @param consumer The task to run.
     */
    public void runAsync(@NonNull JavaPlugin plugin, @NonNull Consumer<BukkitRunnable> consumer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        }.runTaskAsynchronously(plugin);
    }

    /**
     * Runs a task synchronously after a delay.
     *
     * @param plugin   The plugin to run the task on.
     * @param consumer The task to run.
     * @param delay    The delay in ticks.
     */
    public void runLater(@NonNull JavaPlugin plugin, @NonNull Consumer<BukkitRunnable> consumer, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        }.runTaskLater(plugin, delay);
    }

    /**
     * Runs a task asynchronously after a delay.
     *
     * @param plugin   The plugin to run the task on.
     * @param consumer The task to run.
     * @param delay    The delay in ticks.
     */
    public void runLaterAsync(@NonNull JavaPlugin plugin, @NonNull Consumer<BukkitRunnable> consumer, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        }.runTaskLaterAsynchronously(plugin, delay);
    }

    /**
     * Runs a task synchronously after a delay and then repeatedly after a period.
     *
     * @param plugin   The plugin to run the task on.
     * @param consumer The task to run.
     * @param delay    The delay in ticks.
     * @param period   The period in ticks.
     */
    public void runTimer(@NonNull JavaPlugin plugin, @NonNull Consumer<BukkitRunnable> consumer, long delay, long period) {
        new BukkitRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        }.runTaskTimer(plugin, delay, period);
    }

    /**
     * Runs a task asynchronously after a delay and then repeatedly after a period.
     *
     * @param plugin   The plugin to run the task on.
     * @param consumer The task to run.
     * @param delay    The delay in ticks.
     * @param period   The period in ticks.
     */
    public void runTimerAsync(@NonNull JavaPlugin plugin, @NonNull Consumer<BukkitRunnable> consumer, long delay, long period) {
        new BukkitRunnable() {
            @Override
            public void run() {
                consumer.accept(this);
            }
        }.runTaskTimerAsynchronously(plugin, delay, period);

    }

}
