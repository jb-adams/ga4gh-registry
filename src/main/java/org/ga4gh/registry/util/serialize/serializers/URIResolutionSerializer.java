package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ga4gh.registry.model.URIResolution;

public class URIResolutionSerializer  extends VariableDepthSerializer<URIResolution> {

    private static final long serialVersionUID = 1L;

    public URIResolutionSerializer() {
        super(URIResolution.class);
    }

    public URIResolutionSerializer(String[] serializedRelationalAttributes) {
        super(URIResolution.class, serializedRelationalAttributes);
    }

    @Override
    public void serialize(URIResolution value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        
        gen.writeStartObject();
        writeStringIfExists(gen, "serviceId", value.getServiceId());
        writeStringIfExists(gen, "serviceName", value.getServiceName());
        writeObjectIfSelected(gen, "serviceType", value.getServiceType());
        writeStringIfExists(gen, "resolvedURL", value.getResolvedURL());
        gen.writeEndObject();
    }
}
