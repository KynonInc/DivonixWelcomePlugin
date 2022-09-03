package net.kynon.divonixwp;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.kynon.divonix.plugins.DivonixPlugin;
import net.kynon.divonixwp.config.ConfigFile;
import net.kynon.divonixwp.events.MemberJoinEvent;
import net.kynon.divonixwp.events.MemberLeaveEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static net.kynon.divonix.Main.jda;

public class Main extends DivonixPlugin {

    public static String version = "1.1.0";

    @Override
    public void onEnable() {
        new File("plugins/DWP").mkdirs();

        ConfigFile.loadConfig();

        jda.addEventListener(new MemberJoinEvent());
        jda.addEventListener(new MemberLeaveEvent());
    }


    public static Map<String, Object> replacePlaceholders(Map<String, Object> input, Member member, Guild guild) {

        Map<String, Object> output = new HashMap<>();

        for (String key : input.keySet()) {
            if (input.get(key) != null) {
                String newkey = input.get(key).toString().replaceAll("%member-tag%", member.getUser().getAsTag())
                        .replaceAll("%member-name%", member.getUser().getName())
                        .replaceAll("%member-mention%", "<@!" + member.getId() + ">")
                        .replaceAll("%member-id%", member.getId())
                        .replaceAll("%total-members%", String.valueOf(guild.getMemberCount()))
                                .replaceAll("%bot-version%", version);

                if (member.getNickname() != null) {
                    newkey = newkey.replaceAll("%member-nickname%", member.getNickname());
                }
                else {
                    newkey = newkey.replaceAll("%member-nickname%", "none");
                }

                if (member.getAvatarUrl() != null) {
                    newkey = newkey.replaceAll("%member-avatar-url%", member.getUser().getAvatarUrl());
                }
                else {
                    newkey = newkey.replaceAll("%member-avatar-url%", "");
                }

                output.put(key, newkey);
            }
        }

        return output;
    }
}