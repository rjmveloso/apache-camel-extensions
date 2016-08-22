# Apache Camel BeanIO module extension
This package adds a `DynamicDataFormat` that wraps BeanIODataFormat allowing dynamic behavior for streamName property.
The streamName property is retrieved from message header with same property name.

## Example of usage
Example using camel-spring

```xml
<bean id="dformat" class="net.masktbit.camel.dataformat.beanio.DynamicDataFormat" />

<route id="input">
	<setHeader headerName="streamName">
		<constant>foo</constant>
  	</setHeader>
  	<to uri="seda:marshal" />
</route>

<route id="marshal">
	<from uri="seda:marshal" />
	<log message="Marshling data" loggingLevel="INFO" />
	<marshal>
		<custom ref="dformat" />
	</marshal>
	...
</route>
```
