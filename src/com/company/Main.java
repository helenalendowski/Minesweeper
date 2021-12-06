package com.company;

public class Main {

    public Main(){
        MinesweeperModel model = new MinesweeperModel();
        MinesweeperView view = new MinesweeperView(model);
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
