package com.aiccj.abtest.common.token;

import com.aiccj.abtest.common.exception.BusinessException;
import com.aiccj.abtest.common.exception.BusinessMsg;
import com.aiccj.abtest.service.CacheService;
import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public class TokenManager {

    public static final String SECRET_KEY = "60b1ae7f6c9540578b40a39f4cabfe66";
    public static final String nonce = "zgdAlijeRbCFVuIr";
    public static final String associatedData = "gYFTabEIHuzxiHG2";

    private static Logger logger = LoggerFactory.getLogger(TokenManager.class);

    public static String createToken(Long userId
            , String userName, String role, LoginType type){
        try {
            String content = JSON.toJSONString(new TokenDetail(userId, userName, type.name(),role, System.currentTimeMillis()));

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128,
                    nonce.getBytes(StandardCharsets.UTF_8));
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                    "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            if (StringUtils.isNotEmpty(associatedData)) {
                cipher.updateAAD(associatedData.getBytes(StandardCharsets.UTF_8));
            }
            byte[] resultMessage = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

            String encode = Base64.getEncoder().encodeToString(resultMessage);

            return encode;
        } catch (Exception ex) {
            throw new BusinessException(BusinessMsg.SYS_ERROR);
        }
    }


    @Autowired
    CacheService cacheService;

    public static TokenDetail getTokenDetail(String token) {
        TokenDetail tokenDetail;
        try {
            byte[] cipherByte = Base64.getDecoder().decode(token);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128,
                    nonce.getBytes(StandardCharsets.UTF_8));
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                    "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            if (StringUtils.isNotEmpty(associatedData)) {
                cipher.updateAAD(associatedData.getBytes(StandardCharsets.UTF_8));
            }
            byte[] dataMessage = cipher.doFinal(cipherByte);
            String s = new String(dataMessage, StandardCharsets.UTF_8);
            tokenDetail = JSON.parseObject(s, TokenDetail.class);
        } catch (Exception ex) {
            throw new BusinessException(BusinessMsg.NOT_LOGIN);
        }

        if (tokenDetail == null){
            logger.error("TokenDetail is null!");
            throw new BusinessException(BusinessMsg.NOT_LOGIN);
        }

        if (tokenDetail.getId() == null){
            logger.error("accountId is null!");
            throw new BusinessException(BusinessMsg.NOT_LOGIN);
        }

        if (tokenDetail.getType() == null){
            logger.error("type is null!");
            throw new BusinessException(BusinessMsg.NOT_LOGIN);
        }
        return tokenDetail;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenDetail {
        private Long id;
        private String name;
        private String type;
        private String role;
        private Long timestamp;
    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidateResult {
        private CheckTokenResult checkTokenResult;
        private TokenDetail tokenDetail;
    }

    public enum CheckTokenResult {

        OK, EXPIRED, OFFLINE,
        ;

        public void validateResult(){
            switch (this) {
                case OK:
                    break;
                case EXPIRED:
                    throw new BusinessException(BusinessMsg.TOKEN_EXPIRED);
                case OFFLINE:
                    throw new BusinessException(BusinessMsg.TOKEN_OFFLINE);
            }
        }
    }

    public static String randomString(int length) {
        String sampleString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final StringBuilder sb = new StringBuilder(length);
        int baseLength = sampleString.length();
        while (sb.length() < length) {
            int number = ThreadLocalRandom.current().nextInt(baseLength);
            sb.append(sampleString.charAt(number));
        }
        return sb.toString();
    }

}
