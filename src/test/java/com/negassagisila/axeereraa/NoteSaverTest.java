package com.negassagisila.axeereraa;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteSaverTest {

    private NoteSaver noteSaverDummy;
    private FileOutputStream dummyFileOutputStream;
    private ObjectInputStream objectInputStreamToTest;

    private FileOutputStream dummyFileOutputStream2;
    private ObjectOutputStream objectOutputStream2;
    private ObjectInputStream objectInputStreamWithValue;

    @Rule
    public TemporaryFolder tempFolderToTest = new TemporaryFolder();

    @Rule
    public TemporaryFolder tempFolderWithValue = new TemporaryFolder();

    private File tempFileToTest;
    private File tempFileWithValue;


    private static List<Note> exampleNotes = new ArrayList<>();
    static {
        exampleNotes.add(new Note("some written text"));
        exampleNotes.add(new Note("some other written text", NoteColor.lightGreen));
    }

    private FileInputStream fileInputStreamToTest;
    private FileInputStream fileInputStreamWithValue;

    /**
     * This method sets up & initializes the necessary components that will be used
     * in the test. Should any object that is related to file operation fail to initialize
     * it @throws IOException.
     */

    @Before
    public void setUp() throws IOException {
        tempFileToTest = tempFolderToTest.newFile("someBullshit.ser");

        tempFileWithValue = tempFolderWithValue.newFile("anotherBullshit.ser");

        dummyFileOutputStream = new FileOutputStream(tempFileToTest);
        noteSaverDummy = new NoteSaver(dummyFileOutputStream);


        dummyFileOutputStream2 = new FileOutputStream(tempFileWithValue);
        objectOutputStream2 = new ObjectOutputStream(dummyFileOutputStream2);

        fileInputStreamToTest = new FileInputStream(tempFileToTest);

        fileInputStreamWithValue = new FileInputStream(tempFileWithValue);

    }

    @After
    public void tearDown() throws IOException {
        dummyFileOutputStream.flush();
        dummyFileOutputStream.close();
        dummyFileOutputStream2.flush();
        dummyFileOutputStream2.close();

        noteSaverDummy = null;

        tempFileToTest = null;
        tempFileWithValue = null;

        objectOutputStream2.flush();
        objectOutputStream2.close();

    }

    /**
     * This method is responsible for taking the @param objectInputStreamToTest of the two
     * different locations to deserialize and @return the Note object. Should any of
     * the operations fail it @throws IOException & @throws ClassNotFoundException
     */
    private Note readNote(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (Note) objectInputStream.readObject();
    }

    @Test
    public void shouldSaveTheNote() throws IOException, ClassNotFoundException {
        //given
        noteSaverDummy.save(exampleNotes.get(0));
        objectOutputStream2.writeObject(exampleNotes.get(0));

        //when
        objectInputStreamToTest = new ObjectInputStream(fileInputStreamToTest);
        objectInputStreamWithValue = new ObjectInputStream(fileInputStreamWithValue);

        Note realNote = readNote(objectInputStreamToTest);
        Note dummyNote = readNote(objectInputStreamWithValue);

        objectInputStreamToTest.close();
        objectInputStreamWithValue.close();

        //then
        Assert.assertEquals(
                "the notes have not been saved",
                dummyNote,
                realNote
        );

    }

    @Test
    public void shouldSaveTheBackupNote() throws IOException, ClassNotFoundException {
        //given
        File bakTempFileToTest = tempFolderToTest.newFile("someBullshit.ser.bak");
        File bakTempFileWithValue = tempFolderWithValue.newFile("someBullshit.ser.bak");

        dummyFileOutputStream2 = new FileOutputStream(bakTempFileWithValue);
        dummyFileOutputStream = new FileOutputStream(bakTempFileToTest);

        objectOutputStream2 = new ObjectOutputStream(dummyFileOutputStream2);
        noteSaverDummy = new NoteSaver(dummyFileOutputStream);

        objectOutputStream2.writeObject(exampleNotes.get(0));
        noteSaverDummy.save(exampleNotes.get(0));

        fileInputStreamToTest = new FileInputStream(bakTempFileToTest);
        fileInputStreamWithValue = new FileInputStream(bakTempFileWithValue);

        objectInputStreamToTest = new ObjectInputStream(fileInputStreamToTest);
        objectInputStreamWithValue = new ObjectInputStream(fileInputStreamWithValue);

        //when
        Note bakNote = readNote(objectInputStreamToTest);
        Note bakNoteWithValue = readNote(objectInputStreamWithValue);

        objectInputStreamToTest.close();
        objectInputStreamWithValue.close();

        //then
        Assert.assertEquals(
                "the backup notes have not been saved",
                bakNote,
                bakNoteWithValue
        );

    }
}
