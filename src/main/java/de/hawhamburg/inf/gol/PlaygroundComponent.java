package de.hawhamburg.inf.gol;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Swing Component that draws a Playground on the screen.
 * 
 * @author Christian Lins
 */
public class PlaygroundComponent extends JComponent {

    private final Playground playground;
    
    public PlaygroundComponent(Playground playground) {
        this.playground = playground;
    }
    
    private int cellSize() {
        return Math.min(
                cellSize(playground.getDimensionX(), getWidth()), 
                cellSize(playground.getDimensionX(), getHeight()));
    }
    
    private int cellSize(int playgroundDim, int componentDim) {
        int size = componentDim / playgroundDim;
        return size == 0 ? 1 : size;
    }
    
    private int offset(int cellSize, int playgroundDim, int componentDim) {
        return (componentDim - playgroundDim * cellSize) / 2;
    }
    
    /**
     * Paints the playground according to its Cell states.
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        int cellSize = cellSize();
        int offX = offset(cellSize, playground.getDimensionX(), getWidth());
        int offY = offset(cellSize, playground.getDimensionY(), getHeight());
        
        for (int x = 0; x < playground.getDimensionX(); x++) {
            for (int y = 0; y < playground.getDimensionY(); y++) {
                Cell cell = playground.getCell(x, y);
                g.setColor(cell.getValue() == 0 ? Color.BLACK : Color.GREEN);
                g.fillRect(offX + x * cellSize, offY + y * cellSize, cellSize, cellSize);
            }
        }
    }
    
}
