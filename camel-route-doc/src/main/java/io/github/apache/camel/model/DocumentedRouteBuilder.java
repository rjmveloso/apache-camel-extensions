package io.github.apache.camel.model;

import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

public abstract class DocumentedRouteBuilder extends RouteBuilder {

    private RouteDocDefinition definition;

    public RouteDocDefinition documented() {
        return documented(null);
    }

    public RouteDocDefinition documented(String routeId) {
        definition = new RouteDocDefinition(routeId, this);
        return definition;
    }

    @Override
    protected void populateRoutes() throws Exception {
        RouteDefinition route = asInternalRoute();
        getRouteCollection().route(route);
        super.populateRoutes();
    }

    protected RouteDefinition asInternalRoute() {
        ExtendedCamelContext ecc = getContext().adapt(ExtendedCamelContext.class);

        RouteDefinition answer = new RouteDefinition();
        answer.setId(definition.getRouteDescription().getRouteId());

        final String routeId = answer.idOrCreate(ecc.getNodeIdFactory());
        definition.getRouteDescription().setRouteId(routeId);

        answer.from("servlet:route-doc/" + routeId);
        answer.id("route-doc:" + routeId);
        answer.process(new RouteDocProcessor(definition.getRouteDescription()));
        return answer;
    }

}
