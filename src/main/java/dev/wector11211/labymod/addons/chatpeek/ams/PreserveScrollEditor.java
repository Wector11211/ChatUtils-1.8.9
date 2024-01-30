package dev.wector11211.labymod.addons.chatpeek.ams;

import dev.wector11211.labymod.addons.chatpeek.ChatUtilsAddon;
import net.labymod.core.asm.global.ClassEditor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PreserveScrollEditor extends ClassEditor {
//    public static final String CLASS = ChatRenderer.class.getName().replace(".", "/");
    public static final String OWNER = PreserveScrollEditor.class.getName().replace(".", "/");
    private final String className;

    public PreserveScrollEditor(ClassVisitor classVisitor, String className) {
        super(ClassEditorType.CLASS_VISITOR);
        this.className = className;
        this.cv = classVisitor;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor originalVisitor = super.visitMethod(access, name, desc, signature, exceptions);

        MethodVisitor changeScrollVisitor = new MethodVisitor(api, originalVisitor) {
            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.RETURN) {
                    super.visitVarInsn(Opcodes.ALOAD, 0);
                    super.visitVarInsn(Opcodes.ALOAD, 0);
                    super.visitFieldInsn(Opcodes.GETFIELD, className, "scrollPos", "I");
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, OWNER, "updateScrollPos", "(I)I", false);
                    super.visitFieldInsn(Opcodes.PUTFIELD, className, "scrollPos", "I");
                }
                super.visitInsn(opcode);
            }
        };

        return "addChatLine".equals(name) ? changeScrollVisitor : originalVisitor;
    }

    public static int updateScrollPos(int scrollPos) {
        if (ChatUtilsAddon.isScrollPreserved()) {
            if (scrollPos > 0) scrollPos++;
        }
        return scrollPos;
    }
}
