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
      dummyNote.setWrittenText("some bullshit");
      Assert.assertThat(
              "the written text is not a String",
              dummyNote.getWrittenText(),
              Matchers.isA(String.class)
        );
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
      dummyNote.setNoteColor(NoteColor.lightGreen);
      Assert.assertEquals(
              "the color of the not",
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

}
