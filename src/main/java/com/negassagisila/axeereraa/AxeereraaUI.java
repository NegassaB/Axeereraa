package com.negassagisila.axeereraa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

//TODO: the next steps:
//TODO: 2. code the right click options with their actionEventListeners

//TODO: 6. code the always on top, minimize & close button actions
//TODO: 7. run & test (currently no other option, sorry)

/**
 * The actual user interface for the Axeereraa application.
 */

public class AxeereraaUI extends JFrame {
  private JPanel axRootPanel;
  private JScrollPane axRootScrollPane;
  private JTextArea axRootTextArea;
  private JMenuBar axMenuBar;
  private AxeereraaRunner axRunner;
  private static int counter;
  private JPopupMenu rightClickOptions;
  
  /**
   * A constructor that runs every time a new Axeereraa note is needed or built
   */
  
  //TODO: find a way to create a new Note object whenever this constructor runs
  //TODO: especially if it's run from a saved file location

  public AxeereraaUI(AxeereraaRunner axRunner) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    
    this.axRunner = axRunner;
    UIManager.setLookAndFeel(axRunner.getLookAndFeel());
    
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    
    add(axRootPanel);
    setSize(300, 250);
    setTitle("Axeereraa");
    
    axRootScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    axRootScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    buildUI();
    setJMenuBar(axMenuBar);
    
    axRootTextArea.addMouseListener(new RightClickOptions());
    
