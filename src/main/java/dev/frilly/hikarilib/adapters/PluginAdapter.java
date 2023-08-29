package dev.frilly.hikarilib.adapters;

import dev.frilly.hikarilib.collections.LateInitReference;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A task for holding the plugin instance of {@link dev.frilly.hikarilib.HikariLibrary}.
 */
@UtilityClass
public final class PluginAdapter {

    private LateInitReference<JavaPlugin> instance;

    /**
     * Gets the plugin reference.
     *
     * @return the reference
     */
    @NonNull
    public JavaPlugin getInstance() {
        return instance.get();
    }

    /**
     * Sets the plugin reference.
     *
     * @param plugin the reference
     */
    public void setInstance(@NonNull JavaPlugin plugin) {
        instance.set(plugin);
    }

}
