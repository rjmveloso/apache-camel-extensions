# Apache Camel BeanIO module extension
This package adds a `DynamicDataFormat` that wraps BeanIODataFormat allowing dynamic behavior for streamName property.  
The stream name is retrieved from a property named *streamName*.

## Example of usage
Example using camel-spring

```xml

<bean id="dformat" class="io.github.apache.camel.dateformat.beanio.DynamicDataFormat">
    <property name="mapping" value="io/github/mappings.xml"/>
    <property name="encoding" value="UTF-8"/>
</bean>

<camelContext xmlns="http://camel.apache.org/schema/spring">
<route id="route1">
    <setProperty name="streamName">
        <constant>stream1</constant>
    </setProperty>
    <to uri="direct:marshal"/>
</route>

<route id="route2">
    <setProperty name="streamName">
        <constant>stream2</constant>
    </setProperty>
    <to uri="direct:marshal"/>
</route>

<route id="marshal">
    <from uri="direct:marshal"/>
    <log message="marshling..." loggingLevel="INFO"/>
    <marshal>
        <custom ref="dformat"/>
    </marshal>
    <to uri="mock:beanio-marshal"/>
</route>
</camelContext>
```
