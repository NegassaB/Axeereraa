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

    public String getWrittenText() {
        return writtenText;
    }

    public void setWrittenText(String writtenText) {
        Note.writtenText = writtenText;
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

    public static Note onlyInstanceOfNote = new Note(writtenText);

    private Note(String writtenText) {
        Note.writtenText = writtenText;
    }

    public static Note newInstance() {
        return onlyInstanceOfNote;
    }
}
