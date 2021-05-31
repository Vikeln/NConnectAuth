package co.ke.mymobi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Utilities {

    public static final Logger logger = LoggerFactory.getLogger("MFS-DevUtils");
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String STATUS = "status";
    public static final String TITLE = "title";
    public static final String CUSTOMER = "customer";
    public static final String MESSAGE = "message";
    public static final String CURRENCIES = "currencies";
    public static final String ERROR_LOG_FORMAT = "error={}";

    private Utilities() {
    }


    public static ObjectMapper mapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return mapper;
    }
    public static String toJson(Object entity) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {

            logger.info("context {}", e);
        }
        return json;
    }
    public static String createAppKey(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getRequestAppKey(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader("App-Key");
    }

    public static Date getNewDateAfterAddingDays(Integer daysToAdd) {
        if (daysToAdd == null || daysToAdd == 0) {
            return new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, daysToAdd);
        return calendar.getTime();
    }

    public static PrivateKey getPrivateKey(String filename) {
        PrivateKey privateKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }
    public static PublicKey getPublicKey(String filename) {
        PublicKey publicKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
