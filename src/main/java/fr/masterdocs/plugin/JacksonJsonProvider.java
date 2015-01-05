package fr.masterdocs.plugin;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JacksonJsonProvider extends JacksonJaxbJsonProvider {

    public JacksonJsonProvider() {
        super();
        configure(Feature.FAIL_ON_EMPTY_BEANS, false);
        configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this._mapperConfig.getConfiguredMapper().getSerializationConfig().withSerializationInclusion(Inclusion.NON_NULL);
    }

    public ObjectMapper getObjectMapper() {
        return this._mapperConfig.getConfiguredMapper();
    }

}
