package dev.wector11211.labymod.addons.chatpeek.settings;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.function.Function;

import static net.labymod.utils.manager.ConfigManager.GSON;

public class ChatUtilsSettings<T> {
    public static final ChatUtilsSettings<Boolean> ENABLED = new ChatUtilsSettings<>();
    public static final ChatUtilsSettings<Integer> HOTKEY = new ChatUtilsSettings<>();

    private String attribute;
    private T value;

    public void init(JsonObject config, String attribute, T defaultValue, Function<JsonElement, T> getter) {
        this.attribute = attribute;
        if (!config.has(attribute)) {
            this.value = defaultValue;
            config.add(attribute, GSON.toJsonTree(defaultValue));
        } else {
            this.value = getter.apply(config.get(attribute));
        }
    }

    public String attribute() {
        return attribute;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public static List<ChatUtilsSettings<?>> values() {
        return Lists.newArrayList(ENABLED, HOTKEY);
    }
}
