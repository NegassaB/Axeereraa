package com.negassagisila.axeereraa;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

class NoteSaver {
    //you initialize this from the main method with the save location
    private FileOutputStream fileOutputStream;
    private static final String HOME = System.getProperty("user.home");

    NoteSaver(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    boolean save(List<Note> noteList) {
        boolean operationIndicator = false;

        for (Note n : noteList) {
            try {
                /*fileOutputStream = new FileOutputStream(
                        HOME +
                                File.separator +
                                ".axeereraa" +
                                File.separator +
                                "axeereraa" +
                                LocalDateTime.now().toString() +
                                ".ser"
                );*/
                new ObjectOutputStream(fileOutputStream).writeObject(n);
                operationIndicator = true;
            } catch (IOException iex) {
                throw new RuntimeException(iex);
            }
        }
        return operationIndicator;
    }
}
