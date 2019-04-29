package com.negassagisila.axeereraa;

import java.awt.*;
import java.io.*;

/**
 *  This class is defines the actual Note object that will be used through out this
 *  application.
 */

public class Note {

    /**
     * this will hold the written text
     */

    private static String writtenText;
    private static NoteColor noteColor;
    private static final NoteColor DEFAULT_NOTE_COLOR = NoteColor.lightYellow;

    private static Note instanceOfNote = new Note(writtenText, noteColor);

    private Note(String writtenText, NoteColor noteColor) {
        Note.writtenText = writtenText;
        Note.noteColor = DEFAULT_NOTE_COLOR;
    }

    static Note newInstance() {
        return instanceOfNote;
    }

    void setNoteColor(NoteColor chosenColor) {
        Note.noteColor = chosenColor;
    }

    void setWrittenText(String insertedText) {
        Note.writtenText = insertedText;
    }

    public static final void setNoteFont(Font font) {
        File file = new File("src/main/resources/font");
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

    String getWrittenText() {
        return writtenText;
    }

    Color getNoteColor() {
        return NoteColor.getTheColorOfTheNote(noteColor);
    }
}
