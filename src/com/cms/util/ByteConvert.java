package com.cms.util;

/*
 * Created on 2004-5-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


final public class ByteConvert {
    final static String lineSeperate = "\r\n";
    final public static int DEFAULT_BUFFERLENGTH = 1024 * 3;
    final public static int DEFAULT_TABLELENGTH = 256;
    private static String[] convertTable;

    static {
        convertTable = new String[DEFAULT_TABLELENGTH];

        int i = 0;

        for (i = 0; i < 16; i++) {
            convertTable[i] = "0" + Integer.toHexString(i);
        }

        for (; i < 256; i++) {
            convertTable[i] = Integer.toHexString(i);
        }
    }

    /**
     *
     * Gordon Added
     *
     *
     */
    public static byte[] intToByte(int i) {
        byte[] abyte0 = new byte[4];
        abyte0[0] = (byte) (0xff & i);
        abyte0[1] = (byte) ((0xff00 & i) >> 8);
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);

        return abyte0;
    }

    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 0xFF;
        addr |= ((bytes[1] << 8) & 0xFF00);
        addr |= ((bytes[2] << 16) & 0xFF0000);
        addr |= ((bytes[3] << 24) & 0xFF000000);

        return addr;
    }

    /**
     * convert two byte to int, in fact return int for represent unsigned short.
     *
     * @param convertByte
     *            byte stream
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int2(byte[] convertByte) {
        return byte2int2(convertByte, 0, true);
    }

    /**
     * convert two byte to int, in fact return int for represent unsigned short.
     *
     * @param convertByte
     *            byte stream
     * @param bigIndian
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int2(byte[] convertByte, boolean bigIndian) {
        return byte2int2(convertByte, 0, bigIndian);
    }

    /**
     * convert two byte to int, in fact return int for represent unsigned short.
     *
     * @param convertByte
     *            byte stream
     * @param offset
     *            offset of byte stream to be converted
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int2(byte[] convertByte, int offset) {
        return byte2int2(convertByte, offset, true);
    }

    /**
     * convert two byte to int, in fact return int for represent unsigned short.
     *
     * @param convertByte
     *            byte stream
     * @param offset
     *            offset of byte stream to be converted
     * @param bigIndian
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int2(byte[] convertByte, int offset,
        boolean bigIndian) {
        int value = 0;
        int byte0;
        int byte1;

        byte0 = (convertByte[0 + offset] < 0) ? (convertByte[0 + offset] + 256)
                                              : convertByte[0 + offset];
        byte1 = (convertByte[1 + offset] < 0) ? (convertByte[1 + offset] + 256)
                                              : convertByte[1 + offset];

        if (!bigIndian) {
            value = (byte1 * 256) + byte0;
        } else {
            value = (byte0 * 256) + byte1;
        }

        return value;
    }

    /**
     * convert four byte to int.
     *
     * @param convertByte
     *            byte stream
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int4(byte[] convertByte) {
        return byte2int4(convertByte, 0, true);
    }

    /**
     * convert four byte to int.
     *
     * @param convertByte
     *            byte stream
     * @param bigIndian
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int4(byte[] convertByte, boolean bigIndian) {
        return byte2int4(convertByte, 0, bigIndian);
    }

    /**
     * convert four byte to int.
     *
     * @param convertByte
     *            byte stream
     * @param offset
     *            offset of byte stream to be converted
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int4(byte[] convertByte, int offset) {
        return byte2int4(convertByte, offset, true);
    }

    /**
     * convert four byte to int.
     *
     * @param convertByte
     *            byte stream
     * @param offset
     *            offset of byte stream to be converted
     * @param bigIndian
     * @return int
     * @exception exceptions
     *                No exceptions thrown
     */
    public static int byte2int4(byte[] convertByte, int offset,
        boolean bigIndian) {
        int value = 0;
        int byte0;
        int byte1;
        int byte2;
        int byte3;

        byte0 = (convertByte[0 + offset] < 0) ? (convertByte[0 + offset] + 256)
                                              : convertByte[0 + offset];
        byte1 = (convertByte[1 + offset] < 0) ? (convertByte[1 + offset] + 256)
                                              : convertByte[1 + offset];
        byte2 = (convertByte[2 + offset] < 0) ? (convertByte[2 + offset] + 256)
                                              : convertByte[2 + offset];
        byte3 = (convertByte[3 + offset] < 0) ? (convertByte[3 + offset] + 256)
                                              : convertByte[3 + offset];

        if (!bigIndian) {
            value = (byte3 << 24) + (byte2 << 16) + (byte1 << 8) + byte0;
        } else {
            value = (byte0 << 24) + (byte1 << 16) + (byte2 << 8) + byte3;
        }

        return value;
    }

    public static long byte2long4(byte[] convertByte, int offset,boolean bigIndian) {
        long value = 0;
        long byte0;
        long byte1;
        long byte2;
        long byte3;

        byte0 = (convertByte[0 + offset] < 0) ? (convertByte[0 + offset] + 256)
                                              : convertByte[0 + offset];
        byte1 = (convertByte[1 + offset] < 0) ? (convertByte[1 + offset] + 256)
                                              : convertByte[1 + offset];
        byte2 = (convertByte[2 + offset] < 0) ? (convertByte[2 + offset] + 256)
                                              : convertByte[2 + offset];
        byte3 = (convertByte[3 + offset] < 0) ? (convertByte[3 + offset] + 256)
                                              : convertByte[3 + offset];

        if (!bigIndian) {
            value = (byte3 << 24) + (byte2 << 16) + (byte1 << 8) + byte0;
        } else {
            value = (byte0 << 24) + (byte1 << 16) + (byte2 << 8) + byte3;
        }

        return value;
    }

    /**
     * convert short to two byte.
     *
     * @param value
     *            int value represent unsigned short
     * @return byte[]
     * @exception exceptions
     *                No exceptions thrown
     */
    public static byte[] int2byte2(int value) {
        return int2byte2(value, true);
    }

    /**
     * convert short to two byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param bigIndian
     * @return byte[]
     * @exception exceptions
     *                No exceptions thrown
     */
    public static byte[] int2byte2(int value, boolean bigIndian) {
        byte[] byteValue = new byte[2];

        if (!bigIndian) {
            byteValue[0] = (byte) (value & 0xff);
            byteValue[1] = (byte) ((value & 0xff00) >>> 8);
        } else {
            byteValue[1] = (byte) (value & 0xff);
            byteValue[0] = (byte) ((value & 0xff00) >>> 8);
        }

        return byteValue;
    }

    /**
     * convert short to two byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte2(int value, byte[] fillByte) {
        int2byte2(value, fillByte, 0, true);
    }

    /**
     * convert short to two byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @param bigIndian
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte2(int value, byte[] fillByte, boolean bigIndian) {
        int2byte2(value, fillByte, 0, bigIndian);
    }

    /**
     * convert short to two byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @param fillByte
     *            at the offset of byte stream to set
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte2(int value, byte[] fillByte, int offset) {
        int2byte2(value, fillByte, offset, true);
    }

    /**
     * convert short to two byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @param fillByte
     *            at the offset of byte stream to set
     * @param bigIndian
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte2(int value, byte[] fillByte, int offset,
        boolean bigIndian) {
        if (!bigIndian) {
            fillByte[0 + offset] = (byte) (value & 0xff);
            fillByte[1 + offset] = (byte) ((value & 0xff00) >>> 8);
        } else {
            fillByte[1 + offset] = (byte) (value & 0xff);
            fillByte[0 + offset] = (byte) ((value & 0xff00) >>> 8);
        }
    }

    /**
     * convert int to four byte.
     *
     * @param value
     *            int
     * @return byte[]
     * @exception exceptions
     *                No exceptions thrown
     */
    public static byte[] int2byte4(int value) {
        return int2byte4(value, true);
    }

    /**
     * convert int to four byte.
     *
     * @param value
     *            int
     * @param bigIndian
     * @return byte[]
     * @exception exceptions
     *                No exceptions thrown
     */
    public static byte[] int2byte4(int value, boolean bigIndian) {
        byte[] byteValue = new byte[4];

        if (!bigIndian) {
            byteValue[0] = (byte) (value & 0xff);
            byteValue[1] = (byte) ((value & 0xff00) >>> 8);
            byteValue[2] = (byte) ((value & 0xff0000) >>> 16);
            byteValue[3] = (byte) ((value & 0xff000000) >>> 24);
        } else {
            byteValue[3] = (byte) (value & 0xff);
            byteValue[2] = (byte) ((value & 0xff00) >>> 8);
            byteValue[1] = (byte) ((value & 0xff0000) >>> 16);
            byteValue[0] = (byte) ((value & 0xff000000) >>> 24);
        }

        return byteValue;
    }

    /**
     * convertint to four byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte4(int value, byte[] fillByte) {
        int2byte4(value, fillByte, 0, true);
    }

    /**
     * convertint to four byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @param bigIndian
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte4(int value, byte[] fillByte, boolean bigIndian) {
        int2byte4(value, fillByte, 0, bigIndian);
    }

    /**
     * convertint to four byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @param fillByte
     *            at the offset of byte stream to set
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte4(int value, byte[] fillByte, int offset) {
        int2byte4(value, fillByte, offset, true);
    }

    /**
     * convertint to four byte.
     *
     * @param value
     *            int value represent unsigned short
     * @param fillByte
     *            byte stream to set
     * @param fillByte
     *            at the offset of byte stream to set
     * @param bigIndian
     * @return void
     * @exception exceptions
     *                No exceptions thrown
     */
    public static void int2byte4(int value, byte[] fillByte, int offset,
        boolean bigIndian) {
        if (!bigIndian) {
            fillByte[0 + offset] = (byte) (value & 0xff);
            fillByte[1 + offset] = (byte) ((value & 0xff00) >>> 8);
            fillByte[2 + offset] = (byte) ((value & 0xff0000) >>> 16);
            fillByte[3 + offset] = (byte) ((value & 0xff000000) >>> 24);
        } else {
            fillByte[3 + offset] = (byte) (value & 0xff);
            fillByte[2 + offset] = (byte) ((value & 0xff00) >>> 8);
            fillByte[1 + offset] = (byte) ((value & 0xff0000) >>> 16);
            fillByte[0 + offset] = (byte) ((value & 0xff000000) >>> 24);
        }
    }

    /**
     * change a byte array to a HEX string
     *
     * @param info
     *            the title of the HEX string
     * @param buf
     *            the byte array to be changed
     * @return the HEX string
     */
    public static String buf2String(String info, byte[] buf) {
        return buf2String(info, buf, 0, buf.length, false);
    }

    /**
     * change a byte array to a HEX string
     *
     * @param info
     *            the title of the HEX string
     * @param buf
     *            the byte array to be changed
     * @param oneLine16
     *            if change a line of every 16 bytes
     * @return the HEX string
     */
    public static String buf2String(String info, byte[] buf, boolean oneLine16) {
        return buf2String(info, buf, 0, buf.length, oneLine16);
    }

    /**
     * change a byte array to a HEX string
     *
     * @param info
     *            the title of the HEX string
     * @param buf
     *            the byte array to be changed
     * @param offset
     *            the begin index of the byte array to be changed
     * @param length
     *            the length to be changed
     * @return the HEX string
     */
    public static String buf2String(String info, byte[] buf, int offset,
        int length) {
        return buf2String(info, buf, offset, length, true);
    }

    /**
     * change a byte array to a HEX string
     *
     * @param info
     *            the title of the HEX string
     * @param buf
     *            the byte array to be changed
     * @param offset
     *            the begin index of the byte array to be changed
     * @param length
     *            the length to be changed
     * @param oneLine16
     *            if change a line of every 16 bytes
     * @return the HEX string
     */
    public static String buf2String(String info, byte[] buf, int offset,
        int length, boolean oneLine16) {
        int i;
        int index;
        StringBuffer sBuf = new StringBuffer();
        sBuf.append(info);

        for (i = 0; i < length; i++) {
            if ((i % 16) == 0) {
                if (oneLine16) {
                    sBuf.append(lineSeperate);
                }
            } else if ((i % 8) == 0) {
                if (oneLine16) {
                    sBuf.append("   ");
                }
            }

            index = (buf[i + offset] < 0)
                ? (buf[i + offset] + DEFAULT_TABLELENGTH) : buf[i + offset];
            sBuf.append(convertTable[index]);
            sBuf.append(" ");
        }

        return sBuf.toString();
    }
}
