package com.negassagisila.axeereraa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class NoteSaverTest {
    private NoteSaver dummyNoteSaver;

    private List<Note> exampleNotes = Arrays.asList(
            new Note("some written text"),
            new Note("some other written text", NoteColor.lightGreen)
    );


    @Before
    public void setUp() {
        FileOutputStream dummyFileOutputStream = Mockito.mock(FileOutputStream.class);
        dummyNoteSaver = new NoteSaver(dummyFileOutputStream);
    }

    @After
    public void tearDown() {
        dummyNoteSaver = null;
    }

    @Test
    public void shouldSaveTheNote() {
//        Mockito.when(dummyNoteSaver.save(exampleNotes)).thenReturn(true);

//        Mockito.verify(dummyNoteSaver.save(exampleNotes), Mockito.atLeastOnce());
        Assert.assertTrue(
                "the notes have not been saved",
                dummyNoteSaver.save(exampleNotes));
    }

}