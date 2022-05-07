package de.hawhamburg.inf.gol;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Playground of the Game of Life. Consists logically of a two dimensional
 * array of Cell elements (DIM_X x DIM_Y).
 * 
 * @author Christian Lins
 */
public class Playground {
    
    private final int dimX;
    private final int dimY;
    
    /** 
     * The actual playground. Note that we use a 1D array here although the 
     * playground is 2D. 
     */
    private final Cell[] playground; 
    
    public Playground(int dimX, int dimY, Stream<Cell> generator) {
        this.dimX = dimX;
        this.dimY = dimY;
        
        // Initialize playground with (probably) random cells
        playground = generator.limit(dimX * dimY).toArray(Cell[]::new);
    }
    
    /**
     * Returns the Cell at the given index.
     * 
     * @param x
     * @param y
     * @return Cell object
     */
    public Cell getCell(int x, int y) {
        if (x < 0 || x >= dimX || y < 0 || y >= dimY) {
            return new Cell(Cell.DEAD);
        }

        return this.playground[x * dimX + y];
    }
    
    public int getDimensionX() {
        return dimX;
    }
    
    public int getDimensionY() {
        return dimY;
    }
    
    /**
     * Returns the playground as List of Cells for easier processing the 
     * Streams.
     * @return 
     */
    public List<Cell> asList() {
        return Arrays.asList(playground);
    }
}
