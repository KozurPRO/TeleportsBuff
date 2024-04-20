package ru.kozur.teleportsbuff.utils;

import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ButtonsAPI {
    @Getter
    private TextComponent button;

    public ButtonsAPI(String buttonText) {

        this.button = new TextComponent( TextComponent.fromLegacyText(StringAPI.asColor(buttonText)));
    }

    public void setHover(String textToShow) {
        this.button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(StringAPI.asColor(textToShow))));
    }
    public void setClickEvent(String commandString) {
        this.button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandString));
    }
}