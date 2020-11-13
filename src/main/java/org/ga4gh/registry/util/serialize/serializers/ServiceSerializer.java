package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Service;

public class ServiceSerializer extends VariableDepthSerializer<Service> {

    private static final long serialVersionUID = 1L;

    public ServiceSerializer() {
        super(Service.class);
    }

    public ServiceSerializer(String[] serializedRelationalAttributes) {
        super(Service.class, serializedRelationalAttributes);
    }

    @Override
    public void serialize(Service value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId());
        writeStringIfExists(gen, "name", value.getName());
        writeStringIfExists(gen, "description", value.getDescription());
        writeObjectIfExists(gen, "type", value.getServiceType());
        writeObjectIfSelected(gen, "organization", value.getOrganization());
        writeStringIfExists(gen, "contactUrl", value.getContactUrl());
        writeStringIfExists(gen, "documentationUrl", value.getDocumentationUrl());
        writeObjectIfExists(gen, "createdAt", value.getCreatedAt());
        writeObjectIfExists(gen, "updatedAt", value.getUpdatedAt());
        writeStringIfExists(gen, "environment", value.getEnvironment());
        writeStringIfExists(gen, "version", value.getVersion());
        writeStringIfExists(gen, "url", value.getUrl());
        writeStringIfExists(gen, "curiePrefix", value.getCuriePrefix());
        gen.writeEndObject();

    }
}
