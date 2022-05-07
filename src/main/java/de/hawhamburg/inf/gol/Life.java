package de.hawhamburg.inf.gol;

/**
 * Includes the Game of Life rules.
 * 
 * @author Christian Lins
 */
public class Life {
    
    private final Playground playground;
    
    public Life(Playground playground) {
        this.playground = playground;
    }
    
    /**
     * Counts the ALIVE neighbours for a cell identified by playground 
     * coordinates.
     * 
     * @param x
     * @param y
     * @return Number of ALIVE neighbours 
     */
    private int countNeighbours(int x, int y) {
        int neighbours = 0;
        for (int xi = x - 1; xi <= x + 1; xi++) {
            for (int yi = y - 1; yi <= y + 1; yi++) {
                if (x == xi && y == yi)
                    continue; // Identity
                if (playground.getCell(xi, yi).isAlive()) {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }
    
    /**
     * Applies the rules of the Game of Life and sets new state of the given
     * cell. If a cell has 3 neighbours and is currently DEAD, it is revived in
     * the next generation. If a cell has less than 2 or more than 3 neightbours
     * it will die in the next generation. So if a cell has 2 or 3 neighbours
     * and is currently ALIVE it will stay ALIVE.
     * @param cell
     * @param x
     * @param y 
     */
    public void process(Cell cell, int x, int y) {
        int neighbours = countNeighbours(x, y);
        
        if (neighbours == 3 && !cell.isAlive()) {
            // Cell is reborn
            cell.setValueNext(Cell.ALIVE);
        } else if (neighbours < 2 || neighbours > 3) {
            // Cell will die from loneliness or too much neighbours
            cell.setValueNext(Cell.DEAD);
        } else if (cell.isAlive()) /* 2 or 3 */ {
            // Cell stays alive
            cell.setValueNext(Cell.ALIVE);
        }
    }
}
