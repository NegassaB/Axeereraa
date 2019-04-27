package com.negassagisila.axeereraa;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//TODO: javadoc the shit outta the entire project
//TODO-BIG: start preparing the markdown parser to handle markdown support and add a button to the file menu that says new .md note
public class AxeereraaRunner {
  private final String lookAndFeel;
  private final String appHome;
  private static List<Note> notes = new ArrayList<>();
  private static File APP_HOME_FILE = null;
  private static AxeereraaUI axUI;
  private static String theSystem;
  
  private static String theFileSeparator;
  private static String theUserHome;
  
  private static String getTheSystem() {
    return theSystem;
  }
  
  private static String getTheFileSeparator() {
    return theFileSeparator;
  }
  
  private static String getTheUserHome() {
    return theUserHome;
  }
  
  String getLookAndFeel() {
      return lookAndFeel;
  }

  private String getAppHome() {
      return appHome;
  }

  public AxeereraaRunner(String appHome, String lookAndFeel) {
    this.appHome = appHome;
    this.lookAndFeel = lookAndFeel;
  }
  
  /**
   *
   * @param args
   */
  public static void main(String[] args) {
    //TODO: create and instantiate a new concrete Note object for every UI instance
    //TODO: perhaps using a builder for the UI instance that will call a factory method for the Note object
    theSystem = System.getProperty("os.name");
    theFileSeparator = System.getProperty("file.separator");
    theUserHome = System.getProperty("user.home");

    String theLookAndFeel = UIManager.getSystemLookAndFeelClassName();

    String theAppHome = getAxEnvironment(getTheSystem(), getTheFileSeparator(), getTheUserHome());

    AxeereraaRunner axRunner = new AxeereraaRunner(theAppHome, theLookAndFeel);
  
//    AxeereraaRunner.setNoteFont();
    
    try {
      axUI = new AxeereraaUI(axRunner);
    } catch (IllegalAccessException |
            InstantiationException |
            ClassNotFoundException |
            UnsupportedLookAndFeelException |
            FontFormatException |
            IOException e) {
      e.printStackTrace();
    }
  
    APP_HOME_FILE = new File(axRunner.getAppHome());
    
    /**
     * checks if the folder exists or not & if it's empty or not, and creates it if it doesn't exist.
     */
    if (!APP_HOME_FILE.exists() || !APP_HOME_FILE.isDirectory()) {
      APP_HOME_FILE.mkdir();
      axUI.setNote(new Note("")).showAx();
    } else {
      displayExistingNotes(axRunner, theFileSeparator);
    }

  }
  
  /**
   * used to save a single Note instance
   */
  
  static void saveNote(Note n) {
    try {
      final String noteName = "Axeereraa".concat(String.valueOf(n.hashCode())).concat(".ser");
      new NoteSaver(new FileOutputStream(APP_HOME_FILE + theFileSeparator + noteName)).save(n);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This method is used to convertToMarkdown the pre-existing notes that were already saved.
   * @param runner the AxeereraaRunner object needed to set it up.
   */
  //TODO: this is not getting and displaying all the saved notes
  private static void displayExistingNotes(AxeereraaRunner runner, String theFileSeparator) {
    List<Note> result;
    try {
      result = runner.getExistingNotes(theFileSeparator);
      for (Note n : result) {
        new AxeereraaUI(runner).setNote(n)
                .showAx();
      }
      if (result.isEmpty()) {
        axUI.setNote(new Note("")).showAx();
      }
    } catch (IllegalAccessException |
            InstantiationException |
            UnsupportedLookAndFeelException |
            ClassNotFoundException |
            FontFormatException |
            IOException e) {
      e.printStackTrace();
    } finally {
      result = null;
    }
  }
  
  /**
   *
   * @param theSystem
   * @param theFileSeparator
   * @param theUserHome
   * @return
   */
  private static String getAxEnvironment(String theSystem, String theFileSeparator, String theUserHome) {
    String output;
    if (theSystem.startsWith("win")) {
      if (theSystem.contains("xp")) {
        output = System.getenv("APPDATA") + "\\.Axeereraa\\";
      } else {
        output = theUserHome + theFileSeparator + ".Axeereraa" + theFileSeparator;
      }
    } else {
      output = theUserHome + theFileSeparator + ".Axeereraa" + theFileSeparator;
    }
    
    return output;
  }

  /**
   *
   * @return
   * @throws FileNotFoundException
   */
  private List<Note> getExistingNotes(String theFileSeparator) throws FileNotFoundException {
    File savedNotesLocation = new File(APP_HOME_FILE + theFileSeparator);
    for (File f: savedNotesLocation.listFiles()) {
      NoteReader noteReader = new NoteReader(new FileInputStream(f));
      synchronized (notes) {
        try {
          notes.add(noteReader.read());
        } catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    
    return notes;
  }
  
  /**
   *
   */
  
  /*private static void setNoteFont() {
    try {
      GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsEnvironment.registerFont(
              Font.createFont(
                      Font.TRUETYPE_FONT,
                      AxeereraaRunner.class.getResourceAsStream(
                              "/font/Roboto-Medium.ttf"
                      )
              )
      );
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }*/
  
  /**
   *
   * @return
   */
  public String getTextAreaText() {
    return null;
  }
}
