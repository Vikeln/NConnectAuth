package co.ke.auth.utils;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author TMwaura on 03/10/2019
 * @Project mobiloan-iso-bridge
 */
public class IpAddressGenerator implements ValueGenerator<String> {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String generateValue(Session session, Object object) {
        try {
            return  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        }catch (Exception ex){
            logger.warn("No Ip was generated for {}", object.getClass().getName());
         return null;
        }
    }
}
