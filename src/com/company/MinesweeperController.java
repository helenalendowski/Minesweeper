package com.company;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MinesweeperController implements Observer, ActionListener {
    private MinesweeperModel model;
    private MinesweeperView view;

    public MinesweeperController(MinesweeperModel model, MinesweeperView view){
        this.model = model;
        this.view = view;

        this.model.addObserver(this);
    }

    @Override
    public void update(Observable source, Object agrs){
        System.out.println("Controller update.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String action = e.getActionCommand();
        int size = model.getGridSize();

        for(int row=0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Check which button was clicked
                if (e.getSource() == view.buttons[row][col]) {
                    System.out.println("Event: " + e.getSource());
                    // Change model
                    model.performMove(row, col);
                }
            }
        }
    }

}
