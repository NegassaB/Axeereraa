package com.negassagisila.axeereraa;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//TODO: javadoc the shit outta the entire project
//TODO-BIG: start preparing the markdown parser to handle markdown support and add a button to the file menu that says new .md note
public class AxeereraaRunner {
  private final String lookAndFeel;
  private final String appHome;
  private static List<Note> notes = new ArrayList<>();
  private NoteReader noteReader;
  private static File APP_HOME_FILE = null;

  public String getLookAndFeel() {
      return lookAndFeel;
  }

  public String getAppHome() {
      return appHome;
  }

  public AxeereraaRunner(String appHome, String lookAndFeel) {
    this.appHome = appHome;
    this.lookAndFeel = lookAndFeel;
  }


  public static void main(String[] args) throws InstantiationException, UnsupportedLookAndFeelException, IllegalAccessException, ClassNotFoundException, IOException {
    
    String theSystem = System.getProperty("os.name");
    String theFileSeparator = System.getProperty("file.separator");
    String theUserHome = System.getProperty("user.home");

    String theLookAndFeel = UIManager.getSystemLookAndFeelClassName();

    String theAppHome = getAxEnvironment(theSystem, theFileSeparator, theUserHome);

    AxeereraaRunner axRunner = new AxeereraaRunner(theAppHome, theLookAndFeel);
  
    APP_HOME_FILE = new File(axRunner.getAppHome());
    
    /**
     * check if the folder exists or not & if it's empty or not, and create it if it doesn't exist.
     * Immediately save the current note.
     */
    if (!APP_HOME_FILE.exists() || !APP_HOME_FILE.isDirectory()) {
      APP_HOME_FILE.mkdir();
      try {
        //TODO: run this on the ED thread
        new AxeereraaUI(axRunner)
                .setNote(new Note(""))
                .showAx();
      } catch (ClassNotFoundException |
              UnsupportedLookAndFeelException |
              IllegalAccessException |
              InstantiationException e) {
        e.printStackTrace();
      }
      //TODO: run this every 1000ms by using a thread
      synchronized (notes) {
        new AxeereraaUI(axRunner).getNotes(notes);
      }
      saveTheNotes(APP_HOME_FILE, theFileSeparator);
    } else {
      displayExistingNotes(axRunner);
    }

  }
  
  /**
   * This method is used to save the notes, it calls the save() from the NoteSaver class
   * @param file the note location that will be used to construct the FileOutputStream
   */

  
  private static void saveTheNotes(File file, String fileSeparator) {
    FileOutputStream fileOutputStream = null;
    
    try {
      synchronized (notes) {
        for (Note n : notes) {
          fileOutputStream = new FileOutputStream(
                  file  +
                          fileSeparator +
                          ".Axeereraa".concat(String.valueOf(n.hashCode())).concat(".ser"));
          new NoteSaver(fileOutputStream).save(n);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This method is used to display the pre-existing notes that were already saved.
   * @param runner the AxeereraaRunner object needed to set it up.
   */

  private static void displayExistingNotes(AxeereraaRunner runner) {
    List<Note> result;
    try {
      result = runner.getExistingNotes();
      for (Note n : result) {
        new AxeereraaUI(runner).setNote(n)
                .showAx();
      }
    } catch (FileNotFoundException |
            IllegalAccessException |
            InstantiationException |
            UnsupportedLookAndFeelException |
            ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      result = null;
    }
  }
  
  /**
   *
   * @param theSystem
   * @param thePathSeparator
   * @param theUserHome
   * @return
   */
  
  private static String getAxEnvironment(String theSystem, String thePathSeparator, String theUserHome) {
    String output;
    if (theSystem.startsWith("win")) {
      if (theSystem.contains("xp")) {
        output = System.getenv("APPDATA") + "\\.Axeereraa\\";
      } else {
        output = theUserHome + thePathSeparator + ".Axeereraa" + thePathSeparator;
      }
    } else {
      output = theUserHome + thePathSeparator + ".Axeereraa" + thePathSeparator;
    }
    
    return output;
  }

  /**
   *
   * @return
   * @throws FileNotFoundException
   */

  private List<Note> getExistingNotes() throws FileNotFoundException {
    File savedNotesFile = new File(appHome);
    for (File f: savedNotesFile.listFiles()) {
      noteReader = new NoteReader(new FileInputStream(f));
      synchronized (notes) {
        notes = noteReader.load();
        if (notes.isEmpty()) {
          notes = noteReader.loadBackup();
        }
      }
    }
    
    return notes;
  }
  
  /**
   *
   * @return
   */
  
  public String getTextAreaText() {
    return null;
  }
}
