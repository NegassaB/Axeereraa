package com.negassagisila.axeereraa;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

//TODO: run a thread every second (1000ms) to save all the notes currently on display
//TODO: run the GUI on the EDT thread
//TODO: javadoc the shit outta the entire project
//TODO-BIG: start preparing the markdown parser to handle markdown support and add a button to the file menu that says new .md note
public class AxeereraaRunner {
    private final String notesLocation;
    private final String lookAndFeel;
    private static String appHome;
    private static List<Note> notes;
    private NoteReader noteReader;

    public AxeereraaRunner(String notesLocation, String lookAndFeel) {

        this.notesLocation = notesLocation;
        this.lookAndFeel = lookAndFeel;

    }


    public static void main(String[] args) {

        String theSystem = System.getProperty("os.name");
        String theFileSeparator = System.getProperty("file.separator");
        String theUserHome = System.getProperty("user.home");

        String theLookAndFeel = UIManager.getSystemLookAndFeelClassName();

        getAxEnvironment(theSystem, theFileSeparator, theUserHome);

        AxeereraaRunner axRunner = new AxeereraaRunner(appHome, theLookAndFeel);

        /**
         * check if the folder exists or not, and create it if it doesn't exist.
         */

        if (!new File(appHome).exists() || !new File(appHome).isDirectory()) {
            new File(appHome).mkdir();
            try {
                new AxeereraaUI(theLookAndFeel)
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

    private static void displayExistingNotes(AxeereraaRunner runner) {
        try {
            for (Note n : runner.getExistingNotes()) {
                for (int i = 0; i <= runner.getExistingNotes().size(); i++) {
                    new AxeereraaUI(runner.lookAndFeel)
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

    private static void getAxEnvironment(String theSystem, String thePathSeparator, String theUserHome) {
        if (theSystem.startsWith("win")) {
            if (theSystem.contains("xp")) {
                appHome = System.getenv("APPDATA") + "\\.Axeereraa\\";
            } else {
                appHome = theUserHome + thePathSeparator + ".Axeereraa" + thePathSeparator;
            }
        } else {
            appHome = theUserHome + thePathSeparator + ".Axeereraa" + thePathSeparator;
        }
    }

    private List<Note> getExistingNotes() throws FileNotFoundException {

        noteReader = new NoteReader(new FileInputStream(new File(appHome)));

        synchronized (notes) {
            notes = noteReader.load();
        }

        if (notes.isEmpty()) {
            synchronized (notes) {
                notes = noteReader.loadBackup();
            }
        }

        return notes;
    }

    public String getTextAreaText() {
        return null;
    }
}
