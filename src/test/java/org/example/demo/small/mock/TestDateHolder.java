package org.example.demo.small.mock;

import org.example.demo.common.time.port.DateHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestDateHolder implements DateHolder {
    private final String date;
    private boolean useRealTime = false;

    public TestDateHolder(String date) {
        this.date = date;
    }

    public void setUseRealTimeInOneTime() {
        this.useRealTime = true;
    }

    @Override
    public String now() {
        if (useRealTime) {
            this.useRealTime = false;
            ZonedDateTime now = ZonedDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            return dateTimeFormatter.format(now);
        }
        return date;
    }

    @Override
    public long nowEpochTime() {
        if (useRealTime) {
            this.useRealTime = false;
            return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            return zonedDateTime.toInstant().toEpochMilli();
        }
    }
}
