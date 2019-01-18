package com.negassagisila.axeereraa;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for deserializing the notes that were saved.
 * It takes a FileInputStream as a dependency and from the file inside that
 * stream takes and deserializes the Note objects.
 */

class NoteReader {
    // for no reason at all, delete after you've finished
    private final FileInputStream fileInputStream;

    NoteReader(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    /**
     * This is the method responsible for deserializing the file from
     * the FileInputStream.
     * It @return a List<Note> to be used later on.
     * It @throws IOException & @throws ClassNotFoundException for the
     * operations of FileInputStream and ObjectInputStream
     */

    List<Note> load() throws IOException {
        checkFile();
        ObjectInputStream objectInputStream = null;
        List<Note> output = new ArrayList<>();

        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (fileInputStream.available() != 0) {
                Note n = (Note) objectInputStream.readObject();
                output.add(n);
            }
        } catch (ClassNotFoundException | IOException e) {
            fileInputStream.close();
            assert objectInputStream != null;
            objectInputStream.close();

            e.printStackTrace();
        }

        return output;
    }

    /**
     * This method checks the validity of the FileInputStream and
     * it @throws IOException if it's invalid.
     */

    private void checkFile() throws IOException {
        if (fileInputStream.getChannel().size() == 0 ||
                !(fileInputStream.getFD().valid())) {
            throw new IOException();
        }

    }
}
