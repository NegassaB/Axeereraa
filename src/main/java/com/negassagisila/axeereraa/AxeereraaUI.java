package com.negassagisila.axeereraa;

import javax.swing.*;

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

    public AxeereraaUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        add(axRootPanel);
        setSize(400, 300);
        setTitle("Axeereraa");

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
        newNoteMenuItem.addActionListener(e -> {

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

    public static void main(String[] args) {
        try {
            new AxeereraaUI().buildUI();
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This inner class is responsible for bold-ing the entire text found in the
     * any selected text in the Axeereraa text area.
     */

    private class Bold {

    }
}
