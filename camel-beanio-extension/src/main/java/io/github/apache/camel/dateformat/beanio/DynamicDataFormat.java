package io.github.apache.camel.dateformat.beanio;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.annotations.Dataformat;

@Dataformat("beanio")
public class DynamicDataFormat extends BeanIODataFormat implements DataFormat {

	private final ThreadLocal<String> streamNameProperty = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "";
        }
	};

	public DynamicDataFormat() {
	}

	public DynamicDataFormat(String mapping) {
		super(mapping, "");
	}

	@Override
	public String getStreamName() {
		return streamNameProperty.get();
	}

	protected void setStreamName(Exchange exchange) {
		this.streamNameProperty.set((String) exchange.getProperty("streamName"));
	}

	@Override
	public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
		setStreamName(exchange);
		super.marshal(exchange, graph, stream);
		streamNameProperty.remove();
	}

	@Override
	public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
		setStreamName(exchange);
		Object result = super.unmarshal(exchange, stream);
		streamNameProperty.remove();
		return result;
	}

}
