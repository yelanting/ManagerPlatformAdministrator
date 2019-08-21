/**
 * @author : 孙留平
 * @since : 2018年12月1日 下午2:46:50
 * @see:
 */
package com.administrator.platform.core.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.exception.base.BusinessValidationException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author : Administrator
 * @since : 二〇一八年十二月一日 14:48:42
 * @see :
 */
public class ChinesePinYin {
    private ChinesePinYin() {

    }

    private static Logger logger = LoggerFactory.getLogger(ChinesePinYin.class);

    public static final String FULL_PINYIN = "fullPinyin";
    public static final String SIMPLE_PINYIN = "simplePinyin";

    /**
     * 获取全拼和简写拼音，长度过长的时候会截取
     * 
     * @see :
     * @param :
     * @return : Map<String,String>
     * @param chinese
     * @return
     */
    public static Map<String, String> changeChinese2Pinyin(String chinese) {
        Map<String, String> pinyin = new HashMap<>(16);

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuilder fullPinyin = new StringBuilder();
        StringBuilder simplePinyin = new StringBuilder();
        char[] chineseChar = chinese.toCharArray();
        for (int i = 0; i < chineseChar.length; i++) {
            String[] str = null;
            try {
                str = PinyinHelper.toHanyuPinyinStringArray(chineseChar[i],
                        format);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                logger.error("异常信息", e);
            }
            if (str != null) {
                fullPinyin = fullPinyin.append(str[0]);
                simplePinyin = simplePinyin.append(str[0].charAt(0));
            }
            if (str == null) {
                String regex = "^[0-9]*[a-zA-Z]*[;.、]*$";
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(String.valueOf(chineseChar[i]));
                if (m.find()) {
                    fullPinyin = fullPinyin.append(chineseChar[i]);
                    simplePinyin = simplePinyin.append(chineseChar[i]);
                }
            }
        }
        pinyin.put(FULL_PINYIN,
                fullPinyin.length() > 20 ? fullPinyin.substring(0, 20)
                        : fullPinyin.toString());
        pinyin.put(SIMPLE_PINYIN,
                simplePinyin.length() > 10 ? simplePinyin.substring(0, 10)
                        : simplePinyin.toString());

        return pinyin;
    }

    /**
     * 获取全拼和简写拼音
     * 
     * @see :
     * @param :
     * @return : Map<String,String>
     * @param chinese
     * @return
     */
    public static Map<String, String> changeChineseNoCutPinyin(String chinese) {
        Map<String, String> pinyin = new HashMap<>(16);

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuilder fullPinyin = new StringBuilder();
        StringBuilder simplePinyin = new StringBuilder();
        char[] chineseChar = chinese.toCharArray();
        for (int i = 0; i < chineseChar.length; i++) {
            String[] str = null;
            try {
                str = PinyinHelper.toHanyuPinyinStringArray(chineseChar[i],
                        format);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                logger.error("异常信息", e);
            }
            if (str != null) {
                fullPinyin = fullPinyin.append(str[0]);
                simplePinyin = simplePinyin.append(str[0].charAt(0));
            }
            if (str == null) {
                String regex = "^[0-9]*[a-zA-Z]*+$";
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(String.valueOf(chineseChar[i]));
                if (m.find()) {
                    fullPinyin = fullPinyin.append(chineseChar[i]);
                    simplePinyin = simplePinyin.append(chineseChar[i]);
                }
            }
        }
        pinyin.put(FULL_PINYIN, fullPinyin.toString());
        pinyin.put(SIMPLE_PINYIN, simplePinyin.toString());

        return pinyin;
    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     * 
     * @param inputString
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        StringBuilder output = new StringBuilder();

        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i])
                        .matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper
                            .toHanyuPinyinStringArray(input[i], format);
                    output.append(temp[0]);
                } else {
                    output.append(java.lang.Character.toString(input[i]));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("获取拼音异常:{}", e.getMessage());
            throw new BusinessValidationException("获取拼音异常");
        }
        return output.toString();
    }
}
