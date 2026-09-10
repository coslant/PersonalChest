package com.metox.chest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// coslant
public final class Compat {

    public static final int MINOR;

    static {
        int m = 8;
        try {
            String raw = Bukkit.getBukkitVersion().split("-")[0];
            String[] parts = raw.split("\\.");
            if (parts.length >= 2) m = Integer.parseInt(parts[1]);
        } catch (Throwable t) {
            m = 8;
        }
        MINOR = m;
    }

    private Compat() {}


    public static String color(String in) {
        if (in == null) return "";
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> color(List<String> in) {
        if (in == null) return null;
        for (int i = 0; i < in.size(); i++) {
            in.set(i, color(in.get(i)));
        }
        return in;
    }

    public static void playSound(Player p, String name) {
        if (name == null || name.isEmpty()) return;
        Sound s = matchSound(name);
        if (s == null) return;
        try {
            p.playSound(p.getLocation(), s, 1f, 1f);
        } catch (Throwable ignored) {
        }
    }

    private static Sound matchSound(String name) {
        String want = name.trim();
        for (Sound s : Sound.values()) {
            if (s.name().equalsIgnoreCase(want)) return s;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static List<Player> online() {
        List<Player> list = new ArrayList<Player>();
        try {
            Object res = Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
            if (res instanceof Player[]) {
                for (Player p : (Player[]) res) list.add(p);
            } else if (res instanceof Collection) {
                for (Object o : (Collection<Object>) res) list.add((Player) o);
            }
        } catch (Throwable ignored) {
        }
        return list;
    }
}
