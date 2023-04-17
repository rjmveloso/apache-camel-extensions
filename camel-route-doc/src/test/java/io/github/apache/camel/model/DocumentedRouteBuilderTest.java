package io.github.apache.camel.model;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentedRouteBuilderTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        replaceRouteFromWith("route-doc:route1", "direct:route-doc/route1");

        return new DocumentedRouteBuilder() {
            @Override
            public void configure() throws Exception {
                documented()
                        .header("header1", "mandatory header", true)
                        .header("header2", "optional header", false)
                        .end()
                            .from("direct:start").to("mock:result");
            }
        };
    }

    @Test
    void validateDocumentedHeaders() {
        assertEquals(2, context.getRoutes().size());

        assertNotNull(context.getRouteDefinition("route-doc:route1"));

        /*
        context.getProcessor("process1", RouteDocProcessor.class).setDocGeneratorFactory(context ->
                (response, description) -> {
                    response.setHeader(Exchange.CONTENT_TYPE, "text/yaml");
                    response.write("DUMMY".getBytes());
                }
        );
        */

        Exchange result = template.send("direct:route-doc/route1", processor -> {});
        assertEquals("text/yaml", result.getIn().getHeader(Exchange.CONTENT_TYPE));
        assertEquals("headers:\n" +
                "- {descr: mandatory header, name: header1, required: true}\n" +
                "- {descr: optional header, name: header2, required: false}\n" +
                "routeId: route1\n",
                result.getMessage().getBody(String.class));
    }

}
