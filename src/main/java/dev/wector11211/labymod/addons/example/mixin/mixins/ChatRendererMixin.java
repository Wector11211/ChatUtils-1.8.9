package dev.wector11211.labymod.addons.example.mixin.mixins;

import dev.wector11211.labymod.addons.example.ChatPeekAddon;
import net.labymod.ingamechat.renderer.ChatRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChatRenderer.class, remap = false)
public abstract class ChatRendererMixin {

//    @Inject(method = "renderChat", at = @At("HEAD"))
//    private void renderChatTop(CallbackInfo ci) {
//        System.out.println("Pressed " + ChatPeekAddon.isPeek());
//    }
//
//    @ModifyVariable(method = "renderChat", at = @At("HEAD"), argsOnly = true)
//    private int renderChat(int updateCounter) {
//        return ChatPeekAddon.isPeek() ? 0 : updateCounter;
//    }
//
//    @Redirect(method = "renderChat", at = @At(value = "INVOKE", target = "Lnet/labymod/ingamechat/renderer/ChatRenderer;isVisible()Z"))
//    private boolean isVisible(ChatRenderer instance) {
//        return ChatPeekAddon.isPeek() || instance.isVisible();
//    }

    @ModifyVariable(method = "renderChat", at = @At("STORE"), index = 26)
    private int updateCounterDifference(int updateCounterDifference) {
//        System.out.println("Test");
        return ChatPeekAddon.isPeek() ? 0 : updateCounterDifference;
    }

    @Redirect(method = "renderChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;getUpdateCounter()I"))
    private int getUpdateCounter(GuiIngame instance) {
//        System.out.println("getLineCount");
//        return ChatPeekAddon.isPeek() ? 0 : instance.getUpdateCounter();
        return instance.getUpdateCounter();
    }

    @Redirect(method = "renderChat", at = @At(value = "INVOKE", target = "Lnet/labymod/ingamechat/renderer/ChatRenderer;getLineCount()I"))
    private int getLineCount(ChatRenderer instance) {
//        System.out.println("getLineCount");
        return ChatPeekAddon.isPeek() ? GuiNewChat.calculateChatboxHeight(Minecraft.getMinecraft().gameSettings.chatHeightFocused) / 9 : instance.getLineCount();
    }
}
