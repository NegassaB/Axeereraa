package com.negassagisila.axeereraa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.List;

public class NoteSerializerTest {
    private NoteSerializer dummySerializer;
    private File dummyFile;
    private List<Note> listOfNotes;

    @Before
    public void setUp() {
        listOfNotes = Mockito.mock(List.class);
        dummyFile = Mockito.mock(File.class);
        dummySerializer = new NoteSerializer(dummyFile);
    }

    @After
    public void tearDown() {
        dummyFile = null;
        dummySerializer = null;
    }

    @Test
    public void shouldCreateSaveFile() {
        dummySerializer.createSaveFile();

        Assert.assertTrue(
                "the file to save at, has not been created",
                dummyFile.exists()
        );
    }

    @Test
    public void shouldSaveTheWrittenTextToTheFile() {}
}
