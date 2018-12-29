package com.negassagisila.axeereraa;

import org.hamcrest.Matchers;
import org.junit.*;
import org.hamcrest.*;
import org.hamcrest.core.*;
import org.mockito.*;
import org.mockito.Mockito.*;

public class NoteTest {
    private Note dummyNote;

    @Before
    public void setUp() {
        dummyNote = Note.newInstance();
    }

    @Test
    public void allTextShouldBeOfStringType() {
        dummyNote.setWrittenText("some bullshit");
        Assert.assertThat(
                "the written text is not a String",dummyNote.getWrittenText(),
                Matchers.isA(String.class)
        );
    }

    @After
    public void tearDown() {
        dummyNote = null;
    }

}
