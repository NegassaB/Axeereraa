package com.negassagisila.axeereraa;

import org.junit.Assert;
import org.junit.Test;

public class DisplayMarkdownTest {
  
@Test
  public void convertToMarkdownTest() throws Exception {
    String testData = "* [ ] Open item\n" +
            "* [x] Closed item\n";
    String testMethodOutput = DisplayMarkdown.convertToMarkdown(testData);
    String output = "";
    Assert.assertEquals("stupid motherfucker doesn't work", output, testMethodOutput);
  }
  
}