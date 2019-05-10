package com.negassagisila.axeereraa;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import javax.swing.*;
import java.util.Collections;

/**
 *
 */

class DisplayMarkdown {
  
  /**
   * This method is responsible for changing the written raw text into Markdown. It instantiates
   * a new MutableDataSet() that will hold a DataKey, and it's value that would properly parse
   * task list items when the Parser & HtmlRenderer objects are built with it.
   * @param writtenMarkdownText markdown document to be parsed by
   * com.vladsch.flexmark.parser.Parser.parse() method
   * @return a String representation of the written markdown text
   */
  static String convertToMarkdown(String writtenMarkdownText) {
    MutableDataHolder option = new MutableDataSet();
    option.set(Parser.EXTENSIONS, Collections.singletonList(TaskListExtension.create()));
    
    Parser parser = Parser.builder(option).build();
    Node document = parser.parse(writtenMarkdownText);
    HtmlRenderer renderer = HtmlRenderer.builder(option).build();
    return renderer.render(document);
  }
  
  /**
   * This method is responsible for instantiating a new JEditorPane that contains the markdown
   * text that will be shown to the user.
   * @param writtenMarkDownText markdown text that will be parsed by DisplayMarkdown.convertToMarkdown()
   * @return a JEditorPane that will be displayed
   */
  JEditorPane displayMarkdown(String writtenMarkDownText) {
    JEditorPane markdownPane = new JEditorPane("text/html", DisplayMarkdown.convertToMarkdown(writtenMarkDownText));
    markdownPane.setEditable(true);
    return markdownPane;
  }
}