package com.negassagisila.axeereraa;

import java.io.File;
import java.io.IOException;

public class NoteSerializer {
    private final File inputFile;

    public NoteSerializer(File inputFile) {
        this.inputFile = inputFile;
    }

    public void createSaveFile() {
        if (!inputFile.exists()) {
            try {
                inputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
