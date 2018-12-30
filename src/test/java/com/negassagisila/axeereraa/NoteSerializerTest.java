package com.negassagisila.axeereraa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileInputStream;

public class NoteSerializerTest {
    private NoteSerializer dummySerializer;
    private String home;

    @Before
    public void setUp() {
        home = "/home/gadd/";
        String dummyFilePath = Mockito.mock(String.class);
        FileInputStream dummyInputStream = Mockito.mock(FileInputStream.class);
        dummySerializer = new NoteSerializer(dummyFilePath, dummyInputStream);
    }

    @Test
    public void shouldGetFileLocation() {
        Assert.assertEquals("File location not found",
                home,
                dummySerializer.getFileLocationPath());

    }

    @Test
    public void shouldMakeAPrimaryFile() {
        String primaryFile = home + "/axeereraaFile" + "*" + ".ser";
        Assert.assertEquals("No primary file found",
                primaryFile,
                dummySerializer.getSaveFile());
    }

    @Test
    public void shouldMakeABackupFile() {
        String backupFile = home + "/axeereraaFile" + "*" + ".ser" + ".bak";
        Assert.assertEquals("No backup file found",
                backupFile,
                dummySerializer.getBackupFile());
    }

    @Test
    public void shouldSerializeFile() {

    }

    @Test
    public void shouldDeserializeFile() {

    }

    @After
    public void tearDown() {
        dummySerializer = null;
    }
}
