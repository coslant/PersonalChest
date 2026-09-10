package com.metox.chest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChestCommand implements TabExecutor {

    private final PersonalChests plugin;
    private final ChestManager manager;

    public ChestCommand(PersonalChests plugin, ChestManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("chest.admin")) {
                manager.send(sender, "no-permission");
                return true;
            }
            plugin.reloadConfig();
            manager.reload();
            manager.send(sender, "reloaded");
            return true;
        }

        // coslant
        if (args.length >= 1 && args[0].equalsIgnoreCase("admin")) {
            if (!(sender instanceof Player)) {
                manager.send(sender, "players-only");
                return true;
            }
            if (!sender.hasPermission("chest.admin")) {
                manager.send(sender, "no-permission");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(Compat.color("&7/chest admin <oyuncu> [sayfa]"));
                return true;
            }

            UUID targetId;
            String targetName;

            Player online = Bukkit.getPlayerExact(args[1]);
            if (online != null) {
                targetId = online.getUniqueId();
                targetName = online.getName();
            } else {
                OfflinePlayer off = Bukkit.getOfflinePlayer(args[1]);
                if (off == null || off.getUniqueId() == null || (!off.hasPlayedBefore() && online == null)) {
                    manager.send(sender, "player-not-found");
                    return true;
                }
                targetId = off.getUniqueId();
                targetName = off.getName() != null ? off.getName() : args[1];
            }

            int page = 1;
            if (args.length >= 3) {
                Integer parsed = parse(args[2]);
                if (parsed == null) {
                    manager.send(sender, "invalid-page");
                    return true;
                }
                page = parsed;
            }
            if (page < 1 || page > manager.getTotalChests()) {
                manager.send(sender, "out-of-range", "%total%", String.valueOf(manager.getTotalChests()));
                return true;
            }

            Player viewer = (Player) sender;
            Inventory inv = manager.build(targetId, targetName, page, true);
            viewer.openInventory(inv);
            manager.playOpen(viewer);
            manager.send(viewer, "admin-opened", "%owner%", targetName, "%page%", String.valueOf(page));
            return true;
        }


        if (!(sender instanceof Player)) {
            manager.send(sender, "players-only");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("chest.use")) {
            manager.send(p, "no-permission");
            return true;
        }

        int page = 1;
        if (args.length >= 1) {
            Integer parsed = parse(args[0]);
            if (parsed == null) {
                manager.send(p, "invalid-page");
                return true;
            }
            page = parsed;
        }

        if (page < 1 || page > manager.getTotalChests()) {
            manager.send(p, "out-of-range", "%total%", String.valueOf(manager.getTotalChests()));
            return true;
        }

        Inventory inv = manager.build(p.getUniqueId(), p.getName(), page, false);
        p.openInventory(inv);
        manager.playOpen(p);
        manager.send(p, "opened", "%page%", String.valueOf(page));
        return true;
    }

    private Integer parse(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // coslant
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();

        if (args.length == 1) {
            List<String> base = new ArrayList<String>();
            for (int i = 1; i <= manager.getTotalChests(); i++) base.add(String.valueOf(i));
            if (sender.hasPermission("chest.admin")) {
                base.add("admin");
                base.add("reload");
            }
            String pref = args[0].toLowerCase();
            for (String b : base) {
                if (b.toLowerCase().startsWith(pref)) out.add(b);
            }
            return out;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("admin") && sender.hasPermission("chest.admin")) {
            String pref = args[1].toLowerCase();
            for (Player pl : Compat.online()) {
                if (pl.getName().toLowerCase().startsWith(pref)) out.add(pl.getName());
            }
            return out;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("admin") && sender.hasPermission("chest.admin")) {
            for (int i = 1; i <= manager.getTotalChests(); i++) out.add(String.valueOf(i));
            return out;
        }

        return out;
    }
}
