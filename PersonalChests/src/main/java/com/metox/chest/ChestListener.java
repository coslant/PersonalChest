package com.metox.chest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChestListener implements Listener {

    private final PersonalChests plugin;
    private final ChestManager manager;

    private final Set<UUID> switching = new HashSet<UUID>();

    public ChestListener(PersonalChests plugin, ChestManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    // coslant
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory top = e.getInventory();
        if (top == null || !(top.getHolder() instanceof ChestHolder)) return;
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player p = (Player) e.getWhoClicked();
        ChestHolder holder = (ChestHolder) top.getHolder();

        if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
            e.setCancelled(true);
            return;
        }

        int raw = e.getRawSlot();
        if (raw < 0 || raw >= top.getSize()) {
            return;
        }

        if (manager.isNavSlot(raw)) {
            e.setCancelled(true);

            if (manager.isPrev(raw)) {
                turn(p, holder, top, holder.getPage() - 1);
            } else if (manager.isNext(raw)) {
                turn(p, holder, top, holder.getPage() + 1);
            } else if (manager.isClose(raw)) {
                p.closeInventory();
            }
        }
    }

    private void turn(final Player p, final ChestHolder holder, Inventory current, final int target) {
        if (target < 1 || target > manager.getTotalChests()) return;

        manager.save(current);
        switching.add(p.getUniqueId());

        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                Inventory next = manager.build(holder.getOwner(), holder.getOwnerName(), target, holder.isAdmin());
                p.openInventory(next);
                manager.playTurn(p);
            }
        });
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Inventory top = e.getInventory();
        if (top == null || !(top.getHolder() instanceof ChestHolder)) return;

        int size = top.getSize();
        for (int raw : e.getRawSlots()) {
            if (raw < size && manager.isNavSlot(raw)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    // coslant
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        if (inv == null || !(inv.getHolder() instanceof ChestHolder)) return;

        UUID id = e.getPlayer().getUniqueId();
        if (switching.remove(id)) {
            return;
        }
        manager.save(inv);
    }
}
