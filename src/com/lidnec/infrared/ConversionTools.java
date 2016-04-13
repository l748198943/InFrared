package com.lidnec.infrared;


/**
 * Created by lindec on 2016/3/7.
 */
public class ConversionTools {

    public static ConversionTools Tool() {
        return new ConversionTools();
    }


    /**
     * Btye 转换成 Str
     *
     * @param b    byte
     * @param flag true ：表示输出如0x03->03 ;false:表示输出0x03->3
     * @return Str
     */
    public String byte2Str(byte b, boolean flag) {
        String stmp = Integer.toHexString(b & 0xFF);
        if (!flag) {
            return stmp;
        } else {
            StringBuilder sb = new StringBuilder("");
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            stmp = sb.toString();
            return stmp.toUpperCase().trim();
        }


    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     * @return String 每个Byte值之间空格分隔  {0x01,0x02} -> "01 02"
     */
    public String bytes2HexStr(byte[] b, boolean flag) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            if (flag) sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }


    /**
     * bytes字符串转换为Byte值
     *
     * @param src  Byte字符串，每个Byte之间没有分隔符发
     * @param flag true：允许超过7f（127） false：不允许允许超过7f（127），如：80就会有错误
     * @return byte[]    "020a7f" -> {0x02,0x0a,0x7f}
     */
    public byte[] hexStr2Bytes(String src, boolean flag) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        if (flag) {
            for (int i = 0; i < l; i++) {
                int temp = Integer.decode("0x" + src.substring(i * 2, i * 2 + 1) + src.substring(i * 2 + 1, i * 2 + 2));
                ret[i] = (byte) temp;
            }
        } else {
            for (int i = 0; i < l; i++) {
                ret[i] = Byte.decode("0x" + src.substring(i * 2, i * 2 + 1) + src.substring(i * 2 + 1, i * 2 + 2));
            }
        }

        return ret;
    }

    /**
     * String的字符串转换成unicode的String
     *
     * @param strText 全角字符串
     * @return String 每个unicode之间无分隔符 如："我是天才" -> "\u6211\u662f\u5929\u624d"
     * @throws Exception
     */
    public String strToUnicode(String strText)
            throws Exception {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u" + strHex);
            else // 低位在前面补00
                str.append("\\u00" + strHex);
        }
        return str.toString();
    }

    /**
     * unicode的String转换成String的字符串
     *
     * @param hex 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串 如："\\u6211\\u662f\\u5929\\u624d" -> "我是天才"
     */
    public String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    /**
     * 字符串转换成十六进制(ASCII码值)字符串
     *
     * @param str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: "1a=" -> "31 61 3D"
     */
    public String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     *
     * @param hexStr Byte字符串(Byte之间无分隔符 如:[31613D])
     * @return String 对应的字符串  "31613D" -> "1a="
     */
    public String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 数据转换成字符串
     *
     * @param array 数值
     * @return String 大数字符串 每个Int之间空格分隔，如：{0x0001,0x0002,0x0100} -> "0001 0002 0100"
     */
    public String array2BEhexStr(int[] array, boolean flag) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < array.length; n++) {
            stmp = Integer.toHexString(array[n] & 0xFFFF);
            if (stmp.length() == 0) {
                sb.append("0000");
            } else if (stmp.length() == 1) {
                sb.append("000" + stmp);
            } else if (stmp.length() == 2) {
                sb.append("00" + stmp);
            } else if (stmp.length() == 3) {
                sb.append("0" + stmp);
            } else sb.append(stmp);
            if (flag) sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * 数据转换成字符串
     *
     * @param array 数值
     * @return 小数字符串 每个Int之间空格分隔，如：{0x0001,0x0002,0x0100} -> "0100 0200 0001"
     */
    public String array2LEhexStr(int[] array, boolean flag) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < array.length; n++) {
            for (int i = 0; i < 2; i++) {
                if (i == 0) stmp = Integer.toHexString(array[n] & 0xFF);
                if (i == 1) stmp = Integer.toHexString((array[n] >> 8) & 0xFF);
                if (stmp.length() == 0) {
                    sb.append("00");
                } else if (stmp.length() == 1) {
                    sb.append("0" + stmp);
                } else {
                    sb.append(stmp);
                }
            }
            if (flag) sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * 十六进制转换数值
     *
     * @param hexStr 大数int字符串(如："0102"对应 ｛0x0102｝)
     * @return int[] 如："000100020100" -> {0x0001,0x0002,0x0100}
     */
    public int[] beHexStr2Array(String hexStr) {
        int length = hexStr.length() / 4;
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = Integer.decode("0x" + hexStr.substring(i * 4, i * 4 + 1) + hexStr.substring(i * 4 + 1, i * 4 + 2) +
                    hexStr.substring(i * 4 + 2, i * 4 + 3) + hexStr.substring(i * 4 + 3, i * 4 + 4));
        }
        return array;
    }

    /**
     * 十六进制转换数值
     *
     * @param hexStr 小数int字符串(如："0102"对应 ｛0x0201｝)
     * @return int[] 如："000100020100" -> {0x0100,0x0200,0x0001}
     */
    public int[] leHexStr2Array(String hexStr) {

        int length = hexStr.length() / 4;
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = Integer.decode("0x" + hexStr.substring(i * 4 + 2, i * 4 + 3) + hexStr.substring(i * 4 + 3, i * 4 + 4)
                    + hexStr.substring(i * 4, i * 4 + 1) + hexStr.substring(i * 4 + 1, i * 4 + 2));
        }
        return array;
    }


}
