package com.negassagisila.axeereraa;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

//TODO: 7. run & test (currently no other option, sorry)

/**
 * The actual user interface for the Axeereraa application.
 */

public class AxeereraaUI extends JFrame {
  private JPanel axRootPanel;
  private JScrollPane axRootScrollPane;
  private JTextArea axRootTextArea;
  private JMenuBar axMenuBar;
  private Axeereraa axRunner;
  private static int COUNTER;
  private JPopupMenu rightClickOptions;
  private Font f = Font.createFont(Font.TRUETYPE_FONT,
          Axeereraa.class.getResourceAsStream("/font/Roboto-Medium.ttf"));
  /**
   * A constructor that runs every time a new Axeereraa note is needed or built
   */
  
  //TODO: find a way to create a new Note object whenever this constructor runs
  //especially if it's run from a saved file location

  public AxeereraaUI(Axeereraa axRunner) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, FontFormatException {
    
    this.axRunner = axRunner;
    UIManager.setLookAndFeel(axRunner.getLookAndFeel());
    
  
    AxeereraaUI.this.setFont(f);
//    axRootPanel.setFont(f);
    
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    
    add(axRootPanel);
    setSize(300, 250);
    setTitle("Axeereraa");
    
    buildUI();
    setJMenuBar(axMenuBar);

    AxeereraaUI.COUNTER++;
  }

  /**
   * This method is responsible for building the components of the UI like
   * the menu bar, the menu and it's options.
   */

