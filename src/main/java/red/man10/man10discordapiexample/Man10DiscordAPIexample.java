package red.man10.man10discordapiexample;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import red.man10.man10discordsync.DiscordAPI;
import red.man10.man10discordsync.Discorddata;
import red.man10.man10discordsync.Man10DiscordSync;

import java.util.HashMap;
import java.util.UUID;


public final class Man10DiscordAPIexample extends JavaPlugin{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    getServer().getPluginManager().disablePlugin(this);
                    getServer().getPluginManager().enablePlugin(this);
                    getLogger().info("設定を再読み込みしました。");
                    return true;
                }
            }
            getLogger().info("mas reload");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage("==========§d§l●§f§l●§a§l●" + prefix + "§a§l●§f§l●§d§l●§r==========");
            p.sendMessage("§e--プレイヤー向けヘルプ--");
            p.sendMessage("§e/mdiscord link : discord側で !man10 link [自分の名前]を実行した後このコマンドを実行してね");
            p.sendMessage("§e/mdiscord check : あなたがlinkしているユーザーの表示");
            if (p.hasPermission("man10discord.adminhelp")) {
                p.sendMessage("§c--admin向けヘルプ--");
                p.sendMessage("§c/mdiscord check [ユーザー名]");
            }
            p.sendMessage("==========§d§l●§f§l●§a§l●" + prefix + "§a§l●§f§l●§d§l●§r==========");
            return true;
        }else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("link")){
                if(!playerStates.containsKey(p.getUniqueId())){
                    p.sendMessage(prefix+"§4リンクの情報があなたには来ていません");
                    return true;
                }
                if(!discord.playercontain(p)) {
                    p.sendMessage(prefix + "§a" + playerStates.get(p.getUniqueId()).getName() + "とリンクし、discordで権限を付与しました");
                    playerStates.get(p.getUniqueId()).openPrivateChannel().complete().sendMessage(p.getName() + "とリンクし、権限を付与しました").queue();
                    if (p.hasPermission("man10discord.mage_cyborg")) {
                        discord.getserver().getController().addSingleRoleToMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())), discord.getserver().getRoleById(config1.getString("mage_cyborg"))).queue();
                    } else if (p.hasPermission("man10discord.ultra_super_vip")) {
                        discord.getserver().getController().addSingleRoleToMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())), discord.getserver().getRoleById(config1.getString("ultra_super_vip"))).queue();
                    } else if (p.hasPermission("man10discord.expert_member")) {
                        discord.getserver().getController().addSingleRoleToMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())), discord.getserver().getRoleById(config1.getString("expert_member"))).queue();
                    }
                    discord.playercreate(p, playerStates.get(p.getUniqueId()).getId());
                    playerStates.remove(p.getUniqueId());
                    return true;
                }else{
                    p.sendMessage(prefix + "§a" + playerStates.get(p.getUniqueId()).getName() + "と再リンクし、discordで権限を付与しました");
                    playerStates.get(p.getUniqueId()).openPrivateChannel().complete().sendMessage(p.getName() + "と再リンクし、権限を付与しました").queue();
                    discord.getserver().getController().removeRolesFromMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())),discord.getserver().getRoleById(config1.getString("mage_cyborg"))).queue();
                    discord.getserver().getController().removeRolesFromMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())),discord.getserver().getRoleById(config1.getString("ultra_super_vip"))).queue();
                    discord.getserver().getController().removeRolesFromMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())),discord.getserver().getRoleById(config1.getString("expert_member"))).queue();
                    if (p.hasPermission("man10discord.mage_cyborg")) {
                        discord.getserver().getController().addSingleRoleToMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())), discord.getserver().getRoleById(config1.getString("mage_cyborg"))).queue();
                    } else if (p.hasPermission("man10discord.ultra_super_vip")) {
                        discord.getserver().getController().addSingleRoleToMember(discord.getserver().getMember(playerStates.get(p.getUniqueId())), discord.getserver().getRoleById(config1.getString("ultra_super_vip"))).queue();
                    } else if (p.hasPermission("man10discord.expert_member")) {
                    }
                    playerStates.remove(p.getUniqueId());
                    return true;
                }
            }else if(args[0].equalsIgnoreCase("check")){
                if(!discord.playercontain(p)){
                    p.sendMessage(prefix+"§4あなたはリンクを行っていません");
                    return true;
                }
                String name = discord.getdiscordname(p);
                p.sendMessage(prefix+"§e"+p.getName()+" §6<=link=>§e "+name);
                return true;
            }
        }else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("check")){
                if(!p.hasPermission("man10discord.check")){
                    p.sendMessage(prefix+"§4あなたには他人の情報を見る権限がありません");
                    return true;
                }
                if(Bukkit.getPlayer(args[1])==null){
                    p.sendMessage(prefix+"§4そのプレイヤーはオンラインではありません");
                    return true;
                }
                if(!discord.playercontain(Bukkit.getPlayer(args[1]))){
                    p.sendMessage(prefix+"§4そのプレイヤーはリンクを行っていません");
                    return true;
                }
                String name = discord.getdiscordname(Bukkit.getPlayer(args[1]));
                p.sendMessage(prefix+"§e"+args[1]+" §6<=link=>§e "+name);
                return true;
            }
        }
        return true;
    }

    Man10DiscordSync core;
    DiscordAPI api;
    Discorddata discord;
    DiscordAPI_Link discordlink;
    HashMap<UUID,User> playerStates;
    FileConfiguration config1;
    String prefix = "§1[§dM§bDiscord§1]§r";
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        config1 = getConfig();
        playerStates = new HashMap<>();
        getLogger().info("Man10DiscordSyncにアクセスします…");
        core = (Man10DiscordSync) Bukkit.getPluginManager().getPlugin("Man10DiscordSync");
        api = core.getApi();
        discord = core.getData();
        discordlink = new DiscordAPI_Link(this,core);
        getCommand("mdiscord").setExecutor(this);
        getLogger().info("Man10DiscordAPI認識完了!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
