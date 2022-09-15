package com.mycompany.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashicorp.cdktf.Fn;
import com.hashicorp.cdktf.TerraformStack;
import com.hashicorp.cdktf.providers.docker.DockerProvider;
import com.hashicorp.cdktf.providers.docker.Image;
import software.constructs.Construct;

public class MainStack extends TerraformStack {

    public record TestingResource(String name, Number count) {}

    public MainStack(final Construct scope, final String id) {
        super(scope, id);

        TestingResource testingResource = new TestingResource(
                Fn.trimprefix("cpc_123456", "cpc_"),
                Fn.tonumber(Fn.trimprefix("cpc_123456", "cpc_"))
        );

        final String valueAsString;
        try {
            valueAsString = new ObjectMapper().writeValueAsString(testingResource);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        DockerProvider.Builder.create(this, "docker")
                .build();

        Image image = Image.Builder.create(this, "nginxImage")
                // Adding the name directly, no serialization
                .name(testingResource.name())

                // Adding the count directly, no serialization
                .count(testingResource.count())

                // Not really relevant the use of the 'pullTrigger', I just used it because it expects a String value
                // This is where the bug shows up in the synthesized count value
                .pullTrigger(valueAsString)

                .build();

    }
}
