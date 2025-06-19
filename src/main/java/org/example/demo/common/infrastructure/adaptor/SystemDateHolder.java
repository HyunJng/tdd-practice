package org.example.demo.common.infrastructure.adaptor;

import lombok.RequiredArgsConstructor;
import org.example.demo.common.service.port.DateHolder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SystemDateHolder implements DateHolder {

    @Override
    public String now() {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return dateTimeFormatter.format(now);
    }
}
