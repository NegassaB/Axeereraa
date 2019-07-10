package com.negassagisila.axeereraa;

import org.hamcrest.Matchers;
import org.junit.*;

public class NoteTest {
    private Note dummyNote;
    private Note anotherDummyNote;
    
    @Before
    public void setUp() {
      dummyNote = new Note("some text");
      anotherDummyNote = new Note("some other text", NoteColor.lightRed);
    }

    @After
    public void tearDown() {
      dummyNote = null;
      anotherDummyNote = null;
    }

    @Test
    public void allTextShouldBeOfStringType() {
      dummyNote.setCurrentlyEditing(true);
      dummyNote.setWrittenText("some bullshit");
      Assert.assertThat(
              "the written text is not a String",
              dummyNote.getWrittenText(),
              Matchers.isA(String.class)
        );
      dummyNote.setCurrentlyEditing(false);
    }

    @Test
    public void shouldHaveADefaultColor() {
      Assert.assertEquals(
              "default color is not set",
              NoteColor.getTheColorOfTheNote(),
              dummyNote.getNoteColor()
      );
    }

    @Test
    public void shouldChangeNoteColor() {
      dummyNote.setCurrentlyEditing(true);
      dummyNote.setNoteColor(NoteColor.lightGreen);
      Assert.assertEquals(
              "the color of the not",
              NoteColor.getTheColorOfTheNote(NoteColor.lightGreen),
              dummyNote.getNoteColor()
      );
      dummyNote.setCurrentlyEditing(false);
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldNotAllowToEditWithoutSettingBooleanValue() {
      dummyNote.setNoteColor(NoteColor.lightRed);
      dummyNote.setWrittenText("Blah");
      Assert.assertNotEquals(
              "the note has not been edited",
              NoteColor.getTheColorOfTheNote(NoteColor.lightGreen),
              dummyNote.getNoteColor()
      );
    }

    @Test
    public void shouldHaveABuiltInColor() {
      Assert.assertEquals(
              "the constructor color has not been set",
              NoteColor.getTheColorOfTheNote(NoteColor.lightRed),
              anotherDummyNote.getNoteColor());
    }
    
    @Test
    public void currentlyEditingShouldBeFalse() {
      Assert.assertFalse("it's not false", dummyNote.getCurrentlyEditing());
    }

}
