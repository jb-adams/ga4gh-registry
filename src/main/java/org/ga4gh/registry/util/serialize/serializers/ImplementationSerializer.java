package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.Implementation;

public class ImplementationSerializer extends VariableDepthSerializer<Implementation> {

    private static final long serialVersionUID = 1L;

    public ImplementationSerializer() {
        super(Implementation.class);
    }

    public ImplementationSerializer(String[] serializedRelationalAttributes) {
        super(Implementation.class, serializedRelationalAttributes);
    }

    @Override
    public void serialize(Implementation value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        
        gen.writeStartObject();
        writeStringIfExists(gen, "id", value.getId());
        writeStringIfExists(gen, "name", value.getName());
        writeStringIfExists(gen, "description", value.getDescription());
        writeObjectIfSelected(gen, "organization", value.getOrganization());
        writeStringIfExists(gen, "contactUrl", value.getContactUrl());
        writeStringIfExists(gen, "documentationUrl", value.getDocumentationUrl());
        writeObjectIfExists(gen, "createdAt", value.getCreatedAt());
        writeObjectIfExists(gen, "updatedAt", value.getUpdatedAt());
        writeStringIfExists(gen, "version", value.getVersion());
        writeObjectIfSelected(gen, "standards", value.getStandards());
        gen.writeEndObject();
    }
}
