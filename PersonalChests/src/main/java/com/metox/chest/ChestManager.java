package com.metox.chest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ChestManager {

    private final PersonalChests plugin;
    private final File dataFolder;

    private int totalChests;
    private int rows;

    private int size;
    private int storageSize;
    private int navStart;

    private int prevSlot;
    private int infoSlot;
    private int nextSlot;
    private int closeSlot;

    private String titlePlayer;
    private String titleAdmin;

    private boolean soundEnabled;
    private String soundOpen;
    private String soundTurn;

    public ChestManager(PersonalChests plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();
        reload();
    }

    // coslant
    public void reload() {
        FileConfiguration c = plugin.getConfig();

        totalChests = Math.max(1, c.getInt("total-chests", 10));
        rows = c.getInt("rows", 6);
        if (rows < 2) rows = 2;
        if (rows > 6) rows = 6;

        size = rows * 9;
        navStart = (rows - 1) * 9;
        storageSize = navStart;

        prevSlot = navStart + 3;
        infoSlot = navStart + 4;
        nextSlot = navStart + 5;
        closeSlot = navStart + 8;

        titlePlayer = c.getString("titles.player", "&8Sandik &7#%page%");
        titleAdmin = c.getString("titles.admin", "&c[Admin] &8%owner% &7#%page%");

        soundEnabled = c.getBoolean("sound.enabled", true);
        soundOpen = c.getString("sound.open", "");
        soundTurn = c.getString("sound.page-turn", "");
    }

    public int getTotalChests() { return totalChests; }

    public int getStorageSize() { return storageSize; }

    public boolean isNavSlot(int raw) {
        return raw >= navStart && raw < size;
    }

    public boolean isPrev(int raw) { return raw == prevSlot; }

    public boolean isNext(int raw) { return raw == nextSlot; }

    public boolean isClose(int raw) { return raw == closeSlot; }


    public Inventory build(UUID owner, String ownerName, int page, boolean admin) {
        ChestHolder holder = new ChestHolder(owner, ownerName, page, admin);

        String fmt = admin ? titleAdmin : titlePlayer;
        String title = Compat.color(apply(fmt, page, ownerName));
        if (title.length() > 32) title = title.substring(0, 32);

        Inventory inv = Bukkit.createInventory(holder, size, title);
        holder.setInventory(inv);

        load(inv, owner, page);
        decorate(inv, page, ownerName);
        return inv;
    }

    private void decorate(Inventory inv, int page, String ownerName) {
        ItemStack filler = item("filler", page, ownerName);
        for (int i = navStart; i < size; i++) {
            inv.setItem(i, filler);
        }

        if (page > 1) inv.setItem(prevSlot, item("previous", page, ownerName));
        inv.setItem(infoSlot, item("info", page, ownerName));
        if (page < totalChests) inv.setItem(nextSlot, item("next", page, ownerName));
        inv.setItem(closeSlot, item("close", page, ownerName));
    }

    // coslant
    private ItemStack item(String key, int page, String ownerName) {
        String base = "items." + key + ".";
        FileConfiguration c = plugin.getConfig();

        Material mat = resolve(c.getString(base + "material", "STONE"), Material.STONE);
        ItemStack it = new ItemStack(mat);

        ItemMeta meta = it.getItemMeta();
        if (meta != null) {
            String name = c.getString(base + "name", "");
            meta.setDisplayName(Compat.color(apply(name, page, ownerName)));

            List<String> lore = c.getStringList(base + "lore");
            if (lore != null && !lore.isEmpty()) {
                for (int i = 0; i < lore.size(); i++) {
                    lore.set(i, Compat.color(apply(lore.get(i), page, ownerName)));
                }
                meta.setLore(lore);
            }
            it.setItemMeta(meta);
        }
        return it;
    }

    private Material resolve(String name, Material def) {
        if (name == null) return def;
        Material m = Material.matchMaterial(name.trim());
        return m != null ? m : def;
    }

    private String apply(String in, int page, String ownerName) {
        if (in == null) return "";
        return in.replace("%page%", String.valueOf(page))
                .replace("%total%", String.valueOf(totalChests))
                .replace("%owner%", ownerName == null ? "?" : ownerName);
    }


    private File fileFor(UUID uuid) {
        return new File(dataFolder, uuid.toString() + ".yml");
    }

    private void load(Inventory inv, UUID owner, int page) {
        File f = fileFor(owner);
        if (!f.exists()) return;

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        ConfigurationSection sec = cfg.getConfigurationSection("pages." + page);
        if (sec == null) return;

        for (String key : sec.getKeys(false)) {
            int slot;
            try {
                slot = Integer.parseInt(key);
            } catch (NumberFormatException e) {
                continue;
            }
            if (slot < 0 || slot >= storageSize) continue;

            ItemStack it = cfg.getItemStack("pages." + page + "." + key);
            if (it != null) inv.setItem(slot, it);
        }
    }

    public void save(Inventory inv) {
        if (inv == null) return;
        if (!(inv.getHolder() instanceof ChestHolder)) return;

        ChestHolder holder = (ChestHolder) inv.getHolder();
        UUID owner = holder.getOwner();
        int page = holder.getPage();

        File f = fileFor(owner);
        YamlConfiguration cfg = f.exists() ? YamlConfiguration.loadConfiguration(f) : new YamlConfiguration();

        cfg.set("pages." + page, null);

        for (int slot = 0; slot < storageSize; slot++) {
            ItemStack it = inv.getItem(slot);
            if (it == null || it.getType() == Material.AIR) continue;
            cfg.set("pages." + page + "." + slot, it);
        }

        try {
            cfg.save(f);
        } catch (IOException ex) {
            plugin.getLogger().warning("Sandik kaydedilemedi: " + f.getName() + " -> " + ex.getMessage());
        }
    }

    public void playOpen(org.bukkit.entity.Player p) {
        if (soundEnabled) Compat.playSound(p, soundOpen);
    }

    public void playTurn(org.bukkit.entity.Player p) {
        if (soundEnabled) Compat.playSound(p, soundTurn);
    }


    public String raw(String key) {
        return plugin.getConfig().getString("messages." + key, "");
    }

    public void send(CommandSender s, String key, String... repl) {
        String m = raw(key);
        if (m == null || m.isEmpty()) return;

        for (int i = 0; i + 1 < repl.length; i += 2) {
            m = m.replace(repl[i], repl[i + 1]);
        }
        s.sendMessage(Compat.color(raw("prefix") + m));
    }
}
