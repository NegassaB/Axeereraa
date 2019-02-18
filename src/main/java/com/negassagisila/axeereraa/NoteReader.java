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
    private FileInputStream fileInputStream;

    NoteReader(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    /**
     * Used to set the FileInputStream. It's mainly used for the backup loading operation
     */

    void setFileInputStream(FileInputStream backupFileInputStream) {
        fileInputStream = backupFileInputStream;
    }

    /**
     * This is the method responsible for deserializing the file from
     * the FileInputStream.
     * It @return a List<Note>.
     * It @throws IOException & @throws ClassNotFoundException for the
     * operations of FileInputStream and ObjectInputStream
     */

    List<Note> load(){
        List<Note> output = new ArrayList<>();

        try {
            read(output);
        } catch (ClassNotFoundException | IOException e) {
            loadBackup();
            e.printStackTrace();
        }

        return output;
    }

    /**
     * This method is used to load the backup notes from the file system
     * when the primary file can not be found/loaded/is corrupted.
     * @return a List<Note>.
     * It @throws IOException & @throws ClassNotFoundException for the
     * operations of FileInputStream and ObjectInputStream
     */

    List<Note> loadBackup() {
        List<Note> output = new ArrayList<>();

        try {
            read(output);
        } catch (IOException | ClassNotFoundException e) {
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

    /**
     * This method is responsible for reading and populating a List<Note>
     * to the calling methods. It's only parameter is @param listOfNotes
     * that it will be populate. It @throws IOException & @throws ClassNotFoundException
     */

    private void read(List<Note> listOfNotes) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream;
        checkFile();
        objectInputStream = new ObjectInputStream(fileInputStream);
        while (fileInputStream.available() != 0) {
            Note n = (Note) objectInputStream.readObject();
            listOfNotes.add(n);
        }
        fileInputStream.close();
        objectInputStream.close();
    }
}
