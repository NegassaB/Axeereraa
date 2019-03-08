package com.negassagisila.axeereraa;

import javax.swing.*;
import java.awt.*;

//TODO: the next steps:
//TODO: 1. code all the necessary actionEventListeners for the menu & it's options
//TODO: 2. code the right click options with their actionEventListeners
//TODO: 3. code the keyboard shortcut options with their actionEventListeners
//TODO: 4. code the setColor method for the axRootPanel, color from the Note object
//TODO: 5. code the word wrap inner class
//TODO: 6. code the always on top, minimize & close button actions
//TODO: 7. run & test (currently no other option, sorry)

/**
 * The actual user interface for the Axeereraa application.
 */

public class AxeereraaUI extends JFrame {
    private JPanel axRootPanel;
    private JScrollPane axRootScrollPane;
    private JTextArea axRootTextArea;
    private JMenuBar axMenuBar;

    /**
     * A constructor that runs every time a new Axeereraa note is needed or built
     */

    //TODO: find a way to create a new Note object whenever this constructor runs
    //TODO: especially if it's run from a saved file location

    public AxeereraaUI(AxeereraaRunner axRunner) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(axRunner.getLookAndFeel());

        add(axRootPanel);
        setSize(300, 250);
        setTitle("Axeereraa");

        buildUI();
        setJMenuBar(axMenuBar);

    }

    //TODO: find a way to create a new Note object whenever this constructor runs
    //TODO: especially if it's run from a saved file location

    public AxeereraaUI() throws UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(UIManager.getLookAndFeel());

        add(axRootPanel);
        setSize(300, 250);
        setTitle("Axeereraa");

        buildUI();
        setJMenuBar(axMenuBar);
    }
    /**
     * This method is responsible for building the components of the UI like
     * the menu bar, the menu and it's options.
     */

    //TODO: how about adding a Note parameter to this method that builds the UI
    //TODO: it can get the existing notes as it builds the UI or
    //TODO: create a new Note object if there aren't any saved notes
    private void buildUI() {
        axMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("file");
        JMenu editMenu = new JMenu("edit");
        JMenu viewMenu = new JMenu("view");
        JMenu helpMenu = new JMenu("help");

        JMenuItem newNoteMenuItem = new JMenuItem("New Note");
        newNoteMenuItem.addActionListener(e -> {
            try {
                new AxeereraaUI().showAx();
            } catch (UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
        });

        JMenuItem deleteNoteMenuItem = new JMenuItem("Delete Note");
        deleteNoteMenuItem.addActionListener(e -> {

        });

        JMenuItem boldNoteMenuItem = new JMenuItem("bold | ctrl+b");
        JMenuItem italicMenuItem = new JMenuItem("italic | ctrl+i");
        JMenuItem underlineMenuItem = new JMenuItem("underline | ctrl+u");
        JMenuItem selectAllMenuItem = new JMenuItem("select all | ctrl+a");
        JMenuItem cutMenuItem = new JMenuItem("cut | ctrl+x");
        JMenuItem copyMenuItem = new JMenuItem("copy | ctrl+c");
        JMenuItem pasteMenuItem = new JMenuItem("paste | ctrl+v");

        JMenuItem alignmentMenuItem = new JMenuItem("alignment");
        alignmentMenuItem.addActionListener(e -> {

        });

        JMenuItem wordWrapMenuItem = new JMenuItem("word wrap");
        wordWrapMenuItem.addActionListener(e -> {

        });

        JMenuItem aboutMenuItem = new JMenuItem("about");
        aboutMenuItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Axeereraa version 1.0.0\n " +
                                "For more info \ngo to the github repo: " +
                                "\ngithub.com/NegassaB/Axeereraa",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        JMenuItem contactDeveloperMenuItem = new JMenuItem("contact developer");
        contactDeveloperMenuItem.addActionListener(e ->
            JOptionPane.showMessageDialog(
                    AxeereraaUI.this,
                    "You can reach the developer via\n email or using github.\n"
                    + "negassab16@gmail.com\n and github.com/NegassaB",
                    "Contact Developer",
                    JOptionPane.INFORMATION_MESSAGE
            )
        );

        fileMenu.add(newNoteMenuItem);
        fileMenu.add(deleteNoteMenuItem);

        editMenu.add(boldNoteMenuItem);
        editMenu.add(italicMenuItem);
        editMenu.add(underlineMenuItem);
        editMenu.add(selectAllMenuItem);
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        viewMenu.add(alignmentMenuItem);
        viewMenu.add(wordWrapMenuItem);

        helpMenu.add(aboutMenuItem);
        helpMenu.add(contactDeveloperMenuItem);

        axMenuBar.add(fileMenu);
        axMenuBar.add(editMenu);
        axMenuBar.add(viewMenu);
        axMenuBar.add(helpMenu);

    }

    AxeereraaUI setAxRootTextAreaText(String text) {

        axRootTextArea.setText(text);
        return this;

    }

    AxeereraaUI setAxRootTexAreaColor(Color color) {

        axRootTextArea.setBackground(color);
        return this;

    }

    void showAx() {

        buildUI();
        setVisible(true);
    }

    /**
     * This method is used to build a Note object from the UI that will be saved.
     * @return Note object
     */

    Note getNote() {
        return new Note(axRootTextArea.getText(), getAxRooTextAreaColor(axRootTextArea.getBackground()));
    }

    /**
     * this method gets the note color from the TextArea background and returns it's equivalent
     * to the calling method as a NoteColor enum object.
     * @param axRootTextAreaBackgroundColor contains the color of the TextArea.
     * @return outputNoteColor is the NoteColor enum object.
     */

    private static NoteColor getAxRooTextAreaColor(Color axRootTextAreaBackgroundColor) {
        NoteColor outputNoteColor;
        int greenValue = axRootTextAreaBackgroundColor.getGreen();
        switch (greenValue) {
            case 255:
                outputNoteColor = NoteColor.lightGreen;
                break;
            case 102:
                outputNoteColor = NoteColor.lightRed;
                break;
            case 229:
            default:
                outputNoteColor = NoteColor.lightYellow;
                break;
        }
        return outputNoteColor;
    }

    /**
     * This inner class is responsible for bold-ing the entire text found in the
     * any selected text in the Axeereraa text area.
     */

    private class Bold {

    }
}