  //TODO: how about adding a Note parameter to this method that builds the UI
  //it can get the existing notes as it builds the UI or
  //create a new Note object if there aren't any saved notes
  private void buildUI() throws IOException {
    axMenuBar = new JMenuBar();
    
    rightClickOptions = new JPopupMenu();
  
    axRootScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    axRootScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    axRootTextArea.addMouseListener(new RightClickOptions());
    
    axRootScrollPane.getVerticalScrollBar().setPreferredSize(
            new Dimension(3, Integer.MAX_VALUE));
    
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
    JMenuItem markdownOption = setupRightClickOptions.getMarkdownOption();
    JMenu changeNoteColorMenu = setupRightClickOptions.getChangeNoteColorMenu();
  
    /**
     * adds all the declared JMenuItems to the right click popup menu.
     */
    
    rightClickOptions.add(selectAllRightClickMenuItem);
    rightClickOptions.add(copyRightClickMenuItem);
    rightClickOptions.add(pasteRightClickMenuItem);
    rightClickOptions.add(cutRightClickMenuItem);
    rightClickOptions.add(markdownOption);
    rightClickOptions.add(changeNoteColorMenu);
    
    AxeereraaUI.this.setIconImage(
            ImageIO.read(
                    this.getClass().getResource(
                            "/images/icon.png"
                    )
            )
    );
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
    //axRootPanel.setBackground(color);
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
      try {
        buildUI();
        setLocationByPlatform(true);
        setVisible(true);
      } catch (IOException e) {
        e.printStackTrace();
      }
      }
    );
}

  /**
   * This method is used to get a single instance of the Note object from the UI
   * @return new Note(written text, NoteColor)
   */
  
  private Note getNote() {
    return new Note(this.axRootTextArea.getText(), this.getAxRooTextAreaColor(this.axRootTextArea.getBackground()));
  }
  
  /**
   * this method gets the note color from the TextArea background and returns it's equivalent
   * to the calling method as a NoteColor enum object.
   * @param axRootTextAreaBackgroundColor contains the color of the TextArea.
   * @return outputNoteColor is the NoteColor enum object.
   */

  private NoteColor getAxRooTextAreaColor(Color axRootTextAreaBackgroundColor) {
    NoteColor outputNoteColor;
    if (axRootTextAreaBackgroundColor.equals(NoteColor.getTheColorOfTheNote(NoteColor.lightGreen))) {
      outputNoteColor = NoteColor.lightGreen;
    } else if (axRootTextAreaBackgroundColor.equals(NoteColor.getTheColorOfTheNote(NoteColor.lightRed))) {
      outputNoteColor = NoteColor.lightRed;
    } else {
      outputNoteColor = NoteColor.lightYellow;
    }
    return outputNoteColor;
  }
  
  /**
   * This method is responsible for setting the application always on top
   * @param status boolean value to be passed to the instance of the UI.
   */
  
  private void stayOnTop(boolean status) {

    /**
     * called on every instance of the UI, method from the JFrame class.
     */
    
    this.setAlwaysOnTop(status);
  
    /**
     * changes the icon to lock to show that the result
     */
    
    displayLockIcon(status);
  }
  
  /**
   * changes the icon to lock to show that the result
   * @param status boolean value of that checks if the always on top has been set
   */
  
  private void displayLockIcon(boolean status) {
    //todo: find a way to display the lock.png image on the axRootPanel or axRootTextArea
    if (status) {
      try {
        Image lockIcon = ImageIO.read(this.getClass().getResource("/images/lock.png"));
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * This method is responsible for removing the deleted note from the UI
   */
  
  private void removeNote() {
    this.setVisible(false);
    AxeereraaUI.COUNTER--;
    //TODO: delete the Note file
    if (AxeereraaUI.COUNTER == 0) {
      System.exit(0);
    }
    //NoteDeleter.deleteNote();
  }
  
  /**
   * This method is responsible for displaying the markdown containing JEditorPane.
   * It calls the remove() method from the root scroll pane to remove the currently displayed
   * axRootTextArea and instead calls the add() method to insert the jEditorPane.
   * @param jEditorPane the editor pane that contains the markdown that will be displayed
   */
  private void showMarkdown(JEditorPane jEditorPane) {
    jEditorPane.addMouseListener(new RightClickOptions());
    this.axRootScrollPane.getViewport().remove(axRootTextArea);
    this.axRootScrollPane.getViewport().add(jEditorPane);
  }
  
  /**
   * This method is responsible for showing the raw text instead of the markdown.
   */
  private void showRawText() {
    this.axRootScrollPane.getViewport().add(axRootTextArea);
  }
  
  /**
   * This method creates and displays a JDialog that
   * contains the necessary info about the application.
   * @param titleOfDialog that will be passed to the JDialog setTitle() method.
   */
  private void displayDialog(String titleOfDialog) {
    String messageText = null;
    if (titleOfDialog.equals("About")) {
      messageText = "Axeereraa version 1.0.0\n" +
              "For more info \ngo to the github repo:\n" +
              "github.com/NegassaB/Axeereraa";
    } else if(titleOfDialog.equals("Contact")){
      messageText = "You can reach the developer via\n" +
              "email or using github.\n" +
              "negassab16@gmail.com\n" +
              "and github.com/NegassaB";
    }
    
    JOptionPane.showMessageDialog(
            AxeereraaUI.this,
            messageText,
            titleOfDialog,
            JOptionPane.INFORMATION_MESSAGE
    );
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
      
      JMenuItem plainNoteMenuItem = new JMenuItem("New Note");
      
      plainNoteMenuItem.addActionListener(e -> {
        try {
          new AxeereraaUI(axRunner)
                  .setNote(new Note(""))
                  .showAx();
        } catch (UnsupportedLookAndFeelException |
                IllegalAccessException |
                ClassNotFoundException |
                InstantiationException |
                FontFormatException |
                IOException ex) {
          ex.printStackTrace();
        }
              }
      );
      
      JMenuItem deleteNoteMenuItem = new JMenuItem("Delete Note");
      deleteNoteMenuItem.addActionListener(e -> removeNote()
      );
      
      JMenuItem saveNoteMenuItem = new JMenuItem("save");
      saveNoteMenuItem.addActionListener(e -> Axeereraa.saveNote(AxeereraaUI.this.getNote()));
      
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
      
      JMenu previewMenu = new JMenu("preview");
      JMenuItem[] previewMenuItems = new JMenuItem[2];
      previewMenuItems[0] = new JMenuItem("show markdown");
      previewMenuItems[0].addActionListener(e -> showMarkdown(DisplayMarkdown.displayMarkdown(axRootTextArea.getText())));
      previewMenuItems[1] = new JMenuItem("show raw text");
      previewMenuItems[1].addActionListener(e -> showRawText());
      
      for (JMenuItem m : previewMenuItems) {
        previewMenu.add(m);
      }
      
      JMenu stayOnTopMenu = new JMenu("stay on top");
      JMenuItem alwaysOnTopItem = new JMenuItem("Always");
      JMenuItem neverOnTopItem = new JMenuItem("Never");
      
      alwaysOnTopItem.addActionListener(e -> stayOnTop(true));
      neverOnTopItem.addActionListener(e -> stayOnTop(false));
      
      stayOnTopMenu.add(alwaysOnTopItem);
      stayOnTopMenu.add(neverOnTopItem);
      
      JMenuItem aboutMenuItem = new JMenuItem("about");
      aboutMenuItem.addActionListener(e -> displayDialog("About"));
      
      JMenuItem contactDeveloperMenuItem = new JMenuItem("contact developer");
      contactDeveloperMenuItem.addActionListener(e -> displayDialog("Contact"));
      
      fileMenu.add(plainNoteMenuItem);
      fileMenu.add(deleteNoteMenuItem);
      fileMenu.add(saveNoteMenuItem);
      
      editMenu.add(selectAllMenuItem);
      editMenu.add(cutMenuItem);
      editMenu.add(copyMenuItem);
      editMenu.add(pasteMenuItem);
      
      viewMenu.add(previewMenu);
      viewMenu.add(stayOnTopMenu);
      
      helpMenu.add(aboutMenuItem);
      helpMenu.add(contactDeveloperMenuItem);
      
      return this;
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
    JMenu markdownOption;
    JMenu changeNoteColorMenu;
  
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
      markdownOption = new JMenu("preview");
      changeNoteColorMenu = new JMenu("change Color");
      
      selectAllRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
      selectAllRightClickMenuItem.addActionListener(e -> axRootTextArea.selectAll());
      
      copyRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
      copyRightClickMenuItem.addActionListener(e -> axRootTextArea.copy());
      
      pasteRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
      pasteRightClickMenuItem.addActionListener(e -> axRootTextArea.paste());
      
      cutRightClickMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
      cutRightClickMenuItem.addActionListener(e -> axRootTextArea.cut());
      
      JMenuItem[] noteColorMenuItems = new JMenuItem[3];
      noteColorMenuItems[0] = new JMenuItem("light green");
      noteColorMenuItems[1] = new JMenuItem("light yellow");
      noteColorMenuItems[2] = new JMenuItem("light red");
      
      noteColorMenuItems[0].addActionListener(e -> axRootTextArea.setBackground(
              NoteColor.getTheColorOfTheNote(NoteColor.lightGreen)
      ));
      
      noteColorMenuItems[1].addActionListener(e -> axRootTextArea.setBackground(
              NoteColor.getTheColorOfTheNote(NoteColor.lightYellow)
      ));
      
      noteColorMenuItems[2].addActionListener(e -> axRootTextArea.setBackground(
              NoteColor.getTheColorOfTheNote(NoteColor.lightRed)
      ));
      
      for (JMenuItem m : noteColorMenuItems) {
        changeNoteColorMenu.add(m);
      }
      
      JMenuItem[] markdownOptions = new JMenuItem[2];
      markdownOptions[0] = new JMenuItem("show markdown");
      markdownOptions[0].addActionListener(e -> showMarkdown(DisplayMarkdown.displayMarkdown(axRootTextArea.getText())));
      markdownOptions[1] = new JMenuItem("back to raw text");
      markdownOptions[1].addActionListener(e -> showRawText());
      
      for (JMenuItem m : markdownOptions) {
        markdownOption.add(m);
      }
      
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
     * Method used to get the markdownOption
     * @return markdownOption
     */
    
    JMenuItem getMarkdownOption() {
      return markdownOption;
    }
  
    /**
     * Method used to get the changeNoteColorMenu
     * @return changeNoteColorMenu
     */
    JMenu getChangeNoteColorMenu() {
      return changeNoteColorMenu;
    }
  }
}