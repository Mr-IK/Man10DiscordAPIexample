package red.man10.man10discordapiexample;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import red.man10.man10discordsync.DiscordAPI;
import red.man10.man10discordsync.Discorddata;
import red.man10.man10discordsync.Man10DiscordSync;

import java.util.HashMap;
import java.util.UUID;


public final class Man10DiscordAPIexample extends JavaPlugin implements Listener{


    Man10DiscordSync core;
    DiscordAPI api;
    Discorddata discord;
    DiscordAPI_Link discordlink;
    HashMap<UUID,User> playerStates;
    FileConfiguration config1;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents (this,this);
        saveDefaultConfig();
        config1 = getConfig();
        playerStates = new HashMap<>();
        getLogger().info("Man10DiscordSyncにアクセスします…");
        core = (Man10DiscordSync) Bukkit.getPluginManager().getPlugin("Man10DiscordSync");
        api = core.getApi();
        discord = core.getData();
        discordlink = new DiscordAPI_Link(this,core);
        getLogger().info("Man10DiscordAPI認識完了!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
