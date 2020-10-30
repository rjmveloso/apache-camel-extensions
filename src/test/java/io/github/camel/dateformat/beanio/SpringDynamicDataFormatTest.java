package io.github.camel.dateformat.beanio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.github.camel.dateformat.beanio.record.Record;
import io.github.camel.dateformat.beanio.record.RecordDetail;

public class SpringDynamicDataFormatTest extends CamelSpringTestSupport {

	private final String STREAM_PROPERTY_NAME = "streamName";

	private final String data_simple = "001,Joey,20201030" + System.lineSeparator() //
			+ "002,Johnny,20201030" + System.lineSeparator() //
			+ "003,Dee Dee,20201030" + System.lineSeparator();

	private final String data_complete = "001,Joey,20201030,A,2,80.50" + System.lineSeparator() //
			+ "002,Johnny,20201030,B,1,25.00" + System.lineSeparator() //
			+ "003,Dee Dee,20201030,C,2,48.25" + System.lineSeparator();

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("spring-test.xml");
	}

	@Test
	void testMarshalSimple() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:beanio-marshal");
		mock.expectedPropertyReceived(STREAM_PROPERTY_NAME, "test-simple");
		mock.expectedBodiesReceived(data_simple);

		template.sendBody("direct:marshal-simple", createTestData());

		mock.assertIsSatisfied();
	}

	@Test
	void testMarshalComplete() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:beanio-marshal");
		mock.expectedPropertyReceived(STREAM_PROPERTY_NAME, "test-complete");
		mock.expectedBodiesReceived(data_complete);

		template.sendBody("direct:marshal-complete", createTestData());

		mock.assertIsSatisfied();
	}

	private List<Record> createTestData() throws ParseException {
		Date date = new SimpleDateFormat("yyyyMMdd").parse("20201030");

		List<Record> body = new ArrayList<>();
		body.add(new Record("001", "Joey", date).add(new RecordDetail("A", 2, 80.50)));
		body.add(new Record("002", "Johnny", date).add(new RecordDetail("B", 1, 25.00)));
		body.add(new Record("003", "Dee Dee", date).add(new RecordDetail("C", 2, 48.25)));
		return body;
	}
}
