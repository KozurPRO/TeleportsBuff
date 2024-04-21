package ru.kozur.teleportsbuff.core;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.kozur.teleportsbuff.TeleportsBuff;

import java.util.HashMap;
import java.util.Map;


public class Teleport {
    public static Map<Player, Player> players = new HashMap<>();

    public static void sendTeleport(Player sender, Player receiver) {

        if (players.containsKey(sender)) {
            sender.sendMessage("Вы уже отправили запрос на телепортацию!");
            return;
        }

        if (sender.getName().equals(receiver.getName())) {
            sender.sendMessage("Вы не можете отправить запрос себе!");
            return;
        }

        sender.sendMessage("Вы отправили запрос на телепортацию");
        clickableMessage(sender, receiver);
        players.put(sender, receiver);
    }

    public static void acceptTeleport(Player receiver) {

        if (!(players.containsValue(receiver))) {
            receiver.sendMessage("У вас нет входящих запросов на телепортацию!");
            return;
        }

        Player sender = players.entrySet().stream().filter(entry -> entry.getValue().equals(receiver)).findFirst().map(Map.Entry::getKey).orElse(null);

        if (sender == null) {
            receiver.sendMessage("Игрок не найден в списке");
            return;
        }
        sender.sendMessage("Игрок принял запрос на телепортацию!");
        receiver.sendMessage("Телепортируем через 7 секунд...");

        BossBar bossBar = Bukkit.createBossBar("Телепортация через:", BarColor.GREEN, BarStyle.SOLID);
        bossBar.setProgress(1.0);
        bossBar.addPlayer(sender);

        long dateNow = System.currentTimeMillis();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(TeleportsBuff.getInstance(), () -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - dateNow <= 7000) {
                bossBar.setTitle("Телепортация через: " + (7 - (currentTime - dateNow) / 1000) + " сек.");
            } else {
                sender.sendMessage("Телепортация...");
                sender.teleport(receiver.getLocation());
                bossBar.setVisible(false);
                bossBar.removeAll();
                Bukkit.getScheduler().cancelTasks(TeleportsBuff.getInstance());
            }

        }, 0L, 0L);


    }

    public static void declineTeleport(Player receiver) {
        if (!(players.containsValue(receiver))) {
            receiver.sendMessage("У вас нет входящих запросов на телепортацию!");
            return;
        }

        Player sender = players.entrySet().stream().filter(entry -> entry.getValue().equals(receiver)).findFirst().map(Map.Entry::getKey).orElse(null);

        if (sender == null) {
            receiver.sendMessage("Игрок не найден в списке!");
            return;
        }

        sender.sendMessage("Игрок отклонил запрос на телепортацию!");

        receiver.sendMessage("Вы отклонили запрос на телепортацию!");
        players.remove(receiver);
        players.remove(sender);
    }

    private static void clickableMessage(@NotNull Player myPlayer, Player targetPlayer) {
        targetPlayer.sendMessage("Игрок " + ChatColor.AQUA + myPlayer.getName() + ChatColor.WHITE + " хочет телепортироваться к вам");

        TextComponent accept = new TextComponent(ChatColor.GREEN + "[Принять]");
        TextComponent and = new TextComponent(ChatColor.WHITE + " | ");
        TextComponent decline = new TextComponent(ChatColor.RED + "[Отклонить]");

        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "Нажмите на кнопку что бы принять запрос на телепортацию")));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));

        decline.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "Нажмите на кнопку что бы отклонить запрос на телепортацию")));
        decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/decline"));
        targetPlayer.spigot().sendMessage(accept, and, decline);
    }
}
