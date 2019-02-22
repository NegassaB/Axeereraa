package com.negassagisila.axeereraa;

import javax.swing.*;

/**
 * The actual user interface for the Axeereraa application.
 */

public class AxeereraaUI extends JFrame {
    private JPanel axRootPanel;
    private JScrollPane axRootScrollPane;
    private JTextArea axRootTextArea;
    private JMenuBar axMenuBar;

    /**
     * A constructor that runs every time a new Axeereraa note is needed or buit
     */

    public AxeereraaUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        add(axRootPanel);
        setSize(500, 300);
        setTitle("\t\t\tAxeereraa");

        buildUI();
        setJMenuBar(axMenuBar);

        setVisible(true);
    }

    /**
     * This method is responsible for building the components of the UI like
     * the menu bar, the menu and it's options.
     */

    private void buildUI() {
        axMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("file");
        JMenu editMenu = new JMenu("edit");
        JMenu viewMenu = new JMenu("view");
        JMenu helpMenu = new JMenu("help");

        JMenuItem newNoteMenuItem = new JMenuItem("New Note");
        JMenuItem deleteNoteMenuItem = new JMenuItem("Delete Note");

        JMenuItem boldNoteMenuItem = new JMenuItem("bold | ctrl+b");
        JMenuItem italicMenuItem = new JMenuItem("italic | ctrl+i");
        JMenuItem underlineMenuItem = new JMenuItem("underline | ctrl+u");
        JMenuItem selectAllMenuItem = new JMenuItem("select all | ctrl+a");
        JMenuItem cutMenuItem = new JMenuItem("cut | ctrl+x");
        JMenuItem copyMenuItem = new JMenuItem("copy | ctrl+c");
        JMenuItem pasteMenuItem = new JMenuItem("paste | ctrl+v");

        JMenuItem alignmentMenuItem = new JMenuItem("alignment");
        JMenuItem wordWrapMenuItem = new JMenuItem("word wrap");

        JMenuItem aboutMenuItem = new JMenuItem("about");
        JMenuItem contactDeveloperMenuItem = new JMenuItem("contact developer");

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

    //TODO: the next steps:
    //TODO: 1. code all the necessary actionEventListeners for the menu & it's options
    //TODO: 2. code the right click options with their actionEventListeners
    //TODO: 3. code the keyboard shortcut options with their actionEventListeners
    //TODO: 4. code the setColor method for the axRootPanel, color from the Note object
    //TODO: 5. code the word wrap inner class
    //TODO: 6. code the always on top, minimize & close button actions
    //TODO: 7. run & test (currently no other option, sorry)


}
