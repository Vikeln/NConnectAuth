package co.ke.mymobi.events;

import co.ke.mymobi.entities.User;
import co.ke.mymobi.models.Mail;
import co.ke.mymobi.repositories.AccountDao;
import co.ke.mymobi.repositories.UserDao;
import co.ke.mymobi.services.EmailService;
import co.ke.mymobi.services.UserService;
import co.ke.mymobi.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ApplicationEventListenerValve {

    @Value("${adminPanel}")
    private String adminPanel;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountDao accountDao;

    private Logger logger = LoggerFactory.getLogger(ApplicationEventListenerValve.class);

    @Async
    @EventListener
    public void handleUserPasswordResetEvent(UserPasswordResetEvent event) {
        sendPasswordMail(event.getUser(), "Password Reset", "Your Password has been Reset  on Admin Dashboard", event.getTenantName());
    }


    @Async
    @EventListener
    public void newUserCreatedEventListener(NewUserCreatedEvent event) {
        logger.info("new User created event fired {}", Utilities.toJson(event));
        User user = event.getUser();
        String userId = null;
        User u = null;
        if (user.getCorrelator() == null) {
            userId = userService.createUpdateUsers();

            if (userId != null) {
                u = updateUserCorrelator(user, userId);

            } else {
                logger.error("newUserCreatedEventListener failed!");
            }
        } else {
            userId = user.getCorrelator();
            u = user;
        }
        sendPasswordMail(u, event.getTenantName() + " Account", "Your user account has been setup. Please verify by clicking the link below. You will be required to set your password. Once done, log in with your username :  " +
                u.getUserName() + " and your new password", u.getTenant().getName());

    }

    private User updateUserCorrelator(User user, String userId) {
        logger.error("userId {}", userId);
        user.setCorrelator(userId);
        user = userDao.save(user);
        return user;
    }


    private void sendPasswordMail(User user, String subject, String message, String tenant) {
        Mail mail = new Mail();
        mail.setTo(user.getEmail());
        mail.setSubject(subject);
        Map<String, Object> map = new HashMap<>();
        map.put("token", (user.getTenant().getClientBaseUrl() != null ? (user.getTenant().getClientBaseUrl() + "/") : (adminPanel + "/auth/resetpassword/")) + user.getCorrelator());
        map.put("name", user.getFirstName());
        map.put("message", message);
        map.put("signature", tenant + " Admin");
        mail.setModel(map);
        emailService.sendSimpleMessage(mail);
    }

}
