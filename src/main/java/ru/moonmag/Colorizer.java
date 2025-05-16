package ru.moonmag;

import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colorizer {

    private static final Pattern HEX_PATTERN = Pattern.compile("#&([A-Fa-f0-9]{6})");

    public static String colorize(String text) {
        if (text == null) return "";

        text = ChatColor.translateAlternateColorCodes('&', text);
        text = ChatColor.translateAlternateColorCodes('§', text);

        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder hexColor = new StringBuilder("§x");
            for (char c : hex.toCharArray()) {
                hexColor.append("§").append(c);
            }
            matcher.appendReplacement(buffer, hexColor.toString());
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }
}
