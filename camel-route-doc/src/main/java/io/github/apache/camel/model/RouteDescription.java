package io.github.apache.camel.model;

import java.util.LinkedList;
import java.util.List;

public class RouteDescription {
    private String routeId;
    private String description;
    private final List<HeaderDefinition> headers = new LinkedList<>();

    public RouteDescription(String routeId) {
        this(routeId, null);
    }

    public RouteDescription(String routeId, String description) {
        this.routeId = routeId;
        this.description = description;
    }

    public String getRouteId() {
        return routeId;
    }

    void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getDescription() {
        return description;
    }

    public List<HeaderDefinition> getHeaders() {
        return headers;
    }

    void header(HeaderDefinition header) {
        this.headers.add(header);
    }

    public static class HeaderDefinition {
        private final String name;
        private final String descr;
        private final boolean required;

        HeaderDefinition(String name, String descr, boolean required) {
            this.name = name;
            this.descr = descr;
            this.required = required;
        }

        public String getName() {
            return name;
        }

        public String getDescr() {
            return descr;
        }

        public boolean isRequired() {
            return required;
        }
    }
}
