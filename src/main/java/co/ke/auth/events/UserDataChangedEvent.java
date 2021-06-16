package co.ke.auth.events;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class UserDataChangedEvent extends ApplicationEvent {
    private String correlator;
    private HashMap<String, Object> userData;
    private String userRealm;

    public UserDataChangedEvent(Object source, String correlator, HashMap<String, Object> userData, String realm) {
        super(source);
        this.correlator = correlator;
        this.userData = userData;
        this.userRealm = realm;
    }

    public String getCorrelator() {
        return correlator;
    }

    public void setCorrelator(String correlator) {
        this.correlator = correlator;
    }

    public HashMap<String, Object> getUserData() {
        return userData;
    }

    public void setUserData(HashMap<String, Object> userData) {
        this.userData = userData;
    }

    public String getUserRealm() {
        return userRealm;
    }

    public void setUserRealm(String userRealm) {
        this.userRealm = userRealm;
    }
}
