package com.aiccj.abtest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public class Password {
    /**
     * 随意定一个私钥（长度必须为24位）
     */
    public static final String USER_NO_COLUMN = "userNo";
    public static final String API_KEY_COLUMN = "apiKey";

//    public static final String SECRET_KEY = "1234567890ABCDEFGHIJKLMN";
    public static final String SECRET_KEY = "ABCDEFGHIJKLMN1234567890";

    public static final String USER_SECRET_KEY = "ABCDEFGHIJKLMN0987654321";

    public static final String IGNORE_VALIDATE_KEY = "5twt5vyo0adc6cezkam2r6sd0iv5tqzq";

    public static final String MERCHANT_SINGLE_SECRET_KEY = "7wxlxep35tu0t9fkj9pe7grx";

    public static final String CHANNEL_SINGLE_SECRET_KEY = "cs75qqieldfcl20kg2cxesjb";

    public static String encrypt(String inStr) {
        return encrypt(inStr, SECRET_KEY);
    }

    public static Boolean checkPassword(String password, String encryptedPassword){
        return encrypt(password).equals(encryptedPassword);
    }

    /**
     * 加密
     *
     * @param inStr     需要加密的内容
     * @param secretKey 密钥
     * @return 加密后的数据
     */

    public static String encrypt(String inStr, String secretKey) {

        SecretKey deskey = new SecretKeySpec(secretKey.getBytes(), "DESede");

        Cipher cipher;

        String outStr = null;

        try {

            cipher = Cipher.getInstance("DESede");

            cipher.init(Cipher.ENCRYPT_MODE, deskey);

            outStr = byte2hex(cipher.doFinal(inStr.getBytes()));

        } catch (Exception e) {

            System.err.println("3DES加密异常" + e.getMessage());
        }



        return outStr;

    }

    /**
     * 解密
     *
     * @param inStr     需要解密的内容
     * @param secretKey 密钥
     * @return 解密后的数据
     */

    public static String decrypt(String inStr, String secretKey) {

        SecretKey deskey = new SecretKeySpec(secretKey.getBytes(), "DESede");

        Cipher cipher;
        String outStr = null;

        try {

            cipher = Cipher.getInstance("DESede");

            cipher.init(Cipher.DECRYPT_MODE, deskey);

            outStr = new String(cipher.doFinal(hex2byte(inStr)));

        } catch (Exception e) {

            System.err.println("3DES解密异常" + e.getMessage());

        }
        return outStr;

    }

    /**
     * 转化为16进制字符串方法
     *
     * @param digest 需要转换的字节组
     * @return 转换后的字符串
     */

    private static String byte2hex(byte[] digest) {

        StringBuffer hs = new StringBuffer();

        String stmp = "";

        for (int n = 0; n < digest.length; n++) {

            stmp = Integer.toHexString(digest[n] & 0XFF);

            if (stmp.length() == 1) {

                hs.append("0" + stmp);

            } else {

                hs.append(stmp);

            }

        }

        return hs.toString().toUpperCase();

    }

    /**
     * 十六进转二进制
     *
     * @param hexStr 待转换16进制字符串
     * @return 二进制字节组
     */

    public static byte[] hex2byte(String hexStr) {

        if (hexStr == null)

            return null;

        hexStr = hexStr.trim();

        int len = hexStr.length();

        if (len == 0 || len % 2 == 1)

            return null;

        byte[] digest = new byte[len / 2];

        try {

            for (int i = 0; i < hexStr.length(); i += 2) {

                digest[i / 2] = (byte) Integer.decode("0x" + hexStr.substring(i, i + 2)).intValue();

            }
            return digest;

        } catch (Exception e) {

            return null;

        }

    }

    public static Map<String, String> openDecrypt(String str){
        Map<String, String> result = new HashMap<>();
        try {
            String decrypt = decrypt(str, SECRET_KEY);
            JSONObject jsonObject = JSONObject.parseObject(decrypt);
            result.put(USER_NO_COLUMN, jsonObject.getString(USER_NO_COLUMN));
            result.put(API_KEY_COLUMN, jsonObject.getString(API_KEY_COLUMN));
        } catch (Exception e) {
            System.out.println("未能正确解析 请检查");
        }
        return result;
    }

    public static String openEncrypt(String userNo, String apiKey){
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("apiKey", apiKey);
        return encrypt(JSON.toJSONString(map), SECRET_KEY);
    }

    public static JSONObject decryptSourceOrder(String str, String key){
        try {
            String decrypt = decrypt(str, key);
            JSONObject jsonObject = JSONObject.parseObject(decrypt);
            return jsonObject;
        } catch (Exception e) {
            System.out.println("未能正确解析 请检查");
        }
        return null;
    }

    public static String encryptSourceOrder(String jsonStr, String key){
        try {
            String decrypt = encrypt(jsonStr, key);
            return decrypt;
        } catch (Exception e) {
            System.out.println("未能正确解析 请检查");
        }
        return null;
    }

    public static String getBId(){
        return null;
    }

    public static String getCId(String userNo, String apiKey){
        String s = openEncrypt(userNo, apiKey);
        return s;
    }



    /**
     * 登录密码加密
     * @param pass
     */
    public static String encryptLoginPass(String pass){
        return encrypt(pass, SECRET_KEY);
    }

    private final static int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
            Integer.MAX_VALUE};
    /**
     * 计算一个整数的大小
     *
     * @param x
     * @return
     */
    public static int sizeOfInt(int x) {
        for (int i = 0; ; i++)
            if (x <= SIZE_TABLE[i]) {
                return i + 1;
            }
    }
    /**
     * 判断字符串的每个字符是否相等
     *
     * @param str
     * @return
     */
    public static boolean isCharEqual(String str) {
        return str.replace(str.charAt(0), ' ').trim().length() == 0;
    }
    /**
     * 确定字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /**
     * 判断字符串是否为空格、空(“)”或null。
     *
     * @param str
     * @return
     */
    public static boolean equalsNull(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static String encryptMerchantSingleCode(Integer merchantId){
        if (merchantId == null) {
            return null;
        }
        return encrypt("MC:"+merchantId+":"+System.currentTimeMillis(), MERCHANT_SINGLE_SECRET_KEY).toLowerCase();
    }

    public static String decryptMerchantSingleCode(String merchantSingleCode){
        if (StringUtil.isEmpty(merchantSingleCode)) {
            return null;
        }
        String decrypt = decrypt(merchantSingleCode, MERCHANT_SINGLE_SECRET_KEY);
        String[] split = decrypt.split(":");
        return split[1];
    }


    public static String encryptChannelSingleCode(Integer merchantId){
        if (merchantId == null) {
            return null;
        }
        return encrypt("CH:"+System.currentTimeMillis()+":"+merchantId, CHANNEL_SINGLE_SECRET_KEY).toLowerCase();
    }

    public static String decryptChannelSingleCode(String merchantSingleCode){
        if (StringUtil.isEmpty(merchantSingleCode)) {
            return null;
        }
        String decrypt = decrypt(merchantSingleCode, CHANNEL_SINGLE_SECRET_KEY);
        String[] split = decrypt.split(":");
        return split[2];
    }


    private static void adminPass(String str) {
        System.out.println("admin 登录原密码"+str);
        String after = encrypt(str);
        System.out.println("admin 登录加密后"+after);
        String before = decrypt("F716793FD333E103E20F944F68FB0AA3", SECRET_KEY);
        System.out.println("admin 登录解开密后"+before);
    }

    public static void main(String[] args) {
        adminPass("123456");
    }
}
