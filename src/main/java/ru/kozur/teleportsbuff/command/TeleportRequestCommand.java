package ru.kozur.teleportsbuff.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.kozur.teleportsbuff.core.Teleport;


import static org.bukkit.Bukkit.getServer;

public class TeleportRequestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length != 1) return false;

        Player targetPlayer = getServer().getPlayer(args[0]);

        Teleport.sendTeleport(player,targetPlayer);
        return true;
    }
}
