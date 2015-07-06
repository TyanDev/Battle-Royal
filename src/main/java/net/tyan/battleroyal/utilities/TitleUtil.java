package net.tyan.battleroyal.utilities;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * by Kevin on 23.06.2015.
 */

public class TitleUtil {

    public static void sendActionBar(Player player, String message) {
        ((CraftPlayer) player).getHandle().playerConnection
                .sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer
                        .a("{\"text\": \"" + message + "\"}"), (byte) 2));
    }

    public static void sendTitle(Player player, String message, int fadeIn, int stay, int fadeOut) {
        ((CraftPlayer) player).getHandle().playerConnection
                .sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer
                        .a("{\"text\": \"" + message + "\"}"), fadeIn, stay, fadeOut));
    }

    public static void sendTitle(Player p, String title, String subtitle, int fadein, int show, int fadeout) {

        PacketPlayOutTitle times = new PacketPlayOutTitle(fadein, show ,fadeout);
        new IChatBaseComponent.ChatSerializer();
        PacketPlayOutTitle Title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
        PacketPlayOutTitle SubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"));

        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(times);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(SubTitle);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(Title);
    }

    public static void sendTabTitle(Player p, String header, String footer) {
        if (header == null) header = "";

        if (footer == null) footer = "";

        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }
}
