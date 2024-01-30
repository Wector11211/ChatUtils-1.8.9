package dev.wector11211.labymod.addons.chatpeek;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import dev.wector11211.labymod.addons.chatpeek.settings.SettingsRegistrar;
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

import static dev.wector11211.labymod.addons.chatpeek.settings.ChatUtilsSettings.*;

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
        PRESERVE_SCROLL.init(getConfig(), "preserve-scroll", false, JsonElement::getAsBoolean);
        PEEK_HOTKEY.init(getConfig(), "peek-hotkey", Keyboard.KEY_NONE, JsonElement::getAsInt);
    }

    @Override
    protected void fillSettings(List<SettingsElement> options) {
        SettingsRegistrar registrar = new SettingsRegistrar(this);

        options.addAll(Lists.newArrayList(
                registrar.register(ENABLED, "Enabled", Material.EMERALD, BooleanElement::new),
                registrar.register(PEEK_HOTKEY, "Chat Peek Hotkey", Material.LEVER, KeyElement::new)
        ));
    }

    public static boolean isPeek() {
        return ENABLED.get() && Keyboard.isKeyDown(PEEK_HOTKEY.get());
    }

    public static boolean isScrollPreserved() {
        return ENABLED.get() && PRESERVE_SCROLL.get();
    }
}