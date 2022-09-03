package net.kynon.divonixwp.events;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kynon.divonixwp.classes.BetterEmbed;
import net.kynon.divonixwp.classes.BetterMessage;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MemberJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(new FileInputStream("plugins/DWP/wpconfig.yml"));

            TextChannel channel = event.getGuild().getTextChannelById(data.get("join-announce-channel").toString());
            channel.sendMessage(BetterMessage.message("join", event.getMember(), event.getGuild()))
                    .addEmbeds(BetterEmbed.sendEmbed("join", event.getMember(), event.getGuild()).build()).queue();

            if (!Objects.equals(data.get("roles-on-join").toString(), null)) {
                List<Role> rolestoadd = new ArrayList<>();
                for (String s : data.get("ss-roles").toString().split(",")) {
                    rolestoadd.add(event.getGuild().getRoleById(s));
                }

                event.getGuild().modifyMemberRoles(event.getMember(), rolestoadd, Collections.emptyList()).queue();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
