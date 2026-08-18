package util;

import java.io.ByteArrayOutputStream;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;

public class Gsm7Encoder {

    private static final Map<Character, Integer> BASIC_TABLE = new HashMap<>();
    private static final Map<Character, Integer> EXTENSION_TABLE = new HashMap<>();

    private static final int ESC = 0x1B;

    static {

        // GSM 03.38 default alphabet
        String basic =
                "@£$¥èéùìòÇ\nØø\rÅå" +
                        "Δ_ΦΓΛΩΠΨΣΘΞ" +
                        "\u001BÆæßÉ" +
                        " !" +
                        "\"#¤%&'()*+,-./" +
                        "0123456789:;<=>?" +
                        "¡ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§¿" +
                        "abcdefghijklmnopqrstuvwxyzäöñüà";

        for (int i = 0; i < basic.length(); i++) {
            BASIC_TABLE.put(basic.charAt(i), i);
        }

        // GSM 03.38 extension table
        EXTENSION_TABLE.put('^', 0x14);
        EXTENSION_TABLE.put('{', 0x28);
        EXTENSION_TABLE.put('}', 0x29);
        EXTENSION_TABLE.put('\\', 0x2F);
        EXTENSION_TABLE.put('[', 0x3C);
        EXTENSION_TABLE.put('~', 0x3D);
        EXTENSION_TABLE.put(']', 0x3E);
        EXTENSION_TABLE.put('|', 0x40);
        EXTENSION_TABLE.put('€', 0x65);
    }

    public byte[] encode(String message) {

        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        ByteArrayOutputStream septets = new ByteArrayOutputStream();

        for (char c : message.toCharArray()) {

            // Check GSM basic alphabet
            Integer value = BASIC_TABLE.get(c);

            if (value != null) {
                septets.write(value);
                continue;
            }

            // Check GSM extension alphabet
            Integer extensionValue = EXTENSION_TABLE.get(c);

            if (extensionValue != null) {
                septets.write(ESC);
                septets.write(extensionValue);
                continue;
            }

            // Character is not supported by GSM 7-bit
            throw new IllegalArgumentException(
                    "Character cannot be encoded in GSM 7-bit: " + c
            );
        }

        return packSeptets(septets.toByteArray());
    }

    private byte[] packSeptets(byte[] septets) {

        int septetCount = septets.length;

        int byteCount = (septetCount * 7 + 7) / 8;

        byte[] packed = new byte[byteCount];

        for (int i = 0; i < septetCount; i++) {

            // Keep only the 7 meaningful bits
            int septet = septets[i] & 0x7F;

            // Position in the continuous 7-bit stream
            int bitPosition = i * 7;

            // Which output byte?
            int bytePosition = bitPosition / 8;

            // Which bit inside that byte?
            int bitOffset = bitPosition % 8;

            // Put the septet into the current byte
            packed[bytePosition] |=
                    (byte) (septet << bitOffset);

            // If the septet crosses the byte boundary,
            // put the remaining bits into the next byte.
            if (bitOffset > 1 && bytePosition + 1 < packed.length) {

                packed[bytePosition + 1] |=
                        (byte) (septet >> (8 - bitOffset));
            }
        }

        return packed;
    }

    public byte[] encodeSeptets(String message) {

        if (message == null) {
            throw new IllegalArgumentException(
                    "Message cannot be null"
            );
        }

        ByteArrayOutputStream septets =
                new ByteArrayOutputStream();

        for (char c : message.toCharArray()) {

            Integer value = BASIC_TABLE.get(c);

            if (value != null) {
                septets.write(value);
                continue;
            }

            Integer extensionValue =
                    EXTENSION_TABLE.get(c);

            if (extensionValue != null) {

                // ESC septet
                septets.write(0x1B);

                // Extension septet
                septets.write(extensionValue);

                continue;
            }

            throw new IllegalArgumentException(
                    "Character cannot be encoded in GSM 7-bit: "
                            + c
            );
        }

        return septets.toByteArray();
    }

    public int septetCount(char c){
        if (BASIC_TABLE.containsKey(c)){
            return 1;
        }
        if(EXTENSION_TABLE.containsKey(c)){
            return  2;
        }
        throw new IllegalArgumentException( "Character cannot be encoded in GSM 7-bit: " + c);
    }
}