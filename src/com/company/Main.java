package com.company;

public class Main {

    private boolean running;
    private double UPS;
    private double FPS;

    public Main(){
        MinesweeperModel model = new MinesweeperModel();
        MinesweeperView view = new MinesweeperView(model);
        running = true;
        FPS = 60.0;
        UPS = 0.0;
    }

//    public void run(){
//        // Implements the game loop
//
//        long previousTime = System.nanoTime();
//        final double timeU = 1000000000 / UPS;
//        final double timeF = 1000000000 / FPS;
//        double deltaU = 0;
//        double deltaF = 0;
//        int frames = 0;
//        int ticks = 0;
//        long timer = System.currentTimeMillis();
//
//        while (running) {
//
//            long currentTime = System.nanoTime();
//            deltaU += (currentTime - previousTime) / timeU;
//            deltaF += (currentTime - previousTime) / timeF;
//            previousTime = currentTime;
//
//            if (deltaU >= 1) {
//                //getInput();
//               //view.update();
//                ticks++;
//                deltaU--;
//            }
//
//            if (deltaF >= 1) {
//                //render();
//                frames++;
//                deltaF--;
//            }
//
//            if (System.currentTimeMillis() - timer > 1000) {
//                //if (RENDER_TIME) {
//                  //  System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
//                //}
//                frames = 0;
//                ticks = 0;
//                timer = timer + 1000;
//            }
//        }
//    }

    public static void main(String[] args) {
	// write your code here
        //Main main = new Main();
        MinesweeperModel model = new MinesweeperModel();
        MinesweeperView view = new MinesweeperView(model);

        //run();

    }
}
