package ru.moonmag;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collections;
import java.util.List;

public class SpaceResourcePack extends JavaPlugin implements Listener, TabExecutor {

    @Override
    public void onEnable() {
        getLogger().info("§x§f§f§7§c§0§0╔");
        getLogger().info("§x§f§f§7§c§0§0║ §fЗапуск плагина...");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§f§1§7Плагин запустился! §fКодер: §x§f§f§6§e§0§0SpaceDev");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§5§f§fh§x§0§0§f§4§f§ft§x§0§0§f§3§f§ft§x§0§0§f§2§f§fp§x§0§0§f§1§f§fs§x§0§0§e§f§f§f:§x§0§0§e§e§f§f/§x§0§0§e§d§f§f/§x§0§0§e§c§f§ft§x§0§0§e§b§f§f.§x§0§0§e§a§f§fm§x§0§0§e§9§f§fe§x§0§0§e§8§f§f/§x§0§0§e§7§f§fs§x§0§0§e§5§f§fp§x§0§0§e§4§f§fa§x§0§0§e§3§f§fc§x§0§0§e§2§f§fe§x§0§0§e§1§f§fs§x§0§0§e§0§f§ft§x§0§0§d§f§f§fu§x§0§0§d§e§f§fd§x§0§0§d§c§f§fi§x§0§0§d§b§f§fo§x§0§0§d§a§f§fm§x§0§0§d§9§f§fc");
        getLogger().info("§x§f§f§7§c§0§0╚");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("ssrp").setExecutor(this);
        getCommand("ssrp").setTabCompleter(this);
        saveDefaultConfig();
        new ru.moonmag.Metrics(this, 25598);
        new UpdateChecker(this).checkForUpdates();
        }

    @Override
    public void onDisable() {
        getLogger().info("§x§f§f§7§c§0§0╔");
        getLogger().info("§x§f§f§7§c§0§0║ §fОтключение плагина...");
        getLogger().info("§x§f§f§7§c§0§0║ §§x§f§f§0§0§0§0Плагин отключился! §fКодер: §x§f§f§6§e§0§0SpaceDev");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§5§f§fh§x§0§0§f§4§f§ft§x§0§0§f§3§f§ft§x§0§0§f§2§f§fp§x§0§0§f§1§f§fs§x§0§0§e§f§f§f:§x§0§0§e§e§f§f/§x§0§0§e§d§f§f/§x§0§0§e§c§f§ft§x§0§0§e§b§f§f.§x§0§0§e§a§f§fm§x§0§0§e§9§f§fe§x§0§0§e§8§f§f/§x§0§0§e§7§f§fs§x§0§0§e§5§f§fp§x§0§0§e§4§f§fa§x§0§0§e§3§f§fc§x§0§0§e§2§f§fe§x§0§0§e§1§f§fs§x§0§0§e§0§f§ft§x§0§0§d§f§f§fu§x§0§0§d§e§f§fd§x§0§0§d§c§f§fi§x§0§0§d§b§f§fo§x§0§0§d§a§f§fm§x§0§0§d§9§f§fc");
        getLogger().info("§x§f§f§7§c§0§0╚");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("spaceresourcepack")) return false;

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("ssrp.admin")) {
                reloadConfig();
                sender.sendMessage(Colorizer.colorize(getConfig().getString("reload")));
            } else {
                sender.sendMessage(Colorizer.colorize(getConfig().getString("nopermission")));
            }
            return true;
        }

        sender.sendMessage(Colorizer.colorize(getConfig().getString("usage")));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("spaceresourcepack")) {
            if (args.length == 1 && sender.hasPermission("ssrp.admin")) {
                return Collections.singletonList("reload");
            }
        }
        return Collections.emptyList();
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();

        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            String kickPrefix = Colorizer.colorize(getConfig().getString("KickPrefix"));
            String line1 = Colorizer.colorize(getConfig().getString("Line-1"));
            String line2 = Colorizer.colorize(getConfig().getString("Line-2"));
            String kickMessage = Colorizer.colorize(getConfig().getString("KickMessage")).replace("%player%", player.getName());

            player.kickPlayer(kickPrefix + " " + line1 + "\n" + line2);

            for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("ssrp.admin")) {
                    onlinePlayer.sendMessage(kickMessage);
                }
            }
        }
    }
}
