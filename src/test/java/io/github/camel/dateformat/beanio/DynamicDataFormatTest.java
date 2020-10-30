package io.github.camel.dateformat.beanio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import io.github.camel.dateformat.beanio.record.Record;

class DynamicDataFormatTest extends CamelTestSupport {

	private final String STREAM_PROPERTY_NAME = "streamName";

	private final String data = "001,Joey,20201030" + System.lineSeparator() //
			+ "002,Johnny,20201030" + System.lineSeparator() //
			+ "003,Dee Dee,20201030" + System.lineSeparator();

	@Test
	void testMarshal() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:beanio-marshal");
		mock.expectedPropertyReceived(STREAM_PROPERTY_NAME, "test-simple");
		mock.expectedBodiesReceived(data);

		template.sendBody("direct:marshal", createTestData());

		mock.assertIsSatisfied();
	}

	@Test
	void testUnmarshal() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:beanio-unmarshal");
		mock.expectedPropertyReceived(STREAM_PROPERTY_NAME, "test-simple");
		mock.expectedBodiesReceived(createTestData());

		template.sendBody("direct:unmarshal", data);

		mock.assertIsSatisfied();
	}

	private List<Record> createTestData() throws ParseException {
		Date date = new SimpleDateFormat("yyyyMMdd").parse("20201030");

		List<Record> body = new ArrayList<>();
		body.add(new Record("001", "Joey", date));
		body.add(new Record("002", "Johnny", date));
		body.add(new Record("003", "Dee Dee", date));
		return body;
	}

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			@Override
			public void configure() {
				DynamicDataFormat format = new DynamicDataFormat("mappings.xml");

				from("direct:marshal").setProperty(STREAM_PROPERTY_NAME, constant("test-simple"))
						.marshal(format).to("mock:beanio-marshal");

				from("direct:unmarshal").setProperty(STREAM_PROPERTY_NAME, constant("test-simple"))
						.unmarshal(format).split(body()).to("mock:beanio-unmarshal");
			}
		};
	}

}
