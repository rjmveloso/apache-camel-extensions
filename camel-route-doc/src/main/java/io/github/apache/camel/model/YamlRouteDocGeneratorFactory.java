package io.github.apache.camel.model;

import org.apache.camel.CamelContext;

public class YamlRouteDocGeneratorFactory implements RouteDocGeneratorFactory {

    @Override
    public RouteDocGenerator create(CamelContext context) {
        return new YamlDocumentGenerator();
    }

}
