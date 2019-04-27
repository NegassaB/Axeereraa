package com.negassagisila.axeereraa;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

import javax.swing.*;

/**
 *
 */

class DisplayMarkdown {
  
  /**
   * @param writtenMarkdownText markdown document to be parsed by
   * com.vladsch.flexmark.parser.Parser.parse() method
   * @return a String representation of the written markdown text
   */
  private static String convertToMarkdown(String writtenMarkdownText) {
    Parser parser = Parser.builder().build();
    Node document = parser.parse(writtenMarkdownText);
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    return renderer.render(document);
  }
  
  /**
   * This method is responsible for instantiating a new JEditorPane that contains the markdown
   * text that will be shown to the user.
   * @param writtenMarkDownText markdown text that will be parsed by DisplayMarkdown.convertToMarkdown()
   * @return a JEditorPane that will be displayed
   */
  static JEditorPane displayMarkdown(String writtenMarkDownText) {
    JEditorPane markdownPane = new JEditorPane("text/html", DisplayMarkdown.convertToMarkdown(writtenMarkDownText));
    markdownPane.setEditable(false);
    return markdownPane;
  }
}