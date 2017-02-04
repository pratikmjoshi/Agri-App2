package com.example.prateekjoshi.agri_app;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Prateek Joshi on 12/28/2016.
 */

public class AlertItem extends RealmObject {

    private RealmList<RealmString> alertMessages;

    public RealmList<RealmString> getAlertMessages() {
        return alertMessages;
    }

    public void setAlertMessages(RealmList<RealmString> alertMessages) {
        this.alertMessages = alertMessages;
    }
}
