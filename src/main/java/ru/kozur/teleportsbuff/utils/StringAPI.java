package ru.kozur.teleportsbuff.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

public class StringAPI {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F\\d]{6})");
    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    public static String asColor(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder builder = new StringBuilder(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(builder, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        message = matcher.appendTail(builder).toString();
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    public static Component serialize(String textToSerialize) {
        return LEGACY_COMPONENT_SERIALIZER.deserialize(textToSerialize).decoration(TextDecoration.ITALIC, false);
    }



}