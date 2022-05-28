package de.hawhamburg.inf.gol;

import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Main application class.
 * 
 * @author Christian Lins
 */
public class Application {

    /* Size of the playground in X dimension */
    public static final int DIM_X = 200;
    
    /* Size of the playground in Y dimension */
    
    public static final int DIM_Y = 200;
    
    /* Probability threshold that a cell is initially being created */
    public static final float ALIVE_PROBABILITY = 0.3125f;
    
    /* Sleep time between every generation in milliseconds */
    public static final int SLEEP = 200;
    
    /**
     * Creates an potentially unlimited stream of Cell objects. The stream uses
     * random numbers between [0, 1] and the probability threshold whether a
     * cell is created DEAD (random > p) or ALIVE (random <= p).
     * 
     * @param p Cell alive probability threshold.
     * @return 
     */  
    
    private static Stream<Cell> createCellStream(float p) {
        Stream<Cell> cells = IntStream
                .range(0, 200000)
                .mapToObj(x -> new Cell(getCellStatus(p)));
        return cells;
    }
    
    public static void main(String[] args) {
        Stream<Cell> cellStream = createCellStream(ALIVE_PROBABILITY);
        Playground playground = new Playground(DIM_X, DIM_Y, cellStream);
        
        // Create and show the application window
        ApplicationFrame window = new ApplicationFrame();
        window.setVisible(true);
        window.getContentPane().add(new PlaygroundComponent(playground));
        
        // Create and start a LifeThreadPool with 50 threads
        LifeThreadPool pool = new LifeThreadPool(50);
        pool.start();       
        
        while (true) {  
            int count = 0;
            Life life = new Life(playground);
            List <Cell> cells = playground.asList();
            for (int xi = 0; xi < DIM_X; xi++) {
                for (int yi = 0; yi < DIM_Y; yi++) {
                    final int x = xi;
                    final int y = yi;    
                    final int c = count;    
                    pool.submit(() -> {
                          life.process(cells.get(c), x, y);                          
                      });   
                      count++;
                }
            }
              pool.start();
            // Wait for all threads to finish this generation
            // TODO
              try {
                    pool.joinAndExit();
              } catch (InterruptedException ie) {
                  System.err.println("Application InterruptedException");
              }
            // Submit switch to next generation for each cell and force a
            // window repaint to update the graphics
            pool.submit(() -> {
                playground.asList().forEach(cell -> cell.nextGen());
                window.repaint();
            });
            
            // Wait SLEEP milliseconds until the next generation
           // TODO
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException ie) {
                System.err.println("Application InterruptedException");
            }
        }
    }
    
     static int getCellStatus(float p) {
        Random r = new Random();
        float random = (float) (0.0 + r.nextFloat() * (1.0 - 0.0));        
        if (random > p) {
            return 0;
        }  
        return 1;
    }
}
