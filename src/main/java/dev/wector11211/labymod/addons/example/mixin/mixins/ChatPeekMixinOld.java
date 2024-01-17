package dev.wector11211.labymod.addons.example.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.wector11211.labymod.addons.example.settings.ChatPeekSettings.HOTKEY;

@Mixin(GuiNewChat.class)
public abstract class ChatPeekMixinOld {
    @Shadow
    @Final
    private Minecraft mc;

    @ModifyVariable(method = "drawChat", at = @At("HEAD"), argsOnly = true)
    private int patcher$modifyUpdateCounter(int updateCounter) {
        System.out.println(patcher$isPressed());
        return patcher$isPressed() ? 0 : updateCounter;
    }

    @ModifyVariable(method = "drawChat", at = @At("STORE"), index = 2)
    private int patcher$modifyChatLineLimit(int linesToDraw) {
        return patcher$isPressed() ? GuiNewChat.calculateChatboxHeight(mc.gameSettings.chatHeightFocused) / 9 : linesToDraw;
    }

    @Unique
    private boolean patcher$isPressed() {
        Integer key = HOTKEY.get();
        return key != null && Keyboard.isKeyDown(key);
    }

	@Inject(method = "drawChat", at = @At("HEAD"))
	private void onDraw(int updateCounter, CallbackInfo ci){
		System.out.println("\n\n INJECTED \n\n");
        System.out.println("Hotkey " + HOTKEY.get());
	}
}