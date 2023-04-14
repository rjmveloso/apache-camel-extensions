package io.github.apache.camel.model;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.IdAware;
import org.apache.camel.support.service.ServiceSupport;

public class RouteDocProcessor extends ServiceSupport implements Processor, IdAware {

    private String id;

    private final RouteDescription description;

    private final RouteDocGeneratorFactory factory;

    public RouteDocProcessor(RouteDescription description) {
        this.description = description;
        this.factory = createDocGeneratorFactory();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        CamelContext context = exchange.getContext();
        RouteResponseAdapter adapter = createResponseAdapter(exchange);
        factory.create(context).generate(adapter, description);
    }

    protected RouteResponseAdapter createResponseAdapter(Exchange exchange) {
        return new ExchangeResponseAdapter(exchange);
    }

    protected RouteDocGeneratorFactory createDocGeneratorFactory() {
        return new YamlRouteDocGeneratorFactory();
    }

}
