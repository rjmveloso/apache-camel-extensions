package io.github.apache.camel.model;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.spi.DataFormat;

public class RouteDocDefinition {

    private final RouteBuilder routeBuilder;

    private final RouteDescription routeDescription;

    private DataFormatDefinition dataFormatDefinition;

    public RouteDocDefinition(RouteBuilder routeBuilder) {
        this(null, routeBuilder);
    }

    public RouteDocDefinition(String routeId, RouteBuilder routeBuilder) {
        this.routeBuilder = routeBuilder;
        this.routeDescription = new RouteDescription(routeId);
    }

    public RouteBuilder end() {
        return routeBuilder;
    }

    public RouteDescription getRouteDescription() {
        return routeDescription;
    }

    public DataFormatDefinition getDataFormatDefinition() {
        return dataFormatDefinition;
    }

    /**
     * Not being used for the moment
     * @param dataFormat formatter to format data before passing to {@link RouteDocGenerator}
     * @return RouteDocDefinition builder
     */
    public RouteDocDefinition marshall(DataFormat dataFormat) {
        dataFormatDefinition = new DataFormatDefinition(dataFormat);
        return this;
    }

    public RouteDocDefinition header(String name, boolean required) {
        return header(name, null, required);
    }

    public RouteDocDefinition header(String name, String descr, boolean required) {
        routeDescription.header(new RouteDescription.HeaderDefinition(name, descr, required));
        return this;
    }

}
