package dev.wector11211.labymod.addons.chatpeek.settings;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class SettingsRegistrar {
    private final LabyModAddon addon;

    public SettingsRegistrar(LabyModAddon addon) {
        this.addon = addon;
    }

    public <T> ControlElement register(ChatUtilsSettings<T> setting, String displayName, ControlElement.IconData iconData, Constructor<T> element) {
        return element.construct(displayName, addon, iconData, setting.attribute(), setting.get());
    }

    public <T> ControlElement register(ChatUtilsSettings<T> setting, String displayName, Material iconMaterial, Constructor<T> element) {
        return element.construct(displayName, addon, new ControlElement.IconData(iconMaterial), setting.attribute(), setting.get());
    }

    public interface Constructor<T> {
        ControlElement construct(String displayName, final LabyModAddon addon, ControlElement.IconData iconData, final String attributeName, T defaultValue);
    }
}
