package com.negassagisila.axeereraa;

import org.junit.Assert;
import org.junit.Test;

public class DisplayMarkdownTest {
  
@Test
  public void convertToMarkdownTest() throws Exception {
    String testData = "* [ ] Open item\n" +
            "* [x] Closed item\n";
    String testMethodOutput = DisplayMarkdown.convertToMarkdown(testData);
    String output = "<ul>\n" +
            "<li class=\"task-list-item\"><input type=\"checkbox\" class=\"task-list-item-checkbox\" disabled=\"disabled\" readonly=\"readonly\" />&nbsp;Open item</li>\n" +
            "<li class=\"task-list-item\"><input type=\"checkbox\" class=\"task-list-item-checkbox\" checked=\"checked\" disabled=\"disabled\" readonly=\"readonly\" />&nbsp;Closed item</li>\n" +
            "</ul>\n";
    Assert.assertEquals("stupid motherfucker doesn't work", output, testMethodOutput);
  }
  
}