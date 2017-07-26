package com.dev.dongworry.events;

/**
 * Created by dengyaoning on 17/7/25.
 */

public class HttpEvent {

    private int eventFlag;

    public int getEventFlag() {
        return eventFlag;
    }

    public HttpEvent setEventFlag(int eventFlag) {
        this.eventFlag = eventFlag;
        return this;
    }

    public static final int NETWORK_ERROR = 0x001;
}
