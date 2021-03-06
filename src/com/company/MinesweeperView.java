package com.company;
import java.util.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class MinesweeperView implements Observer, Runnable{
    private MinesweeperModel model;
    private MinesweeperController controller;

    private JFrame frame;
    private JPanel grid_panel;
    private JPanel title_panel;
    private JLabel title;
    JButton [][] buttons;

    private boolean lostAnimation;
    private int animationCount;
    private double lastAnimationTime;
    private boolean running;
    private ImageIcon currentIcon;
    private Image bomb_0;
    private Image bomb_1;
    private Image bomb_2;
    private Image bomb_3;
    private Image bomb_4;

    public MinesweeperView(MinesweeperModel model) {
        this.model = model;
        this.model.addObserver(this);
        controller = new MinesweeperController(model, this);
        this.lostAnimation = false;
        this.animationCount = 0;
        this.lastAnimationTime = 0;
        display();
        running = true;
        Thread t1= new Thread(this);
        t1.start();
    }

    public void run() {
        // This function contains the game loop pattern.
        System.out.println("Run enabled");
        double previousTime = System.nanoTime();
        double currentTime;
        double elapsedTime;

        while (this.running) {
            currentTime = System.nanoTime();
            elapsedTime = currentTime - previousTime;
            updateGameLoop(elapsedTime);
            previousTime = currentTime;
        }
    }

    private void updateGameLoop(double elapsedTime){
        double msecondsElapsed = elapsedTime/ 1000000;
        this.lastAnimationTime = this.lastAnimationTime + msecondsElapsed;

        // Slow down lost animation: update every 50 ms
        if(this.lostAnimation == true){
            if(this.lastAnimationTime >= 50){
                lost();
                System.out.println("Animation= " + this.animationCount);
                this.lastAnimationTime = 0;
            }
        }
        else{
            System.out.println("No Animation");
        }
    }

    private void loadImages(int size){
        try {
            this.bomb_0 = new ImageIcon(this.getClass().getResource("/bomb_0.png")).getImage();
            Image newimg = bomb_0.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            this.bomb_0 = newimg;  // transform it back
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            this.bomb_1 = new ImageIcon(this.getClass().getResource("/bomb_1.png")).getImage();
            Image newimg = bomb_1.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            this.bomb_1 = newimg;  // transform it back
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            this.bomb_2 = new ImageIcon(this.getClass().getResource("/bomb_2.png")).getImage();
            Image newimg = bomb_2.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            this.bomb_2 = newimg;  // transform it back
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            this.bomb_3 = new ImageIcon(this.getClass().getResource("/bomb_3.png")).getImage();
            Image newimg = bomb_3.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            this.bomb_3 = newimg;  // transform it back
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            this.bomb_4 = new ImageIcon(this.getClass().getResource("/bomb_4.png")).getImage();
            Image newimg = bomb_4.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            this.bomb_4 = newimg;  // transform it back
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void display(){
        int size = model.getGridSize();
        int button_size = 50;
        int frame_size = size * button_size;

        frame = new JFrame();
        grid_panel = new JPanel(new GridLayout(size, size));
        buttons = new JButton[size][size];
        title = new JLabel();
        title_panel = new JPanel();

        loadImages(button_size);

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
                buttons[row][col].setFont(new Font("MV Boli", Font.BOLD, 15));
                buttons[row][col].setFocusable(false);
                buttons[row][col].addActionListener(controller);           // Add cell to the action listener
            }
        }

        // Add components
        title_panel.add(title);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(grid_panel);

    }

    private void setTitle(){
        title.setText("Minesweeper      Mines: " + model.getNumMines());
    }

    private void won(){
        JOptionPane.showMessageDialog(null, "You won!");
    }

    private void lost(){
        /* This function takes care of the animation of exploding mines once a mine has been discovered.
           Choose animation icon according to the animation count.
         */

        int size = model.getGridSize();

        switch (this.animationCount){
            case 0:
                this.currentIcon = new ImageIcon(bomb_0);
                break;
            case 1:
                this.currentIcon = new ImageIcon(bomb_1);
                break;
            case 2:
                this.currentIcon = new ImageIcon(bomb_2);
                break;
            case 3:
                this.currentIcon = new ImageIcon(bomb_3);
                break;
            case 4:
                this.currentIcon = new ImageIcon(bomb_4);
                this.lostAnimation = false;                 //Animation is done
                this.animationCount = 0;                    //Reset animation count
                //JOptionPane.showMessageDialog(null, "You lost!");
                break;
            default: break;
        }

        for(int row=0; row < size; row++){
            for(int col=0; col < size; col++) {
                // Check if the cell is a mine
                if (model.getCellNumber(row, col) == 9) {
                    buttons[row][col].setIcon(currentIcon);
                    System.out.println("BOOM");
                }
            }
        }

        this.animationCount++;  //increase animation count for the next function call
    }

    public void update(Observable source, Object args){
        /*
            This function updates the view if the model changes.
            Button pressed: uncover changed cell(s) in the game grid
            Lost: enable lost animation
            Won: call the won function to show a notification
            NewGame: the user either lost or won and pressed a button again which initializes a new game
        */
        System.out.println("View: update: ");
        int size = model.getGridSize();

        if (args != null) {
            String event = (String)args;
            if (event.equals("LOST")) {
                this.lostAnimation = true;
            }
            if (event.equals("WON")) {
                won();
            }
            if(event.equals("NEWGAME")){
                display();
            }
        }

        // Update cell view
        for(int row=0; row < size; row++){
            for(int col=0; col < size; col++) {

                // Check if the cell is uncovered, show the number of neighbour mines on the button
                if(model.getCellCover(row, col) == 0){

                    // Change the background of the button to show the cell is uncovered
                    buttons[row][col].setBackground(new Color(255,255,255));

                    int num =  model.getCellNumber(row,col);
                    switch (num){
                        case 0:
                            // don't show a number
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
                            //buttons[row][col].setForeground(new Color(37,37,37));
                            //buttons[row][col].setText("X");
                            // The mine view is handled in the lost() function
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
