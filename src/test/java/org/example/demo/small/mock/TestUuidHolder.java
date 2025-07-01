package org.example.demo.small.mock;

import org.example.demo.common.util.port.UuidHolder;

public class TestUuidHolder implements UuidHolder {

    private String defaultUuid;

    public TestUuidHolder(String defaultUuid) {
        this.defaultUuid = defaultUuid;
    }

    @Override
    public String getUuid() {
        return defaultUuid;
    }
}
