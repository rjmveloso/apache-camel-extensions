package io.github.apache.camel.model;

import org.apache.camel.CamelContext;

public interface RouteDocGeneratorFactory {

    RouteDocGenerator create(CamelContext context);

}
