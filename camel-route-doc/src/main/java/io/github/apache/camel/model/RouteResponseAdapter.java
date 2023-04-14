package io.github.apache.camel.model;

import java.io.IOException;

public interface RouteResponseAdapter {

    void setHeader(String name, String value);

    void write(byte[] data) throws IOException;

}
