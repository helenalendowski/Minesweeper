package com.company;
import java.util.*;
import java.awt.*;      // for action events
import javax.swing.*;
import java.io.IOException;

public class MinesweeperView implements Observer{
    private MinesweeperModel model;
    private MinesweeperController controller;

    JFrame frame;
    JPanel grid_panel;
    JPanel title_panel;
    JLabel title;
    JButton [][] buttons;


    public MinesweeperView(MinesweeperModel model) {
        this.model = model;
        this.model.addObserver(this);
        controller = new MinesweeperController(model, this);
        display();
        //makeController();
    }
    private void makeController() {
       // controller = new MinesweeperController(model, this);
    }

    private void display(){
        int size = model.getGridSize();
        int frame_size = size * 50;

        frame = new JFrame();
        grid_panel = new JPanel(new GridLayout(size, size));
        buttons = new JButton[size][size];
        title = new JLabel();
        title_panel = new JPanel();

       //Icon icon = new ImageIcon("\\pictures\\bomb.PNG");


        // Define frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //fame.setLocationRelativeTo
        frame.setLocation(new Point(500, 150));                 // Set location on laptop screen
        frame.setSize(new Dimension(frame_size, frame_size+100));
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout((new BorderLayout()));
        frame.setTitle("Minesweeper");
        frame.setVisible(true);

        // Add title panel
        title.setBackground(new Color(25, 25, 25));
        title.setForeground(new Color(25, 255, 0));
        title.setFont(new Font("Ink Free", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        setTitle();
        //title.setText("Minesweeper");
        title.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,0, frame_size, 100 );

        // Add grid panel and buttons
        //grid_panel.setBackground(new Color(150, 150, 150));

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                buttons[row][col] = new JButton();
                grid_panel.add(buttons[row][col]);
                buttons[row][col] .setFont(new Font("MV Boli", Font.BOLD, 15));
                buttons[row][col].setFocusable(false);
                buttons[row][col].addActionListener(controller);           // Add cell to the action listener
            }
        }

        // Add components
        title_panel.add(title);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(grid_panel);



        //JLabel weight_label = new JLabel("Weight (kg):");
       // weight_label.setBounds(75, 20, 150, 30);
       // firstFrame.add(weight_label);

        // Frame should be defined at the end
        //frame.setSize(400, 300);
        //frame.setLayout(null);
        //frame.setVisible(true);
    }

    public void setTitle(){
        title.setText("Minesweeper      Mines: " + model.getNumMines());
    }

    public void won(){
        JOptionPane.showMessageDialog(null, "You won!");
    }

    public void lost(){
        JOptionPane.showMessageDialog(null, "You lost!");
    }

    public void update(Observable source, Object args){
        System.out.println("View: update: ");

        if (args != null) {
            String event = (String)args;
            if (event.equals("LOST")) {
                lost();
            }
            if (event.equals("WON")) {
                won();
            }
        }
        //resultLabel.setText("Result: " + model.calculateBMI());

        int size = model.getGridSize();

        // Update cell view
        for(int row=0; row < size; row++){
            for(int col=0; col < size; col++) {

                // Check if the cell is uncovered
                if(model.getCellCover(row, col) == 0){
                    buttons[row][col].setBackground(new Color(232,235,247));

                    int num =  model.getCellNumber(row,col);
                    switch (num){
                        case 0:
                            break;
                        case 1:
                            buttons[row][col].setForeground(new Color(55,126,184));
                            buttons[row][col].setText("1");
                            break;
                        case 2:
                            buttons[row][col].setForeground(new Color(77,175,74));
                            buttons[row][col].setText("2");
                            break;
                        case 3:
                            buttons[row][col].setForeground(new Color(255, 0, 0));
                            buttons[row][col].setText("3");
                            break;
                        case 4:
                            buttons[row][col].setForeground(new Color(50,52,148));
                            buttons[row][col].setText("4");
                            break;
                        case 5:
                            buttons[row][col].setForeground(new Color(153,0,13));
                            buttons[row][col].setText("5");
                            break;
                        case 6:
                            buttons[row][col].setForeground(new Color(22,84,94));
                            buttons[row][col].setText("6");
                            break;
                        case 7:
                            buttons[row][col].setForeground(new Color(37,37,37));
                            buttons[row][col].setText("7");
                            break;
                        case 8:
                            buttons[row][col].setForeground(new Color(150,150,150));
                            buttons[row][col].setText("8");
                            break;
                        case 9:
                            buttons[row][col].setForeground(new Color(37,37,37));
                            buttons[row][col].setText("X");
                            break;
                        default: break;
                    }
                }
            }
        }
    }

    // Explosion animation update view in the game loop
    // Change image of button boom animation
}
