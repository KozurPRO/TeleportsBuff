package ru.kozur.teleportsbuff.core;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class Teleport {
    public static Map<Player, Player> players = new HashMap<>();

    public static void sendTeleport(Player sender, Player receiver) {

        if (players.containsKey(sender)) {
            sender.sendMessage("Вы уже отправили запрос на телепортацию!");
            return;
        }

        clickableMessage(sender, receiver);
        players.put(sender, receiver);
    }

    public static void acceptTeleport(Player receiver) {

            if (!(players.containsValue(receiver))) {
                receiver.sendMessage("У вас нет входящих запросов на телепортацию!");
                return;
            }

            Player sender = null;
            for (Map.Entry<Player, Player> entry : players.entrySet()) {
                if (entry.getValue().equals(receiver)) {
                    sender = entry.getKey();
                    break;
                }
            }

            if (sender != null) {
                sender.sendMessage("Игрок принял запрос на телепортацию!");
                receiver.sendMessage("Телепортируем...");
                receiver.teleport(sender.getLocation());
            } else {
                receiver.sendMessage("Игрок не найден в списке!");
            }
            players.remove(receiver);
    }

    public static void declineTeleport(Player receiver) {
        if (!(players.containsValue(receiver))) {
            receiver.sendMessage("У вас нет входящих запросов на телепортацию!");
            return;
        }

        Player sender = null;
        for (Map.Entry<Player, Player> entry : players.entrySet()) {
            if (entry.getValue().equals(receiver)) {
                sender = entry.getKey();
                break;
            }
        }

        if (sender != null) {
            sender.sendMessage("Игрок отклонил запрос на телепортацию!");
        } else {
            receiver.sendMessage("Игрок не найден в списке!");
        }

        receiver.sendMessage("Вы отклонили запрос на телепортацию!");
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
