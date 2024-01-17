package dev.wector11211.labymod.addons.chatpeek.ams;

import net.labymod.core.asm.global.ClassEditor;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class ChatRendererTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
            if ("net.labymod.ingamechat.renderer.ChatRenderer".equals(name)) {
                System.out.println("[ChatPeekAddon] Transforming ChatRenderer class (" + name + ")");

                ClassEditor editor = new ChatPeekEditor();

                ClassReader reader = new ClassReader(basicClass);
                ClassWriter writer = new ClassWriter(reader, 3);
                editor.accept(name, writer);
                reader.accept(editor, 0);

                return writer.toByteArray();
            }
        return basicClass;
    }
}
