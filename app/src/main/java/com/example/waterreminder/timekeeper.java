package com.example.waterreminder;

import io.realm.RealmObject;

public class timekeeper extends RealmObject {

    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
