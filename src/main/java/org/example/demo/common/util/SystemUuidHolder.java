package org.example.demo.common.util;

import org.example.demo.common.util.port.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {

    @Override
    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}
