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
  
  public String getLookAndFeel() {
      return lookAndFeel;
  }

  public String getAppHome() {
      return appHome;
  }

  public AxeereraaRunner(String appHome, String lookAndFeel) {
    this.appHome = appHome;
    this.lookAndFeel = lookAndFeel;
    setNoteFont();
  }
  
  /**
   *
   * @param args
   */
  public static void main(String[] args) {
    //TODO: create and instantiate a new concrete Note object for every UI instance
    theSystem = System.getProperty("os.name");
    theFileSeparator = System.getProperty("file.separator");
    theUserHome = System.getProperty("user.home");

    String theLookAndFeel = UIManager.getSystemLookAndFeelClassName();

    String theAppHome = getAxEnvironment(getTheSystem(), getTheFileSeparator(), getTheUserHome());

    AxeereraaRunner axRunner = new AxeereraaRunner(theAppHome, theLookAndFeel);
  
    AxeereraaRunner.setNoteFont();
    
    try {
      axUI = new AxeereraaUI(axRunner);
    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
  
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
      axUI.setNote(new Note("")).showAx();
      
      /**
       * used to run the scheduleExecutorService that saves  the notes.
       */
      runScheduleExecutorService(axUI);
    
    } else {
      displayExistingNotes(axRunner, theFileSeparator);
    }

  }
  
  /**
   * This method is responsible for collecting the list of Note object and
   * calling the saveNote() method.
   */
  private static void saveTheNotes() {
    synchronized (notes) {
      for (Note n : notes) {
        AxeereraaRunner.saveNote(n);
      }
    }
  }
  
  /**
   * used to save a single Note instance
   */
  static void saveNote(Note n) {
    try {
      final String noteName = "Axeereraa".concat(String.valueOf(n.hashCode())).concat(".ser");
      new NoteSaver(new FileOutputStream(APP_HOME_FILE + "/" + noteName)).save(n);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This method is used to display the pre-existing notes that were already saved.
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
   * @param instanceUI the running object of the AxeereraaUI class
   */
  private static void runScheduleExecutorService(AxeereraaUI instanceUI) {
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      synchronized (notes) {
        instanceUI.getNotes(notes);
        deleteTheNotes();
        saveTheNotes();
      }
    }, 10, 1, TimeUnit.SECONDS);
  }
  
  /**
   *
   */
  private static void deleteTheNotes() {
    File savedNotesLocation = new File(APP_HOME_FILE + theFileSeparator);
  for (File f : savedNotesLocation.listFiles()) {
    AxeereraaRunner.deleteNote(f);
  }
  }
  
  /**
   * This method is used to delete a single Note file
   * @param file Note file to be deleted.
   */
  private static void deleteNote(File file) {
    new NoteDeleter(file).deleteNote();
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
    List<Note> output;
    for (File f: savedNotesLocation.listFiles()) {
      NoteReader noteReader = new NoteReader(new FileInputStream(f));
      synchronized (notes) {
        //todo: this continuously overwrites the list of Note objects find a way to populate it instead
        //notes = noteReader.load();
        try {
          notes.add(noteReader.read());
        } catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
        }
        /*if (notes.isEmpty()) {
          notes = noteReader.loadBackup();
        }*/
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
