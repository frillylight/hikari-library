package dev.frilly.hikarilib;

import dev.frilly.hikarilib.adapters.PluginAdapter;
import org.bukkit.plugin.java.JavaPlugin;

public final class HikariLibrary extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginAdapter.setInstance(this);
    }
    
}
