package com.negassagisila.axeereraa;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * This test class is used to test the operations of the NoteDeleter class.
 * It gets the file from the FileInputStream and checks whether the NoteDeleter class
 * has actually deleted it.
 */

public class NoteDeleterTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File fileToTest;
    private File backupFileToTest;

    /**
     * In this we setup the necessary fixtures that are required to test the
     * operation of the NoteDeleter class. We instantiate and put in place
     * a file to test, a backup file to test and an ObjectOutputStream to write to those
     * files the data. It @throws IOException.
     */

    @Before
    public void setUp() throws IOException {

        fileToTest = temporaryFolder.newFile("someBullshit.ser");
        backupFileToTest = temporaryFolder.newFile("someBullshit2.ser");
    }

    /**
     * In this we teardown the fixtures we setup to test. It @throws IOException.
     */

    @After
    public void tearDown() {

        fileToTest = null;
        backupFileToTest = null;
    }

    /**
     * this will the happy path test the deleteNote() method of the NoteDeleter class,
     * on the primary notes.
     */

    @Test
    public void testDeleteNoteMethodOfPrimaryNote() {

        //then
        Assert.assertTrue(
                "the primary file has not been deleted",
                NoteDeleter.deleteNote(fileToTest)
        );
    }

    /**
     * This will test the happy path of the deleteNote() method of the NoteDeleter class,
     * on the backup notes.
     */

    @Test
    public void testDeleteNoteMethodOfBackupNotes() {

        //then
        Assert.assertTrue(
                "the backup file has not been deleted",
                NoteDeleter.deleteNote(backupFileToTest)
        );
    }

    /**
     * This will test that the deleteNote() method of the NoteDeleter class doesn't
     * delete a non-existent file.
     */

    @Test
    public void testDeleteNoteWithNonExistentFile() {

        Assert.assertFalse(
                "it deleted a non existent file",
                NoteDeleter.deleteNote(new File("bullshit"))
        );
    }
}
