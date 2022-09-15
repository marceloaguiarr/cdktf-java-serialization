package com.company.app;

import com.hashicorp.cdktf.Testing;
import com.hashicorp.cdktf.providers.docker.Image;
import com.mycompany.app.MainStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// The tests below are example tests, you can find more information at
// https://cdk.tf/testing
public class MainTest {

    private final MainStack stack = new MainStack(Testing.app(), "stack");
    private final String synthesized = Testing.synth(stack);

    @Test
    void shouldContainContainerAndPrintSynth() {
        System.out.println(synthesized);
        assertTrue(Testing.toHaveResource(synthesized, Image.TF_RESOURCE_TYPE) );
    }
}