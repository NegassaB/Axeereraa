package com.negassagisila.axeereraa;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteReaderTest {
    private NoteReader noteReaderDummy;

    private ObjectOutputStream primaryObjectOutputStream;

    private ObjectOutputStream backupObjectOutputStream;

    @Rule
    public TemporaryFolder temporaryFolderValue = new TemporaryFolder();

    @Rule
    public TemporaryFolder temporaryFolderBackUp = new TemporaryFolder();

    private File tempFileWithValue;
    private File tempFileBackUp;

    private static List<Note> inputList = new ArrayList<>();
    static {
        inputList.add(
                new Note("some written text")
        );
        inputList.add(
                new Note("some other written text", NoteColor.lightGreen)
        );
    }

    private static List<Note> anotherList = new ArrayList<>();
    static {
        anotherList.add(
                new Note("qazwsxedcrfv")
        );
        anotherList.add(
                new Note("qwertyuiopasdfghjkl", NoteColor.lightRed)
        );
    }

    @Before
    public void setUp() throws IOException {
        tempFileWithValue = temporaryFolderValue.newFile("someBullshit.ser");
        primaryObjectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFileWithValue));

        for(Note n : inputList) {
            primaryObjectOutputStream.writeObject(n);
        }

        noteReaderDummy = new NoteReader(new FileInputStream(tempFileWithValue));

        tempFileBackUp = temporaryFolderBackUp.newFile("someBullshit.ser.bak");
        backupObjectOutputStream = new ObjectOutputStream(new FileOutputStream(tempFileBackUp));
        for (Note n : inputList) {
            backupObjectOutputStream.writeObject(n);
        }

    }

    @After
    public void tearDown() throws IOException {
        primaryObjectOutputStream.flush();
        primaryObjectOutputStream.close();
        tempFileWithValue = null;
        temporaryFolderValue = null;

        noteReaderDummy = null;

        tempFileBackUp = null;
        temporaryFolderBackUp = null;
        backupObjectOutputStream.flush();
        backupObjectOutputStream.close();

    }

    /**
     * This test method tests the happy path of the NoteReader.load() method.
     * It @throws IOException for any failure
     */

    @Test
    public void shouldLoadTheListOfNotes() {

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
    public void shouldNotLoadAnyListOfNotes() {

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
    public void shouldNotBeNull() {

        //then
        List<Note> output = noteReaderDummy.load();
        Assert.assertNotNull(
                "fuck it's null",
                output
        );
    }

    /**
     * this test method is responsible for checking that the NoteReader.load()
     * method finds and loads the backup file if & when the primary ones are not
     * found. It @throws IOException
     */

    @Test
    public void shouldLoadTheBackupNotes() throws FileNotFoundException {

        //given
        noteReaderDummy.setFileInputStream(new FileInputStream(tempFileBackUp));

        //then
        List<Note> output = noteReaderDummy.loadBackup();

        Assert.assertEquals(
                "It didn't load from the backup file",
                output,
                inputList
        );
    }

}