package dev.wector11211.labymod.addons.chatpeek.ams;

import dev.wector11211.labymod.addons.chatpeek.utils.Debug;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ChatRendererTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
            if ("net.labymod.ingamechat.renderer.ChatRenderer".equals(name)) {
                Debug.logger().info("Transforming ChatRenderer class (" + name + ")");


                ClassReader reader = new ClassReader(basicClass);
                ClassWriter writer = new ClassWriter(reader, 3);

                ClassVisitor editor = writer;
                editor = new ChatPeekEditor(editor);
                editor = new PreserveScrollEditor(editor, reader.getClassName());

                reader.accept(editor, 0);

                return writer.toByteArray();
            }
        return basicClass;
    }
}
