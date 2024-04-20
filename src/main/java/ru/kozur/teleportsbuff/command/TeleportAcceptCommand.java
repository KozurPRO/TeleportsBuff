package ru.kozur.teleportsbuff.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.kozur.teleportsbuff.core.Teleport;

import static org.bukkit.Bukkit.getServer;

public class TeleportAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (args.length != 0) return false;
        Player player = getServer().getPlayer(sender.getName());
        Teleport.acceptTeleport(player);
        return true;
    }
}
