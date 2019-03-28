package com.negassagisila.axeereraa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

class NoteSaver {
  private final FileOutputStream fileOutputStream;
  
  NoteSaver(FileOutputStream fileOutputStream) {
    this.fileOutputStream = fileOutputStream;
    }
    
    void save(Note note) {
    ObjectOutputStream outputStream;
    try {
      outputStream = new ObjectOutputStream(fileOutputStream);
      outputStream.writeObject(note);
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        fileOutputStream.flush();
        fileOutputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
