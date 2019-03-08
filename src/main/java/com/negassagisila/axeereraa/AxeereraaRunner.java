package com.negassagisila.axeereraa;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//TODO: run a thread every second (1000ms) to save all the notes currently on display
//TODO: run the GUI on the EDT thread
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

        AxeereraaUI axeereraaUI = new AxeereraaUI(axRunner);

        /**
         * check if the folder exists or not & if it's empty or not, and create it if it doesn't exist.
         * Immediately save the current note.
         */

        APP_HOME_FILE = new File(theAppHome);

        if (!APP_HOME_FILE.exists() || !APP_HOME_FILE.isDirectory()) {
            APP_HOME_FILE.mkdir();
            //TODO: run this as soon as a new instance is created,
            //TODO: ideally you run it with the default parameters &
            //TODO: get the text from the UI
            synchronized (notes) {
                notes.add(axeereraaUI.getNote());
            }
            saveTheNote(APP_HOME_FILE);
            try {
                new AxeereraaUI(axRunner)
                        .showAx();
            } catch (ClassNotFoundException |
                    UnsupportedLookAndFeelException |
                    IllegalAccessException |
                    InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            displayExistingNotes(axRunner);
        }

    }

    /**
     * This method is used to save the notes, it calls the save() from the NoteSaver class
     * @param file the note location that will be used to construct the FileOutputStream
     */

    private static void saveTheNote(File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            synchronized (notes) {
                for (Note n: notes) {
                    new NoteSaver(fileOutputStream).save(n);
                }
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display the pre-existing notes that were already saved.
     * @param runner the AxeereraaRunner object needed to set it up.
     */

    private static void displayExistingNotes(AxeereraaRunner runner) {
        try {
            for (Note n : runner.getExistingNotes()) {
                for (int i = 0; i <= runner.getExistingNotes().size(); i++) {
                    new AxeereraaUI(runner)
                            .setAxRootTexAreaColor(n.getNoteColor())
                            .setAxRootTextAreaText(n.getWrittenText())
                            .showAx();
                }
            }
        } catch (FileNotFoundException |
                IllegalAccessException |
                InstantiationException |
                UnsupportedLookAndFeelException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    private List<Note> getExistingNotes() throws FileNotFoundException {

        noteReader = new NoteReader(new FileInputStream(new File(appHome)));

        synchronized (notes) {
            notes = noteReader.load();
            if (notes.isEmpty()) {
                notes = noteReader.loadBackup();
            }
        }

        return notes;
    }

    public String getTextAreaText() {
        return null;
    }
}
