package com.negassagisila.axeereraa;

import java.io.File;

/**
 * The class responsible for the deletion of the notes. It has no constructor and thus
 * does the only thing it's responsible for, i.e deleting files.
 */

class NoteDeleter {
  File file;
  
  public NoteDeleter(File file) {
    this.file = file;
  }
  
  /**
   * The actual method that is responsible for deleting the file. It receives the file
   * to delete as a parameter and proceeds on from there.
   * @param note to be deleted, and it
   * @return a boolean if the operation was successful/un-successful.
   */
  
  static boolean deleteNote(Note note) {
    note = null;
    return true;
  }
  
  void deleteNote() {
    file.delete();
  }

}
