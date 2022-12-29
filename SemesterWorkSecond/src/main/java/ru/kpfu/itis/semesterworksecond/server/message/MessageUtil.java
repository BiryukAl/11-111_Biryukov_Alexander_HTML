package ru.kpfu.itis.semesterworksecond.server.message;

import java.io.*;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.SerializationUtils;


/**
 * @version demo
 * @author Vitaly Chekushkin
 */
public class MessageUtil {
    private static final byte[] VERSION = new byte[]{0x0, 0x1};

    public static <T extends Serializable> Message<T> readMessage(InputStream is)
            throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(is.readNBytes(2));
        if (byteBuffer.get() != VERSION[0] || byteBuffer.get() != VERSION[1]) {
            throw new IllegalArgumentException("Message version should be " +
                    VERSION[0] + "." + VERSION[1] + ".");
        }

        byteBuffer = ByteBuffer.wrap(is.readNBytes(1));
        TypeMessage type = TypeMessage.values()[byteBuffer.get()];

        byteBuffer = ByteBuffer.wrap(is.readNBytes(4));
        int length = byteBuffer.getInt();

        byteBuffer = ByteBuffer.wrap(is.readNBytes(length));
        T value = SerializationUtils.deserialize(
                new ByteArrayInputStream(byteBuffer.array()));

        return new Message<>(value, type);
    }

    public static <T extends Serializable> Message<T> createMessage(TypeMessage type, T value) {
        return new Message<>(value, type);
    }

    public static <T extends Serializable> void sendMessage(Message<T> message, OutputStream os)
            throws IOException {
        os.write(VERSION);
        os.write((byte) message.getType().ordinal());

        byte[] value = SerializationUtils.serialize(message.getValue());
        os.write(convertIntToBytes(value.length));
        os.write(value);

        os.flush();
    }

    private static byte[] convertIntToBytes(int number) {
        byte[] bytes = new byte[4];

        for (int i = 4; i > 0; i--) {
            bytes[i - 1] = (byte) (number % 256);
            number /= 256;
        }
        return bytes;
    }

}
