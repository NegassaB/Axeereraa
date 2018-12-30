package com.negassagisila.axeereraa;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class AxeereraaSystemTest {

    @Test
    public void shouldShowSampleText() {
        ApplicationRunner runner = new ApplicationRunner();

        String sampleText = runner.run("/home/gadd/.Axeereraa/sample.ser");

        Assert.assertThat(
                "file not found ", sampleText,
                Matchers.containsString("It works!!")
        );
    }
}
