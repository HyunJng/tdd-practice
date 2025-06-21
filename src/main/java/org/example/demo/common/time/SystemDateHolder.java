package org.example.demo.common.time;

import lombok.RequiredArgsConstructor;
import org.example.demo.common.time.port.DateHolder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class SystemDateHolder implements DateHolder {

    @Override
    public String now() {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return dateTimeFormatter.format(now);
    }

    @Override
    public long nowEpochTime() {
        return new Date().getTime();
    }
}
