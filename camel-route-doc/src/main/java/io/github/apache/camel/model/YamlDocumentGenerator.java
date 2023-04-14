package io.github.apache.camel.model;

import org.apache.camel.Exchange;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;

public class YamlDocumentGenerator implements RouteDocGenerator {

    @Override
    public void generate(RouteResponseAdapter response, RouteDescription description) throws IOException {
        Representer representer = createRepresenter();

        Yaml yaml = new Yaml(representer);

        response.setHeader(Exchange.CONTENT_TYPE, "text/yaml");
        response.write(yaml.dump(description).getBytes());
    }

    protected Representer createRepresenter() {
        Representer representer = new SkipNullRepresenter();
        representer.getPropertyUtils().setAllowReadOnlyProperties(true);
        representer.addClassTag(RouteDescription.class, Tag.MAP);
        return representer;
    }

    private static class SkipNullRepresenter extends Representer {
        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
            if (propertyValue == null) {
                return null;
            }
            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
        }
    }

}
