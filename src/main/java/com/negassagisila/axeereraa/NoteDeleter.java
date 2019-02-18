package com.negassagisila.axeereraa;

import java.io.File;

/**
 * The class responsible for the deletion of the notes. It has no constructor and thus
 * does the only thing it's responsible for, i.e deleting files.
 */

class NoteDeleter {

    /**
     * The actual method that is responsible for deleting the file. It receives the file
     * to delete as a parameter and proceeds on from there. @param fileToBeDeleted and it
     * @return a boolean if the operation was successful/un-successful.
     */

    boolean deleteNote(File file) {
        return file.delete();
    }
}
