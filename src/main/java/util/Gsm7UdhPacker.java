package util;

import pduObjects.SmsSegment;

public class Gsm7UdhPacker {

    private final UdhBuilder udhBuilder;
    private final Gsm7Encoder gsm7Encoder;

    public Gsm7UdhPacker() {
        this.udhBuilder = new UdhBuilder();
        this.gsm7Encoder = new Gsm7Encoder();
    }

    public byte[] buildUdhShortMsg(SmsSegment smsSegment) {

        byte[] udh = udhBuilder.build(smsSegment);

        System.out.println("UDH Data for each segment:");
        printHex(udh);

        byte[] septets =
                gsm7Encoder.encodeSeptets(
                        smsSegment.getMessage()
                );

        // UDH = normal 8-bit bytes
        int udhBits = udh.length * 8;

        // One bit is required to align GSM-7 after the UDH.
        int fillBits = 1;

        int messageBits =
                septets.length * 7;

        int totalBits =
                udhBits + fillBits + messageBits;

        int totalBytes =
                (totalBits + 7) / 8;

        byte[] result =
                new byte[totalBytes];

        // -----------------------------------------
        // 1. Copy UDH
        // -----------------------------------------

        System.arraycopy(
                udh,
                0,
                result,
                0,
                udh.length
        );

        // -----------------------------------------
        // 2. Start GSM-7 after UDH + fill bit
        // -----------------------------------------

        int startBit =
                udhBits + fillBits;

        // -----------------------------------------
        // 3. Pack GSM-7 septets
        // -----------------------------------------

        for (int i = 0; i < septets.length; i++) {

            int septet =
                    septets[i] & 0x7F;

            int bitPosition =
                    startBit + (i * 7);

            for (int bit = 0; bit < 7; bit++) {

                if ((septet & (1 << bit)) != 0) {

                    int destinationBit =
                            bitPosition + bit;

                    int bytePosition =
                            destinationBit / 8;

                    int bitOffset =
                            destinationBit % 8;

                    result[bytePosition] |=
                            (byte) (1 << bitOffset);
                }
            }
        }

        return result;
    }

    private static void printHex(byte[] bytes) {

        for (byte b : bytes) {
            System.out.printf(
                    "%02X ",
                    b & 0xFF
            );
        }

        System.out.println();
    }
}