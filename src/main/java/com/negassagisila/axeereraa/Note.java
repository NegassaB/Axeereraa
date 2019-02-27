package com.negassagisila.axeereraa;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 *  This class is defines the actual Note object that will be used through out this
 *  application.
 */

public class Note implements Serializable {

    /**
     * this will hold the written text
     */

    private String writtenText;
    private NoteColor noteColor;
    private static final NoteColor DEFAULT_NOTE_COLOR = NoteColor.lightYellow;

    public Note(String writtenText, NoteColor noteColor) {
        this.writtenText = writtenText;
        this.noteColor = noteColor;
    }

    public Note(String writtenText) {
        this.writtenText = writtenText;
        noteColor = DEFAULT_NOTE_COLOR;
    }

    void setNoteColor(NoteColor chosenColor) {
        noteColor = chosenColor;
    }

    void setWrittenText(String insertedText) {
        writtenText = insertedText;
    }

    public final void setNoteFont() {
        File file = new File("src/main/resources/Roboto-Light.ttf");
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

    @Override
    public int hashCode() {
        return Objects.hashCode(writtenText) + Objects.hashCode(noteColor);
    }

    @Override
    public boolean equals(Object o) {
        boolean output;

        if (o == null) {
            output = false;
        } else if(!(o instanceof Note)) {
            output = false;
        } else if (o == this) {
            output = true;
        } else {
            output = this.hashCode() == o.hashCode();
        }
        return output;
    }

}