    counter++;
  }

  /**
   * This method is responsible for building the components of the UI like
   * the menu bar, the menu and it's options.
   */

  //TODO: how about adding a Note parameter to this method that builds the UI
  //TODO: it can get the existing notes as it builds the UI or
  //TODO: create a new Note object if there aren't any saved notes
  private void buildUI() {
    axMenuBar = new JMenuBar();
    
    rightClickOptions = new JPopupMenu();
    
    /**
     * as the name suggests this sets up the JMenus and their corresponding JMenuItems
     */
    SetUpMenuAndMenuItems setUpMenuAndMenuItems = new SetUpMenuAndMenuItems().invoke();
    JMenu fileMenu = setUpMenuAndMenuItems.getFileMenu();
    JMenu editMenu = setUpMenuAndMenuItems.getEditMenu();
    JMenu viewMenu = setUpMenuAndMenuItems.getViewMenu();
    JMenu helpMenu = setUpMenuAndMenuItems.getHelpMenu();
  
    /**
     * this adds all the above JMenus to the JMenuBar
     */
    
    axMenuBar.add(fileMenu);
    axMenuBar.add(editMenu);
    axMenuBar.add(viewMenu);
    axMenuBar.add(helpMenu);
  
    axRootTextArea.setLineWrap(true);
    axRootTextArea.setWrapStyleWord(true);
    
    /**
     * as the name suggests this sets up the right click options for the text area.
     */
    
    SetupRightClickOptions setupRightClickOptions = new SetupRightClickOptions().setup();
    JMenuItem selectAllRightClickMenuItem = setupRightClickOptions.getSelectAllRightClickMenuItem();
    JMenuItem copyRightClickMenuItem = setupRightClickOptions.getCopyRightClickMenuItem();
    JMenuItem pasteRightClickMenuItem = setupRightClickOptions.getPasteRightClickMenuItem();
    JMenuItem cutRightClickMenuItem = setupRightClickOptions.getCutRightClickMenuItem();
    JMenuItem previewRightClickMenuItem = setupRightClickOptions.getPreviewRightClickMenuItem();
  
    /**
     * adds all the declared JMenuItems to the right click popup menu.
     */
    
    rightClickOptions.add(selectAllRightClickMenuItem);
    rightClickOptions.add(copyRightClickMenuItem);
    rightClickOptions.add(pasteRightClickMenuItem);
    rightClickOptions.add(cutRightClickMenuItem);
    rightClickOptions.add(previewRightClickMenuItem);
    
    axRootScrollPane.getVerticalScrollBar().setPreferredSize(
            new Dimension(4, Integer.MAX_VALUE));
  }
  
  /**
   * This method is responsible for setting the Note to the UI when the previous Notes are loaded.
   * @param note the Note object that will be set to the UI.
   * @return the UI object that will be displayed.
   */
  
  AxeereraaUI setNote(Note note) {
    setAxRootTextAreaText(note.getWrittenText());
    setAxRootTexAreaColor(note.getNoteColor());
    return this;
  }
  
  /**
   *
   * @param text
   * @return
   */

  private void setAxRootTextAreaText(String text) {
    axRootTextArea.setText(text);
  }
  
  /**
   *
   * @param color
   * @return
   */

  private void setAxRootTexAreaColor(Color color) {
    axRootPanel.setBackground(color);
    axRootTextArea.setBackground(color);
  }
  
  /**
   * This method displays the GUI to the user on the Event Dispatcher Thread (EDT).
   */
  
  void showAx() {
    
    /**
     * This calls the EventQueue.invokeLater() method from the EventQueue class so as to run the
     * AxeereraaUI on the EDT.
     */
    
    EventQueue.invokeLater(() -> {
      buildUI();
      setVisible(true);
      }
    );
}

  /**
   * This method is used to build a Note object from the UI that will be saved.
   */

  void getNotes(List<Note> result) {
    while (counter != 0) {
      result.add(
              new Note(
                      this.axRootTextArea.getText(), getAxRooTextAreaColor(
                              this.axRootTextArea.getBackground()
              )
              )
      );
      counter--;
    }
  }
  
  /**
   * this method gets the note color from the TextArea background and returns it's equivalent
   * to the calling method as a NoteColor enum object.
   * @param axRootTextAreaBackgroundColor contains the color of the TextArea.
   * @return outputNoteColor is the NoteColor enum object.
   */

  private static NoteColor getAxRooTextAreaColor(Color axRootTextAreaBackgroundColor) {
    NoteColor outputNoteColor;
    if (axRootTextAreaBackgroundColor == NoteColor.getTheColorOfTheNote(NoteColor.lightGreen)) {
      outputNoteColor = NoteColor.lightGreen;
    } else if (axRootTextAreaBackgroundColor == NoteColor.getTheColorOfTheNote(NoteColor.lightRed)) {
      outputNoteColor = NoteColor.lightRed;
    } else {
      outputNoteColor = NoteColor.lightYellow;
    }
    return outputNoteColor;
  }

  /**
   * This inner class is responsible for setting up the Menus and their items.
   * the entire text found in the
   * any selected text in the Axeereraa text area.
   */
  
  private class SetUpMenuAndMenuItems {
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu viewMenu;
    private JMenu helpMenu;
    
    JMenu getFileMenu() {
      return fileMenu;
    }
    
    JMenu getEditMenu() {
      return editMenu;
    }
    
    JMenu getViewMenu() {
      return viewMenu;
    }
    
    JMenu getHelpMenu() {
      return helpMenu;
    }
    
    SetUpMenuAndMenuItems invoke() {
      fileMenu = new JMenu("file");
      editMenu = new JMenu("edit");
      viewMenu = new JMenu("view");
      helpMenu = new JMenu("help");
      
      JMenuItem newNoteMenuItem = new JMenuItem("New Note");
      newNoteMenuItem.addActionListener(e -> {
        try {
          new AxeereraaUI(axRunner)
                  .setNote(new Note(""))
                  .showAx();
        } catch (UnsupportedLookAndFeelException |
                IllegalAccessException |
                ClassNotFoundException |
                InstantiationException ex) {
          ex.printStackTrace();
        }
      }
      );
      
      JMenuItem deleteNoteMenuItem = new JMenuItem("Delete Note");
      deleteNoteMenuItem.addActionListener(e -> {
      
      }
      );
      
      JMenuItem selectAllMenuItem = new JMenuItem("select all");
      selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
      selectAllMenuItem.addActionListener(e -> axRootTextArea.selectAll());
      
      JMenuItem cutMenuItem = new JMenuItem("cut");
      cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
      cutMenuItem.addActionListener(e -> axRootTextArea.cut());
      
      JMenuItem copyMenuItem = new JMenuItem("copy");
      copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
      copyMenuItem.addActionListener(e -> axRootTextArea.copy());
      
      JMenuItem pasteMenuItem = new JMenuItem("paste");
      pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
      pasteMenuItem.addActionListener(e -> axRootTextArea.paste());
      
      JMenu wordWrapMenu = new JMenu("word wrap");
      JMenuItem[] wordWrapOptions = new JMenuItem[3];
      wordWrapOptions[0] = new JMenuItem(String.valueOf(70));
      wordWrapOptions[1] = new JMenuItem(String.valueOf(80));
      wordWrapOptions[2] = new JMenuItem(String.valueOf(90));
      
      wordWrapOptions[0].addActionListener(
              e -> axRootTextArea.setText(
                      WordWrap.wrap(axRootTextArea.getText(), 70)
              )
      );
      
      wordWrapOptions[1].addActionListener(
              e -> axRootTextArea.setText(
                      WordWrap.wrap(axRootTextArea.getText(), 80)
              )
      );
      
      wordWrapOptions[2].addActionListener(
              e -> axRootTextArea.setText(
              WordWrap.wrap(axRootTextArea.getText(), 90)
              )
      );
      
      JMenuItem previewMenuItem = new JMenuItem("preview");
      previewMenuItem.addActionListener(e -> {
      //todo: use this to preview the markdown files
      });
      
      for (JMenuItem item : wordWrapOptions) {
        wordWrapMenu.add(item);
      }
      
      JMenuItem aboutMenuItem = new JMenuItem("about");
      aboutMenuItem.addActionListener(e ->
              JOptionPane.showMessageDialog(
                      AxeereraaUI.this,
                      "Axeereraa version 1.0.0\n " +
                              "For more info \ngo to the github repo: " +
                              "\ngithub.com/NegassaB/Axeereraa",
                      "About",
                      JOptionPane.INFORMATION_MESSAGE
              )
      );
      
      JMenuItem contactDeveloperMenuItem = new JMenuItem("contact developer");
      contactDeveloperMenuItem.addActionListener(e ->
              JOptionPane.showMessageDialog(
                      AxeereraaUI.this,
                      "You can reach the developer via\n email or using github.\n"
                              + "negassab16@gmail.com\n and github.com/NegassaB",
                      "Contact Developer",
                      JOptionPane.INFORMATION_MESSAGE
              )
      );
      
      fileMenu.add(newNoteMenuItem);
      fileMenu.add(deleteNoteMenuItem);
      
      editMenu.add(selectAllMenuItem);
      editMenu.add(cutMenuItem);
      editMenu.add(copyMenuItem);
      editMenu.add(pasteMenuItem);
      
      viewMenu.add(wordWrapMenu);
      viewMenu.add(previewMenuItem);
      
      helpMenu.add(aboutMenuItem);
      helpMenu.add(contactDeveloperMenuItem);
      
      return this;
    }
  }
  
  /**
   * Private inner class used to wrap the text.
   */
  
  private static class WordWrap {
    
    /**
     * This method is used to wrap the text inside the textarea in accordance with the user wishes.
     * @param inputLine the textarea text
     * @param lineLength the line length it shall be wrapped at.
     * @return a string object that contains the written text.
     */
    
    static String wrap(String inputLine, int lineLength) {
      StringBuilder accumulator = new StringBuilder();
      int length = inputLine.length();
      int splitPoint = lineLength;
      
      accumulator.append(inputLine, 0, Math.min(length, lineLength));
      
      while (length > splitPoint) {
        accumulator.append("\n");
        accumulator.append(inputLine, splitPoint, Math.min(length, splitPoint + lineLength));
        splitPoint += lineLength;
      }
      
      return new String(accumulator);
    }
  }
  
  /**
   * This class displays the right click options when the axRootTextArea is right clicked.
   */
  
  private class RightClickOptions extends MouseAdapter {
  
    @Override
    public void mousePressed(MouseEvent e) {
      showRightClickOptions(e);
    }
    
    private void showRightClickOptions(MouseEvent e) {
      if (e.isPopupTrigger()) {
        rightClickOptions.show(e.getComponent(), e.getX(), e.getY());
      }
    }
  }
  
  /**
   * This inner class is responsible for setting up all the necessary right click options
   * by using JPopupMenu. It's method @method setup() will conduct the necessary steps and
   * package it in the SetupRightClickOptions instance object.
   */
  
  private class SetupRightClickOptions {
    JMenuItem selectAllRightClickMenuItem;
    JMenuItem copyRightClickMenuItem;
    JMenuItem pasteRightClickMenuItem;
    JMenuItem cutRightClickMenuItem;
    JMenuItem previewRightClickMenuItem;
  
    /**
     * This method is responsible for wiring up the necessary functionality of the JPopupMenu with
     * it's JMenuItems instantiated above. It will set the keyboard accelerators and the
     * ActionListeners for all the MenuItems.
     * @return this running instance of SetupRightClickOptions class
     */
    
    SetupRightClickOptions setup() {
      selectAllRightClickMenuItem = new JMenuItem("select all");
      copyRightClickMenuItem = new JMenuItem("copy");
      pasteRightClickMenuItem = new JMenuItem("paste");
      cutRightClickMenuItem = new JMenuItem("cut");
      previewRightClickMenuItem = new JMenuItem("preview");
      
      selectAllRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
      selectAllRightClickMenuItem.addActionListener(e -> axRootTextArea.selectAll());
      
      copyRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
      copyRightClickMenuItem.addActionListener(e -> axRootTextArea.copy());
      
      pasteRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
      pasteRightClickMenuItem.addActionListener(e -> axRootTextArea.paste());
      
      cutRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
      cutRightClickMenuItem.addActionListener(e -> axRootTextArea.cut());
      
      previewRightClickMenuItem.addActionListener(e -> {
      
      });
      
      return this;
    }
  
    /**
     * Method used to get the selectAllRightClickMenuItem
     * @return selectAllRightClickMenuItem
     */
    
    JMenuItem getSelectAllRightClickMenuItem() {
      return selectAllRightClickMenuItem;
    }
  
    /**
     * Method used to get the copyRightClickMenuItem
     * @return copyRightClickMenuItem
     */
    
    JMenuItem getCopyRightClickMenuItem() {
      return copyRightClickMenuItem;
    }
  
    /**
     * Method used to get the pasteRightClickMenuItem
     * @return pasteRightClickMenuItem
     */
    
    JMenuItem getPasteRightClickMenuItem() {
      return pasteRightClickMenuItem;
    }
  
    /**
     * Method used to get the cutRightClickMenuItem
     * @return cutRightClickMenuItem
     */
    
    JMenuItem getCutRightClickMenuItem() {
      return cutRightClickMenuItem;
    }
  
    /**
     * Method used to get the previewRightClickMenuItem
     * @return previewRightClickMenuItem
     */
    
    JMenuItem getPreviewRightClickMenuItem() {
      return previewRightClickMenuItem;
    }
  }
}
