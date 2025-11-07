package fr.kaeios.kpsl.gui;

import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.api.Renderer;

import javax.swing.*;
import java.awt.*;

public class BufferComponent implements PlacedComponent {

    private final Renderer renderer;

    private Point position;

    public BufferComponent(Point initialPosition) {
        this.renderer = new BufferRenderer();
        this.position = initialPosition;
    }

    @Override
    public void place(Graphics graphics) {
        Image img = renderer.render();

        graphics.drawImage(img, position.x(), position.y(), null);
    }

    @Override
    public Point getInputPos() {
        return new Point(position.x(), position.y() + 25);
    }

    @Override
    public Point getOutputPos() {
        return new Point(position.x() + 50, position.y() + 25);
    }

}
