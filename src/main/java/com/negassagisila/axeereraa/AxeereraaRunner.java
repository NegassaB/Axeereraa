package com.negassagisila.axeereraa;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TODO: javadoc the shit outta the entire project
//TODO-BIG: start preparing the markdown parser to handle markdown support and add a button to the file menu that says new .md note
public class AxeereraaRunner {
  private final String lookAndFeel;
  private final String appHome;
  private static List<Note> notes = new ArrayList<>();
  private static File APP_HOME_FILE = null;
  private static ScheduledExecutorService scheduledExecutorService;
  
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
  
  /**
   *
   * @param args
   */
  public static void main(String[] args) {
    //TODO: create and instantiate a new concrete Note object for every UI instance
    String theSystem = System.getProperty("os.name");
    String theFileSeparator = System.getProperty("file.separator");
    String theUserHome = System.getProperty("user.home");

    String theLookAndFeel = UIManager.getSystemLookAndFeelClassName();

    String theAppHome = getAxEnvironment(theSystem, theFileSeparator, theUserHome);

    AxeereraaRunner axRunner = new AxeereraaRunner(theAppHome, theLookAndFeel);
  
    AxeereraaRunner.setNoteFont();
    
    APP_HOME_FILE = new File(axRunner.getAppHome());
  
    /**
     * instantiate the scheduledExecutorService that will handle the timely
     * execution of the @method saveTheNotes() method.
     */
    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    
    /**
     * check if the folder exists or not & if it's empty or not, and create it if it doesn't exist.
     * Immediately save the current note.
     */
    if (!APP_HOME_FILE.exists() || !APP_HOME_FILE.isDirectory()) {
      APP_HOME_FILE.mkdir();
      try {
        new AxeereraaUI(axRunner)
                .setNote(new Note(""))
                .showAx();
      } catch (ClassNotFoundException |
              UnsupportedLookAndFeelException |
              IllegalAccessException |
              InstantiationException e) {
        e.printStackTrace();
      }
  
      /**
       * used to run the scheduleExecutorService that saves  the notes.
       */
      runScheduleExecutorService(axRunner, theFileSeparator);
    
    } else {
      displayExistingNotes(axRunner, theFileSeparator);
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
                          "Axeereraa".concat(String.valueOf(n.hashCode())).concat(".ser"));
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
  private static void displayExistingNotes(AxeereraaRunner runner, String theFileSeparator) {
    List<Note> result;
    try {
      result = runner.getExistingNotes(theFileSeparator);
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
   * method that handles the running of the ScheduledExecutorService instance object.
   * @param runner
   * @param theFileSeparator
   */
  private static void runScheduleExecutorService(AxeereraaRunner runner, String theFileSeparator) {
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      try {
        synchronized (notes) {
          new AxeereraaUI(runner).getNotes(notes);
          saveTheNotes(APP_HOME_FILE, theFileSeparator);
        }
      } catch (IllegalAccessException |
              InstantiationException |
              ClassNotFoundException |
              UnsupportedLookAndFeelException e) {
        e.printStackTrace();
      }
    }, 10, 1, TimeUnit.SECONDS);
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
   */
  private static void setNoteFont() {
    File file = new File("src/main/resources/font/Roboto-Light.ttf");
    try {
      GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
              .getLocalGraphicsEnvironment();
      graphicsEnvironment.registerFont(
              Font.createFont(
                      Font.TRUETYPE_FONT,
                      new FileInputStream(file)
              )
      );
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }
  
  /**
   *
   * @return
   */
  public String getTextAreaText() {
    return null;
  }
}
