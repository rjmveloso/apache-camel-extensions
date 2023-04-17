package io.github.apache.camel.model;

import java.io.IOException;

public interface RouteDocGenerator {

    void generate(RouteResponseAdapter response, RouteDescription description) throws IOException;

}
