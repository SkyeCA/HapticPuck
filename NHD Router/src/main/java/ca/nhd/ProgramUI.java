package ca.nhd;

import ca.nhd.comm.ui.MainForm;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ProgramUI {

    public static void main(String... args) throws IOException {
        JFrame frame = new JFrame("Simple GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        MainForm mainForm = new MainForm();

        frame.add(mainForm, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
