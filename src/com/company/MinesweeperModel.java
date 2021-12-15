package com.company;
import java.util.*;

/*
    The model implements the game logic.
    Each cell shows the amount of neighbour cells with mines
    0: no mines
    1: one mine
    2: two mines
    ...
    8: all neighbours are mines
    9: cell is a mine
*/

public class MinesweeperModel extends Observable {

    private int [][] grid;          // Represents the cells containing the information about mines (Integers from 0 to 9)
    private int [][] cellCover;     // Represents the cells cover - 1: cell is covered, 0: cell is uncovered
    private int numCellsCovered;    // Number of covered cells in the grid
    private int numMines;           // Number of mines in the grid
    private int gridSize;           // Number of cells in one row and column
    private boolean gameEnd;

    public MinesweeperModel() {
        this.gridSize = 9;
        this.numMines = 10;
        this.grid = new int[gridSize][gridSize];
        this.cellCover = new int[gridSize][gridSize];
        setUpBoard();
    }

    private void setGridSize(int size){
        /*
            This function is currently not used.
            For future implementation to enable difficulty levels:
             beginner: 9x9 cells
             intermediate: 16x16 cells
             specialist: 30x16 cells

         */
        this.gridSize = size;
    }
    public int getGridSize(){
        return this.gridSize;
    }
    private void setNumMines(int num){
        /*
            This function is currently not used.
            For future implementation to enable difficulty levels:
             beginner: 10 mines
             intermediate: 40 mines
             specialist: 99 mines
         */
        this.numMines = num;
    }
    public int getNumMines(){
        return this.numMines;
    }
    public int getCellNumber(int row, int col){
        return this.grid[row][col];
    }
    public int getCellCover(int row, int col){
        return this.cellCover[row][col];
    }

    private boolean cellIsMine (int row, int col) {
        // Check whether the given cell (row, col) is a mine
        if (grid[row][col] == 9)
            return (true);
        else
            return (false);
    }

    private boolean cellExists (int row, int col) {
        // Check whether the given cell (row, col) exists
        return (row >= 0) && (row < gridSize) && (col >= 0) && (col < gridSize);
    }

    private int countNeighbourMines (int row, int col) {
    /* Count all the mines in the neighbour cells for the given cell (row,col)
        [ ] [ ] [ ]
        [ ] [x] [ ]
        [ ] [ ] [ ]

        x: Current Cell    (row, col)
        1. Up-Left         (row-1, col-1)
        2. Up              (row-1, col)
        3. Up-Right        (row-1, col+1)
        4. Left            (row, col-1)
        5. Right           (row, col+1)
        6. Down-Left       (row+1, col-1)
        7. Down            (row+1, col)
        8. Down-Right      (row+1, col+1)
    */
        int countMines = 0;

        // Before evaluation if the cell is a mine, check if the cell/ neighbour exists

        // 1st Neighbour Up-Left
        if (cellExists (row-1, col-1) == true) {
            if (cellIsMine (row-1, col-1) == true) {
                countMines++;
            }
        }

        // 2nd Neighbour - Up
        if (cellExists (row-1, col) == true) {
            if (cellIsMine (row-1, col) == true) {
                countMines++;
            }
        }

        // 3rd Neighbour - Up-Right
        if (cellExists (row-1, col+1) == true) {
            if (cellIsMine (row-1, col+1) == true) {
                countMines++;
            }
        }

        // 4th Neighbour - Left
        if (cellExists (row, col-1) == true) {
            if (cellIsMine (row, col-1) == true) {
                countMines++;
            }
        }

        // 5th Neighbour - Right
        if (cellExists (row, col+1) == true) {
            if (cellIsMine (row, col+1) == true) {
                countMines++;
            }
        }

        // 6th Neighbour - Down-Left
        if (cellExists (row+1, col-1) == true) {
            if (cellIsMine (row+1, col-1) == true){
                countMines++;
            }
        }

        // 7th Neighbour - Down
        if (cellExists (row+1, col) == true) {
            if (cellIsMine (row+1, col) == true) {
                countMines++;
            }
    }

        // 8th Neighbour - Down-Right
        if (cellExists (row+1, col+1) == true){
            if (cellIsMine (row+1, col+1) == true) {
                countMines++;
            }
        }

        return (countMines);
    }

    private void setUpBoard(){

        // Define random positions for the mines
        int range = this.gridSize - 1;           //Create range for random number
        int rand1;
        int rand2;

        // Set variables for game end
        this.gameEnd = false;
        this.numCellsCovered = gridSize*gridSize;

        // Find positions for the mines
        for (int m = 0; m < this.numMines; m++){
           do{                                      //Do-While loop in case the same random numbers occur again
                rand1 = (int)(Math.random() * range);
                rand2 = (int)(Math.random() * range);
                //System.out.println("Rand 1:" + rand1 + ", Rand 2: " + rand2);
           }while (cellIsMine(rand1, rand2));    //If the cell is already a mine, calculate again
            this.grid[rand1][rand2] = 9;
        }

        // Define the other cells according to the surrounded mines
        for (int row = 0; row < this.gridSize; row++){
            for(int col = 0; col < this.gridSize; col++){
                // Cover all cells
                this.cellCover[row][col] = 1;
                // Only if this cell is not a mine count the neighbour mines
                if (cellIsMine(row, col) == false) {
                    this.grid[row][col] = countNeighbourMines(row, col);
                }
            }
        }

    }

