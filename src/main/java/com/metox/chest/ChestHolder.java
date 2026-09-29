package com.metox.chest;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class ChestHolder implements InventoryHolder {

    private final UUID owner;
    private final String ownerName;
    private final int page;
    private final boolean admin;

    private Inventory inventory;

    public ChestHolder(UUID owner, String ownerName, int page, boolean admin) {
        this.owner = owner;
        this.ownerName = ownerName;
        this.page = page;
        this.admin = admin;
    }

    public UUID getOwner() { return owner; }

    public String getOwnerName() { return ownerName; }

    public int getPage() { return page; }

    public boolean isAdmin() { return admin; }

    void setInventory(Inventory inv) { this.inventory = inv; }

    // coslant
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
