function injectForEachInsn(method, opcode, callback) {
    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    var target = ASM.findFirstInstruction(method,
        opcode);

    while (target !== null) {
        var index = method.instructions.indexOf(target);
        var indexShift = callback(target, index);

        var newIndex = method.instructions.indexOf(target);
        if (newIndex !== -1)
            index = newIndex;
        else if (typeof indexShift === 'number')
            index += indexShift;

        target = ASM.findFirstInstructionAfter(method,
            opcode,
            index + 1);
    }

    return method;
}

function initializeCoreMod() {
    return {
        'health-bar': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.util.DamageSource',
                'methodName': 'func_76353_a', // getDisplayName
                'methodDesc': '(Lnet/minecraft/entity/projectile/AbstractArrowEntity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/DamageSource;'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                return injectForEachInsn(method, Opcodes.ARETURN, function (target) {
                    var newInstructions = new InsnList();

                    newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    newInstructions.add(ASM.buildMethodCall(
                        "xyz/kamefrede/betterpiercing/BetterPiercing",
                        "changeCrossbowDamageSource",
                        "(Lnet/minecraft/util/DamageSource;Lnet/minecraft/entity/projectile/AbstractArrowEntity;)Lnet/minecraft/util/DamageSource;",
                        ASM.MethodType.STATIC
                    ));
                    method.instructions.insertBefore(target, newInstructions);
                })
            }
        }
    }
}