    private void checkLost(int row, int col){
        if(cellIsMine(row,col) == true){
            System.out.println("You lost.");
            uncoverMines();
            setChanged();
            notifyObservers("LOST");
            this.gameEnd = true;
        }
        else{
            System.out.println("Game continues.");
        }
    }

    private void checkWin(){
        if(this.numCellsCovered == this.numMines){
            setChanged();
            notifyObservers("WON");
            this.gameEnd = true;
        }
    }

    private void uncoverMines(){
        for (int row = 0; row < this.gridSize; row++){
            for(int col = 0; col < this.gridSize; col++){
                // Uncover all mines
                // Only if this cell is not a mine count the neighbour mines
                if (cellIsMine(row, col) == true) {
                    this.cellCover[row][col] = 0;
                }
            }
        }
    }

    private void countDownNumCellsCovered(){
        this.numCellsCovered --;
        System.out.println("Count down. numCellsCovered = "+numCellsCovered);
    }

    private void uncoverNeighbours(int row, int col){
        /* If a grid[row][col] = 0, uncover all neighbour cells for the given cell (row,col)
        [ ] [ ] [ ]
        [ ] [x] [ ]
        [ ] [ ] [ ]

        x: Current Cell    (row, col)
        1. Up-Left         (row-1, col-1)
        2. Up              (row-1, col)
        3. Up-Right        (row-1, col+1)
        4. Left            (row, col-1)
        5. Right           (row, col+1)
        6. Down-Left       (row+1, col-1)
        7. Down            (row+1, col)
        8. Down-Right      (row+1, col+1)

        If another neighbour cell is 0, also uncover all its neighbours
        Before uncovering the cell, check if the cell/ neighbour exists and the neighbour is still covered
    */
       // List<Integer> noNeighboursAreMines = new ArrayList<>();

        // 1st Neighbour Up-Left
        if (cellExists (row-1, col-1) == true){
            if(this.cellCover[row - 1][col - 1] == 1) {
                this.cellCover[row - 1][col - 1] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row - 1][col - 1] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row - 1,col - 1);    // uncover all its neighbours
                }
            }
        }

        // 2nd Neighbour - Up
        if (cellExists (row-1, col) == true) {
            if(this.cellCover[row-1][col] == 1) {
                this.cellCover[row - 1][col] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row - 1][col] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row - 1, col);        // uncover all its neighbours
                }
            }
        }

        // 3rd Neighbour - Up-Right
        if (cellExists (row-1, col+1) == true) {
            if(this.cellCover[row-1][col + 1] == 1) {
                this.cellCover[row - 1][col + 1] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row - 1][col + 1] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row - 1,col + 1);    // uncover all its neighbours
                }
            }
        }

        // 4th Neighbour - Left
        if (cellExists (row, col-1) == true) {
            if(this.cellCover[row][col - 1] == 1) {
                this.cellCover[row][col - 1] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row][col - 1] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row,col - 1);         // uncover all its neighbours
                }
            }
        }

        // 5th Neighbour - Right
        if (cellExists (row, col+1) == true) {
            if(this.cellCover[row][col + 1] == 1) {
                this.cellCover[row][col + 1] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row][col + 1] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row, col + 1);         // uncover all its neighbours
                }
            }
        }

        // 6th Neighbour - Down-Left
        if (cellExists (row+1, col-1) == true) {
            if(this.cellCover[row + 1][col - 1] == 1) {
                this.cellCover[row + 1][col - 1] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row + 1][col - 1] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row + 1, col - 1);    // uncover all its neighbours
                }
            }
        }

        // 7th Neighbour - Down
        if (cellExists (row+1, col) == true) {
            if(this.cellCover[row + 1][col] == 1) {
                this.cellCover[row + 1][col] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row + 1][col] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row + 1, col);        // uncover all its neighbours
                }
            }
        }

        // 8th Neighbour - Down-Right
        if (cellExists (row+1, col+1) == true){
            if(this.cellCover[row + 1][col + 1] == 1) {
                this.cellCover[row + 1][col + 1] = 0;               // uncover the cell
                countDownNumCellsCovered();
                if (this.grid[row + 1][col + 1] == 0) {             // if the number of neighbour mines is 0
                    uncoverNeighbours(row + 1, col + 1);    // uncover all its neighbours
                }
            }
        }
    }

    private void clearGrid(){
        for (int row = 0; row < this.gridSize; row++){
            for(int col = 0; col < this.gridSize; col++){
                // Reset all cells to 0
                this.grid[row][col] = 0;
                // Reset all cells to covered
                this.cellCover[row][col] = 1;
            }
        }
    }

    public void performMove(int row, int col){
        /*
            This function performs a users move and is called by the controller.
            The input of the function is the cell that has been clicked to uncover.
            If the game is already finished reset the grid and set up a new game.
            Otherwise, check if the clicked cell is still uncovered. If yes, set the cell cover to uncovered (=0)
            If the cell has no neighbour mines, uncover all it's neighbours as well
         */
        if(this.gameEnd == true){
            clearGrid();
            setUpBoard();
            System.out.println("Game end, set up new game.");
            setChanged();
            notifyObservers("NEWGAME");
        }
        else {
            if (this.cellCover[row][col] == 1) {
                this.cellCover[row][col] = 0;
                countDownNumCellsCovered();

                // If the cell has no mines in the neighbourhood (grid[][] == 0), uncover all neighbours
                if (this.grid[row][col] == 0) {
                    uncoverNeighbours(row, col);
                }
                setChanged();
                notifyObservers();
                checkLost(row,col);
                checkWin();
            }
        }
    }

}
