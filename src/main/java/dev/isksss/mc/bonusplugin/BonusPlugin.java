package dev.isksss.mc.bonusplugin;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BonusPlugin extends JavaPlugin implements Listener {

    List<String> playerList;
    FileConfiguration config;
    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.config = getConfig();
        playerList = new ArrayList<String>();

        getLogger().info("Register Event");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        PlayerJoinEvent.getHandlerList().unregister((Plugin) this);
        playerList = null;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String msg = "";

        if(!playerList.contains(p.getName())){
            Random r = new Random();
            int num = r.nextInt(1000);
            Material material;
            if(num > 500){
                material = Material.DIAMOND;
            }else{
                material = Material.IRON_INGOT;
            }

            ItemStack item = new ItemStack(material);
            item.setAmount(1);

            Inventory inv = p.getInventory();
            int emptySlot = inv.firstEmpty();
            if(emptySlot != -1){
                inv.addItem(item);
                msg = "we give a bonus to you!!";
                playerList.add(p.getName());
            }else{
                msg = "your inventory is not empty.\nwe don't give you today's bonus.";
            }

        }else{
            String serverName = config.getString("serverName");
            msg = "Welcome to "+ serverName +"!!";
        }

        p.sendMessage(msg);
    }

}
