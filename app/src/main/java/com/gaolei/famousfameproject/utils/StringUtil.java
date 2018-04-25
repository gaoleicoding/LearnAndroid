package com.gaolei.famousfameproject.utils;


import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.security.MessageDigest;

public class StringUtil {


    /**
     * count number position in string
     *
     * @param strInput the string to count number position
     * @return int[] int[0]:start number index;int[1]:end number index. -1 if not contain number
     */
    public static int[] countNumberPosition(String strInput) {
        int[] numPosition = {-1, -1};
        if (StringUtil.isNullOrEmpty(strInput)) {
            return numPosition;
        }

        for (int i = 0; i < strInput.length(); i++) {
            if (strInput.charAt(i) >= 48 && strInput.charAt(i) <= 57) {
                if (numPosition[0] > i || numPosition[0] == -1) {
                    numPosition[0] = i;
                }

                if (numPosition[1] < i) {
                    numPosition[1] = i;
                }
            }
        }

        return numPosition;
    }

    /**
     * Check if inputString is null or empty.
     */
    public static boolean isNullOrEmpty(String inputString) {
        if (null == inputString) {
            return true;
        } else if (inputString.trim().equals("")) {
            return true;
        }

        return false;
    }

    /**
     * Check if all string in strings is null or empty.
     */
    public static boolean isAllNullOrEmpty(String... strings) {
        if (strings == null)
            return true;
        for (String string : strings) {
            if (!isNullOrEmpty(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get character number of string, a non-ascii character will be count 2 size.
     */
    public static int getCharacterNum(final String content) {
        if (null == content || "".equals(content)) {
            return 0;
        } else {
            return (content.length() + getChineseNum(content));
        }
    }

    /**
     * Get non-ascii character number of string.
     */
    public static int getChineseNum(String s) {
        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char) (byte) myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }

    /**
     * @return empty if inputString is null; otherwise return inputString self.
     */
    public static String getRealOrEmpty(String inputString) {
        return isNullOrEmpty(inputString) ? "" : inputString;
    }

    public static String getMaxLengthString(String string, int maxLenght) {
        if (StringUtil.isNullOrEmpty(string) || string.length() <= maxLenght) {
            return string;
        }
        return string.substring(0, maxLenght);
    }

    /**
     * 改变部分文字的颜色
     *
     * @param sourceString 源字符串
     * @param color        改变的颜色
     * @param start        起始字符位置
     * @param end          结束字符位置
     * @return 字符串
     */
    public static Spannable changStringColor(String sourceString, int color, int start, int end) {
        Spannable spannable = new SpannableString(sourceString);
        spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannable;
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static String getSID(String lg) {
        String s = lg;
        s = s.substring(s.length() - 8);
        String a = "";
        String b = "";
        int y;
        for (int i = 0; i < s.length(); i++) {
            y = (int) Math.floor(Math.random() * 10);
            b += y;
            a += s.charAt(i) << y;
        }
        return b + a;
    }


    /**
     * 判断字符传中是否只包含中文
     *
     * @param text 字符串
     * @return true:只包含中文 false:包含其他字符
     */
    public static boolean isOnlyChinese(String text) {
        if (StringUtil.isNullOrEmpty(text)) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            char code = text.charAt(i);
            if (code < 0x4e00 || code > 0x9fa5) { //超过中文汉字区域
                return false;
            }
        }
        return true;
    }

    /**
     * md5 加密
     *
     * @param str 要加密的字符串
     * @return
     */
    public static String md5Encode(String str) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] bytes = md5.digest();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(bytes[i] & 0xff);
                if (s.length() == 1) {
                    buf.append("0");
                }
                buf.append(s);
            }

        } catch (Exception ex) {
        }
        return buf.toString();
    }
}
