package red.man10.man10discordapiexample;

import net.dv8tion.jda.core.entities.ChannelType;
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
        plugin.api.sendMessage(event.getuser().getAsMention()+" さん *=*=* **Man10server公式Discordへようこそ！** *=*=*\n"+
                "#readme を読んだあとは楽しんでいってね！",plugin.discord.getserver().getDefaultChannel());
    }

    @Override
    public void Chat_reception(Chat_reception_Event event){
        plugin.getLogger().info("[msg]"+event.getcontent());
        String[] args = event.getcontent().split(" ");
        //もし、チャンネルタイプがtextなら。
        if(event.getMessage().getChannelType()==ChannelType.TEXT){
            //もし、チャンネルのIDがconfigのbot_channelと一致しているなら
            if(event.getMessage().getChannel().getId().equalsIgnoreCase(plugin.config1.getString("bot_channel"))) {
                //文字列がnullではないなら
                if (args.length != 0) {
                    //最初の文字が !man10 なら
                    if (args[0].equalsIgnoreCase("!man10")) {
                        //!man10 だけなら
                        if (args.length == 1) {
                            plugin.api.sendMessage("!man10 dice : ダイスを振る", event.getMessage().getChannel());
                            return;
                        //!man10 ○○ なら
                        } else if (args.length == 2) {
                            //!man10 dice なら
                            if(args[1].equalsIgnoreCase("dice")) {
                                Random rnd = new Random();
                                int dice = rnd.nextInt(5) + 1;
                                plugin.api.sendMessage(event.getMessage().getAuthor().getAsMention() + " は6面サイコロを振り **" + dice + "** が出た", event.getMessage().getChannel());
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
