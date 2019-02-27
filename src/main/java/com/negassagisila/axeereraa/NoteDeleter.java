package com.negassagisila.axeereraa;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * The class responsible for the deletion of the notes. It has no constructor and thus
 * does the only thing it's responsible for, i.e deleting files.
 */

final class NoteDeleter {

    /**
     * The actual method that is responsible for deleting the Note. It receives the file
     * to delete as a parameter and proceeds on from there.
     * @param noteTobeDeleted and it
     * @return a boolean if the operation was successful/un-successful.
     */

    static boolean deleteNote(@NotNull File noteTobeDeleted) {
        return noteTobeDeleted.delete();
    }
}
