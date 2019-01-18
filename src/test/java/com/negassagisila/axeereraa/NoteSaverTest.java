package com.negassagisila.axeereraa;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This test class is used to test the NoteSaver feature/class of the Axeereraa
 * application. Since I couldn't think of a better way to test if it works or not
 * I instantiated two different ObjectOutputStream from two different FileOutputStreams.
 * Each wrote to their respective files found in their respective TemporaryFolders
 * Then I used an ObjectInputStream reader to the read each object and compare against
 * each other.
 */

public class NoteSaverTest {
    private NoteSaver noteSaverDummy;
    private FileOutputStream dummyFileOutputStream;
    private ObjectInputStream objectInputStream;

    private FileOutputStream dummyFileOutputStream2;
    private ObjectOutputStream objectOutputStream2;
    private ObjectInputStream objectInputStream2;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder anotherTempFolder = new TemporaryFolder();

    private File tempFile;
    private File anotherTempFile;

    /**
     * The List<Note> that will be serialized and deserialized
     */

    private static List<Note> exampleNotes = new ArrayList<>();
    static {
        exampleNotes.add(new Note("some written text"));
        exampleNotes.add(new Note("some other written text", NoteColor.lightGreen));
    }

    private FileInputStream fileInputStream;
    private FileInputStream fileInputStream2;

    /**
     * This method sets up & initializes the necessary components that will be used
     * in the test. Should any object that is related to file operation fail to initialize
     * it @throws IOException.
     */

    @Before
    public void setUp() throws IOException {
        tempFile = tempFolder.newFile("someBullshit.ser");
        anotherTempFile = anotherTempFolder.newFile("anotherBullshit.ser");

        dummyFileOutputStream = new FileOutputStream(tempFile);
        noteSaverDummy = new NoteSaver(dummyFileOutputStream);


        dummyFileOutputStream2 = new FileOutputStream(anotherTempFile);
        objectOutputStream2 = new ObjectOutputStream(dummyFileOutputStream2);

        fileInputStream = new FileInputStream(tempFile);

        fileInputStream2 = new FileInputStream(anotherTempFile);

    }

    /**
     * This method is responsible for deconstructing and nulling the components
     * used for testing. Should the file operations fail it @throws IOException
     */

    @After
    public void tearDown() throws IOException {
        dummyFileOutputStream.flush();
        dummyFileOutputStream.close();
        dummyFileOutputStream2.flush();
        dummyFileOutputStream2.close();

        noteSaverDummy = null;

        tempFile = null;
        anotherTempFile = null;

        objectOutputStream2.flush();
        objectOutputStream2.close();

        objectInputStream.close();
        objectInputStream2.close();

    }

    /**
     * This test method is responsible for testing the happy path of our
     * NoteSave class's serialization feature.As stated above it serializes
     * the same file to two different locations and gives it to the @method readNote.
     * After that it compares them against each other in an @method assertEquals
     * statement. Should any of the operations fail it @throws IOException
     * & @throws ClassNotFoundException
     */

    @Test
    public void shouldSaveTheNote() throws IOException, ClassNotFoundException {
        //given
        noteSaverDummy.save(exampleNotes.get(0));
        objectOutputStream2.writeObject(exampleNotes.get(0));

        //when
        objectInputStream = new ObjectInputStream(fileInputStream);
        objectInputStream2 = new ObjectInputStream(fileInputStream2);

        Note realNote = readNote(objectInputStream);
        Note dummyNote = readNote(objectInputStream2);
        //then
        Assert.assertEquals(
                "the notes have not been saved",
                dummyNote,
                realNote);
    }

    /**
     * This method is responsible for taking the @param objectInputStream of the two
     * different locations to deserialize and @return the Note object. Should any of
     * the operations fail it @throws IOException & @throws ClassNotFoundException
     */
    private Note readNote(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (Note) objectInputStream.readObject();

    }

}
