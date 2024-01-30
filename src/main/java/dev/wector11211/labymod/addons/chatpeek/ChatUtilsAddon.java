package dev.wector11211.labymod.addons.chatpeek;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import dev.wector11211.labymod.addons.chatpeek.utils.Debug;
import net.labymod.addon.online.AddonInfoManager;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.KeyElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static dev.wector11211.labymod.addons.chatpeek.settings.ChatUtilsSettings.ENABLED;
import static dev.wector11211.labymod.addons.chatpeek.settings.ChatUtilsSettings.HOTKEY;

public class ChatUtilsAddon extends LabyModAddon {

    @Override
    public void onEnable() {
        AddonInfoManager.getInstance().getAddonInfoList().stream()
                .filter(info -> info.getAddonElement().getInstalledAddon().equals(this))
                .map(info -> LogManager.getLogger(info.getName()))
                .findAny().ifPresent(Debug::logger);
    }

    @Override
    public void loadConfig() {
        ENABLED.init(getConfig(), "enabled", true, JsonElement::getAsBoolean);
        HOTKEY.init(getConfig(), "hotkey", Keyboard.KEY_NONE, JsonElement::getAsInt);
    }

    @Override
    protected void fillSettings(List<SettingsElement> options) {
        BooleanElement enableElement = new BooleanElement(
                "Enabled",
                this,
                new ControlElement.IconData(Material.EMERALD),
                "enabled", ENABLED.get());

        KeyElement keyElement = new KeyElement("Chat Peek Hotkey",
                this,
                new ControlElement.IconData(Material.LEVER),
                "hotkey", HOTKEY.get());

        options.addAll(Lists.newArrayList(enableElement, keyElement));
    }

    public static boolean isPeek() {
        return ENABLED.get() && Keyboard.isKeyDown(HOTKEY.get());
    }
}