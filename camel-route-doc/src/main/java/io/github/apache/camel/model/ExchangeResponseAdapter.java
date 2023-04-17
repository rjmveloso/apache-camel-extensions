package io.github.apache.camel.model;

import org.apache.camel.Exchange;

import java.io.IOException;

public class ExchangeResponseAdapter implements RouteResponseAdapter {

    private final Exchange exchange;

    public ExchangeResponseAdapter(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void setHeader(String name, String value) {
        exchange.getIn().setHeader(name, value);
    }

    @Override
    public void write(byte[] data) throws IOException {
        exchange.getIn().setBody(data);
    }

}
