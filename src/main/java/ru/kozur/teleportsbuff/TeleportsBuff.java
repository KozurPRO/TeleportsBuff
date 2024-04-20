package ru.kozur.teleportsbuff;

import org.bukkit.plugin.java.JavaPlugin;
import ru.kozur.teleportsbuff.command.TeleportAcceptCommand;
import ru.kozur.teleportsbuff.command.TeleportRequestCommand;
import ru.kozur.teleportsbuff.command.TeleportDeclineCommand;

public final class TeleportsBuff extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("teleport").setExecutor(new TeleportRequestCommand());
        getCommand("tpaccept").setExecutor(new TeleportAcceptCommand());
        getCommand("decline").setExecutor(new TeleportDeclineCommand());
    }

    @Override
    public void onDisable() {

    }
}
