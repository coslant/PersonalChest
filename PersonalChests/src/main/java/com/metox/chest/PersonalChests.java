package com.metox.chest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class PersonalChests extends JavaPlugin {

    private ChestManager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        manager = new ChestManager(this);

        ChestCommand cmd = new ChestCommand(this, manager);
        if (getCommand("chest") != null) {
            getCommand("chest").setExecutor(cmd);
            getCommand("chest").setTabCompleter(cmd);
        }

        getServer().getPluginManager().registerEvents(new ChestListener(this, manager), this);

        getLogger().info("PersonalChests aktif - algilanan surum 1." + Compat.MINOR);
        // coslant
    }

    @Override
    public void onDisable() {
        for (Player p : Compat.online()) {
            if (p.getOpenInventory() == null) continue;
            Inventory top = p.getOpenInventory().getTopInventory();
            if (top != null && top.getHolder() instanceof ChestHolder) {
                manager.save(top);
            }
        }
    }
}
