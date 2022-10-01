package org.example;


import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay();
        obj.add(gamePlay);
        obj.setBounds(10,10,700,600);
        obj.setTitle("Break out ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}