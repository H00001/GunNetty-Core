package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */
public abstract class AbstractGunAmqpProtocl implements BaseAmqp, GunNetInputInterface, GunNetOutputInterface {
    public enum Lengths {
        /**
         * tyoe
         */
        TYPE(0x01), CHANNEL(0x02), LENGTH(4), CLASS(2), METOD(2);
        int len;

        Lengths(int len) {
            this.len = len;
        }

        public int getLen() {
            return len;
        }
    }

    public enum RealType {
        /**
         *
         */
        METHOD((byte) 0x01), HEART((byte) 0x08);
        byte val;

        RealType(byte val) {
            this.val = val;
        }
    }

    public enum ClassType {
        /**
         *
         */
        BASIC((byte) 0x00, (byte) 0x3c), CONNECTION((byte) 0x00, (byte) 0x0a);
        byte h;
        byte l;

        ClassType(byte h, byte l) {
            this.h = h;
            this.l = l;
        }

    }

    public enum MethodType {
        /**
         *
         */

        TUNEOK((byte) 0x02, (byte) 0x05), OPEN((byte) 0x00, (byte) 0x28), CONSUME((byte) 0x00, (byte) 0x14), START((byte) 0x00, (byte) 0x0a);
        byte h;
        byte l;

        MethodType(byte h, byte l) {
            this.h = h;
            this.l = l;
        }
    }

    public void setClassType(ClassType type) {
        classz[0] = type.h;
        classz[1] = type.l;
    }

    public void setMethodType(MethodType type) {
        method[0] = type.h;
        method[1] = type.l;
    }

    RealType type;
    byte[] channel = new byte[2];
    byte[] length = new byte[4];
    byte[] classz = new byte[2];
    byte[] method = new byte[2];
    byte endflag = (byte) 0xce;

}
