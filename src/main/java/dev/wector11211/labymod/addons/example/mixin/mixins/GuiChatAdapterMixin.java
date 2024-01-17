package dev.wector11211.labymod.addons.example.mixin.mixins;

import dev.wector11211.labymod.addons.example.ChatPeekAddon;
import net.labymod.core_implementation.mc18.gui.GuiChatAdapter;
import net.labymod.ingamechat.renderer.ChatRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.wector11211.labymod.addons.example.settings.ChatPeekSettings.HOTKEY;

@Mixin(value = GuiChatAdapter.class, remap = false)
public abstract class GuiChatAdapterMixin {

    @ModifyVariable(method = "drawChat", at = @At("HEAD"), argsOnly = true)
    private int drawChat(int updateCounter) {
        return ChatPeekAddon.isPeek() ? 0 : updateCounter;
    }
}
