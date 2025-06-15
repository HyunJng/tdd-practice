package org.example.demo.small.mock;

import org.example.demo.common.service.port.DateHolder;

public class TestDateHolder implements DateHolder {
    private final String date;

    public TestDateHolder(String date) {
        this.date = date;
    }

    @Override
    public String now() {
        return date;
    }
}
