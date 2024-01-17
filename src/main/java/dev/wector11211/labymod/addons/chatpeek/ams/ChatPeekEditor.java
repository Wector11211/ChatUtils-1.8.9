package dev.wector11211.labymod.addons.chatpeek.ams;

import dev.wector11211.labymod.addons.chatpeek.ChatPeekAddon;
import net.labymod.core.asm.global.ClassEditor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ChatPeekEditor extends ClassEditor {
    public static final String OWNER = ChatPeekEditor.class.getName().replace(".", "/");
    public ChatPeekEditor() {
        super(ClassEditorType.CLASS_VISITOR);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor originalVisitor = super.visitMethod(access, name, desc, signature, exceptions);

        MethodVisitor updateCounterVisitor = new MethodVisitor(api, originalVisitor) {
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                super.visitMethodInsn(opcode, owner, name, desc, itf);

                if ("net/labymod/ingamechat/renderer/ChatRenderer".equals(owner) && "getLineCount".equals(name)) {
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, OWNER, "getLineCountInjector", "(I)I", false);
                }

                if ("net/minecraft/client/gui/GuiIngame".equals(owner) && "getUpdateCounter".equals(name)
                    || "avo".equals(owner) && "e".equals(name)) {
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, OWNER, "getUpdateCounterInjector", "(I)I", false);
                }
            }
        };

        return "renderChat".equals(name) ? updateCounterVisitor : originalVisitor;
    }

    public static int getUpdateCounterInjector(int getUpdateCounter) {
        return ChatPeekAddon.isPeek() ? 0 : getUpdateCounter;
    }

    public static int getLineCountInjector(int getLineCount) {
        return ChatPeekAddon.isPeek() ? GuiNewChat.calculateChatboxHeight(Minecraft.getMinecraft().gameSettings.chatHeightFocused) / 9 : getLineCount;
    }
}
