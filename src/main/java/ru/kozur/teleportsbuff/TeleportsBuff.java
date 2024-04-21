package ru.kozur.teleportsbuff;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.kozur.teleportsbuff.command.TeleportAcceptCommand;
import ru.kozur.teleportsbuff.command.TeleportRequestCommand;
import ru.kozur.teleportsbuff.command.TeleportDeclineCommand;

public final class TeleportsBuff extends JavaPlugin {
    @Getter
    public static TeleportsBuff instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("teleport").setExecutor(new TeleportRequestCommand());
        getCommand("tpaccept").setExecutor(new TeleportAcceptCommand());
        getCommand("decline").setExecutor(new TeleportDeclineCommand());
    }

    @Override
    public void onDisable() {

    }
}
