package io.github.apache.camel.model;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.infra.common.services.TestService;
import org.apache.camel.test.infra.jetty.services.JettyConfiguration;
import org.apache.camel.test.infra.jetty.services.JettyConfiguration.ServletHandlerConfiguration.ServletConfiguration;
import org.apache.camel.test.infra.jetty.services.JettyConfigurationBuilder;
import org.apache.camel.test.infra.jetty.services.JettyEmbeddedService;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import static org.apache.camel.test.infra.jetty.services.JettyConfiguration.ROOT_CONTEXT_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentedRouteTest extends CamelTestSupport {

    private static final JettyConfiguration config = JettyConfigurationBuilder.emptyTemplate()
            .withPort(AvailablePortFinder.getNextAvailable())
            .withContextPath(ROOT_CONTEXT_PATH)
            .withServletConfiguration()
            .addServletConfiguration(
                    new ServletConfiguration<>(CamelHttpTransportServlet.class.getName(), "/services/*", "CamelServlet")
            ).build().build();

    @RegisterExtension
    static TestService testService = new JettyEmbeddedService(config);

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return super.createCamelContext();
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
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
    void testJettyServerRunning() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + config.getPort() + "/services/route-doc/route1"))
                .GET().build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());

        assertEquals("headers:\n" +
                "- {descr: mandatory header, name: header1, required: true}\n" +
                "- {descr: optional header, name: header2, required: false}\n" +
                "routeId: route1\n",
                response.body());
    }

}
