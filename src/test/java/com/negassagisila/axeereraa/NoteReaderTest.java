package com.negassagisila.axeereraa;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteReaderTest {
    // for no reason at all, delete after you've finished
    private NoteReader noteReaderDummy;

    private ObjectOutputStream objectOutputStream;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File tempFile;

    private static List<Note> inputList = new ArrayList<>();
    static {
        inputList.add(new Note("some written text"));
        inputList.add(new Note("some other written text", NoteColor.lightGreen));
    }

    private static List<Note> anotherList = new ArrayList<>();
    static {
        anotherList.add(new Note("qazwsxedcrfv"));
        anotherList.add(new Note("qwertyuiopasdfghjkl", NoteColor.lightRed));
    }

    @Before
    public void setUp() throws IOException {
        tempFile = temporaryFolder.newFile("someBullshit.ser");
        objectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFile));

        for(Note n : inputList) {
            objectOutputStream.writeObject(n);
        }

        noteReaderDummy = new NoteReader(new FileInputStream(tempFile));

    }

    @After
    public void tearDown() throws IOException {
        objectOutputStream.flush();
        objectOutputStream.close();
        tempFile = null;
        temporaryFolder = null;
        noteReaderDummy = null;

    }

    /**
     * This test method tests the happy path of the NoteReader.load() method.
     * It @throws IOException for any failure
     */

    @Test
    public void shouldLoadTheListOfNotes() throws IOException {
        //given

        //then
        List<Note> output = noteReaderDummy.load();
        Assert.assertEquals(
                "the notes have not been loaded",
                inputList,
                output
        );

    }

    /**
     * This test method is responsible for confirming that the output of the
     * NoteReader.load() method doesn't stray from the expected one.
     * It @throws IOException for any failure
     */

    @Test
    public void shouldNotLoadAnyListOfNotes() throws IOException {
        //given

        //then
        List<Note> output = noteReaderDummy.load();
        Assert.assertNotEquals(
                "it loaded the wrong fucking list",
                anotherList,
                output
        );

    }

    /**
     * this test method is responsible for checking that the output of
     * the method NoteReader.load() is not null
     * It @throws IOException for any failure
     */

    @Test
    public void shouldNotBeNull() throws IOException {
        //given

        //then
        List<Note> output = noteReaderDummy.load();
        Assert.assertNotNull(
                "fuck it's null",
                output
        );
    }

}