package net.kynon.divonixwp.events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kynon.divonixwp.classes.BetterEmbed;
import net.kynon.divonixwp.classes.BetterMessage;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class MemberLeaveEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(new FileInputStream("plugins/DWP/wpconfig.yml"));

            TextChannel channel = event.getGuild().getTextChannelById(data.get("leave-announce-channel").toString());
            channel.sendMessage(BetterMessage.message("leave", event.getMember(), event.getGuild()))
                    .addEmbeds(BetterEmbed.sendEmbed("leave", event.getMember(), event.getGuild()).build()).queue();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
