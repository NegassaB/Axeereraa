package com.negassagisila.axeereraa;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
*  This class is defines the actual Note object that will be used through out this
*  application.
*/

public class Note implements Serializable {
  
  /**
   * this will hold the written text.
   */

  private String writtenText;
  private NoteColor noteColor;
  private boolean currentlyEditing;
  private float uniqueId;
  private static final NoteColor DEFAULT_NOTE_COLOR = NoteColor.lightYellow;

  public Note(String writtenText, NoteColor noteColor) {
    this.writtenText = writtenText;
    this.noteColor = noteColor;
    currentlyEditing = false;
    uniqueId = (float) (Math.random() * 100.f);
  }

  public Note(String writtenText) {
    this.writtenText = writtenText;
    noteColor = DEFAULT_NOTE_COLOR;
    currentlyEditing = false;
    uniqueId = (float) (Math.random() * 100.f);
  }
  
  void setNoteColor(NoteColor chosenColor) {
    if (currentlyEditing)
      noteColor = chosenColor;
    else {
      throw new RuntimeException("boolean value for editing is not set");
    }
  }

  void setWrittenText(String insertedText) {
    if (currentlyEditing) writtenText = insertedText;
    else {
      throw new RuntimeException("boolean value for editing is not set");
    }
  }
  
  void setCurrentlyEditing(boolean value) {
    currentlyEditing = value;
  }
  
  String getWrittenText() {
      return writtenText;
  }

  Color getNoteColor() {
      return NoteColor.getTheColorOfTheNote(noteColor);
  }
  
  boolean getCurrentlyEditing() {
    return currentlyEditing;
  }
  
  public float getUniqueId() {
    return uniqueId;
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
    } else if (!(o instanceof Note)) {
      output = false;
    } else if (o == this) output = true;
    else {
      output = this.hashCode() == o.hashCode();
    }
    return output;
  }
  
}
