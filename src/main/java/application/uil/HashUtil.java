package application.uil;

import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * Created by Joybin on 2016/11/30.
 */
public class HashUtil {

    private final static String SHA_256 = "SHA-256";
    private final static String SHA_512 = "SHA-512";
    private final static String SHA_1 = "SHA-1";
    private final static String MD5 = "MD5";
    private static final Logger LOGGER = LoggerFactory.getLogger(HashUtil.class);

    public static String SHA256(final String strText) {
        return SHA(strText, SHA_256);
    }

    public static String SHA512(final String strText) {
        return SHA(strText, SHA_512);
    }

    public static String SHA1(final String strText) {
        return SHA(strText, SHA_1);
    }

    public static String MD5(final String strText) {
        return SHA(strText, MD5);
    }

    private static String SHA(final String strText, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (!Strings.isEmpty(strType)) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                messageDigest.update(strText.getBytes("UTF-8"));
                byte byteBuffer[] = messageDigest.digest();

                StringBuffer strHexString = new StringBuffer();
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return strResult;
    }
}
