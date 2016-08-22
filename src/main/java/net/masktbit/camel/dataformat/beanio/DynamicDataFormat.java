package net.masktbit.camel.dataformat.beanio;

import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.apache.camel.spi.DataFormat;

public class DynamicDataFormat implements DataFormat, CamelContextAware {

	private static final String MAPPING = "META-INF/beanio/mappings.xml";

	private BeanIODataFormat df = new BeanIODataFormat(MAPPING, "");

	@PostConstruct
	void initialize() {
		try {
			df.start();
		} catch (Exception e) {
		}
	}

	@PreDestroy
	void destroy() {
		try {
			df.stop();
		} catch (Exception e) {
		}
	}

	@Override
	public CamelContext getCamelContext() {
		return df.getCamelContext();
	}

	@Override
	public void setCamelContext(CamelContext camelContext) {
		df.setCamelContext(camelContext);
	}

	@Override
	public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
		df.setStreamName(getStreamName(exchange));
		df.marshal(exchange, graph, stream);
	}

	@Override
	public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
		df.setStreamName(getStreamName(exchange));
		return df.unmarshal(exchange, stream);
	}

	private String getStreamName(Exchange exchange) {
		return (String) exchange.getIn().getHeader("streamName");
	}

}
