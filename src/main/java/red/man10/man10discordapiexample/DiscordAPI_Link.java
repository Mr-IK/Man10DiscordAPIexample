package red.man10.man10discordapiexample;

import net.dv8tion.jda.core.entities.ChannelType;
import org.bukkit.Bukkit;
import red.man10.man10discordsync.Discorddata;
import red.man10.man10discordsync.Man10DiscordSync;
import red.man10.man10discordsync.chatapis.Chat_reception_Event;
import red.man10.man10discordsync.chatapis.Chat_reception_Listener;
import red.man10.man10discordsync.loginapis.Login_Event;
import red.man10.man10discordsync.loginapis.Login_Listener;

import java.util.Random;

public class DiscordAPI_Link implements Chat_reception_Listener,Login_Listener {

    Man10DiscordAPIexample plugin;
    Man10DiscordSync core;
    Discorddata data;
    public DiscordAPI_Link(Man10DiscordAPIexample plugin,Man10DiscordSync core){
        this.plugin = plugin;
        this.core = core;
        data = core.getData();
        core.getChatreception().addChat_reception_Listener(this);
        core.getLogin().addLogin_Listener(this);
    }

    @Override
    public void Login(Login_Event event){
        data.sendMessage(event.getuser().getName()+" さん * * ***Man10server公式Discordへようこそ！*** * *\n"+
                plugin.discord.getserver().getTextChannelsByName("readme",true).get(0).getAsMention()+" を読んだあとは楽しんでいってね！",plugin.discord.getserver().getDefaultChannel());
    }

    @Override
    public void Chat_reception(Chat_reception_Event event){
        plugin.getLogger().info("[msg]"+event.getcontent());
        String[] args = event.getcontent().split(" ");
        if (event.getMessage().isFromType(ChannelType.PRIVATE)){
        }else {
            if(args[0].equalsIgnoreCase("!man10")){
                if(args.length == 1){
                    data.sendMessage(event.getMessage().getAuthor().getAsMention()+" **====** **Man10DiscordHELP** **====**\n" +
                            "1: !man10 dice/サイコロ : サイコロを振ります\n" +
                            "2: !man10 link [Player名] : [Player名]とリンクし権限を同期します\n" +
                            "3: !man10 update [Player名] : [Player名]とのリンクをアップデートし権限を再同期します", event.getMessage().getChannel());
                    return;
                }else if(args.length == 2){
                    if(args[1].equalsIgnoreCase("dice")||args[1].equalsIgnoreCase("サイコロ")){
                        Random rnd = new Random();
                        int dice = rnd.nextInt(5)+1;
                        data.sendMessage(event.getMessage().getAuthor().getAsMention()+" はサイコロを振り**"+dice+"**が出た",event.getMessage().getChannel());
                        return;
                    }
                }else if(args.length == 3){
                    if(args[1].equalsIgnoreCase("link")){
                        if(Bukkit.getPlayer(args[2])==null){
                            data.sendMessage(event.getMessage().getAuthor().getAsMention()+" そのプレイヤーはオンラインではありません",event.getMessage().getChannel());
                            return;
                        }
                        if(data.playercontain(Bukkit.getPlayer(args[2]))){
                            data.sendMessage(event.getMessage().getAuthor().getAsMention()+" そのプレイヤーはすでにリンクされています",event.getMessage().getChannel());
                            return;
                        }
                        plugin.playerStates.put(Bukkit.getPlayer(args[2]).getUniqueId(),event.getMessage().getAuthor());
                        Bukkit.getPlayer(args[2]).sendMessage(plugin.prefix+"§e"+event.getMessage().getAuthor().getName()+"§aさんとのリンク申請を受け取りました: §6/mdiscord link");
                        data.sendMessage(event.getMessage().getAuthor().getAsMention()+" "+Bukkit.getPlayer(args[2]).getName()+"へリンク申請を行いました。ゲーム内で/mdiscord link を実行してください",event.getMessage().getChannel());
                        return;
                    }else if(args[1].equalsIgnoreCase("update")){
                        if(Bukkit.getPlayer(args[2])==null){
                            data.sendMessage(event.getMessage().getAuthor().getAsMention()+" そのプレイヤーはオンラインではありません",event.getMessage().getChannel());
                            return;
                        }
                        if(!data.playercontain(Bukkit.getPlayer(args[2]))){
                            data.sendMessage(event.getMessage().getAuthor().getAsMention()+" そのプレイヤーはまだにリンクされてません",event.getMessage().getChannel());
                            return;
                        }
                        plugin.playerStates.put(Bukkit.getPlayer(args[2]).getUniqueId(),event.getMessage().getAuthor());
                        Bukkit.getPlayer(args[2]).sendMessage(plugin.prefix+"§e"+event.getMessage().getAuthor().getName()+"§aさんとの再リンク申請を受け取りました: §6/mdiscord link");
                        data.sendMessage(event.getMessage().getAuthor().getAsMention()+" "+Bukkit.getPlayer(args[2]).getName()+"へ再リンク申請を行いました。ゲーム内で/mdiscord link を実行してください",event.getMessage().getChannel());
                        return;
                    }
                }
                data.sendMessage(event.getMessage().getAuthor().getAsMention()+" **====** **Man10DiscordHELP** **====**\n" +
                        "1: !man10 dice/サイコロ : サイコロを振ります\n" +
                        "2: !man10 link [Player名] : [Player名]とリンクし権限を同期します\n" +
                        "3: !man10 update [Player名] : [Player名]とのリンクをアップデートし権限を再同期します", event.getMessage().getChannel());
                return;
            }
        }
    }
}
