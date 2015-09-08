package org.lostmc.abacus;

import org.bukkit.plugin.java.JavaPlugin;
import utility.Log;

import java.util.logging.Level;

@SuppressWarnings("unused")
public class Abacus extends JavaPlugin {
    public static Log log;

    @Override
    public void onEnable() {
        log = new Log(getLogger(), Level.INFO);

        saveDefaultConfig();

        readConfig();
        setupCommands();
    }

    @Override
    public void onDisable() {
    }

    private void readConfig() {
        log.config("Reading configuration file");
        String logLevel = getConfig().getString("log-level");
        if (logLevel != null && !logLevel.isEmpty()) {
            log.setLevel(logLevel);
        }
    }

    private void setupCommands() {
        getCommand("abacus").setExecutor(new AbacusCommandExecutor(new CommandParser()));
    }
}
