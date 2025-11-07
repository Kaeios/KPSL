package fr.kaeios.kpsl.gui;

import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.api.Renderer;

import javax.swing.*;
import java.awt.*;

public class ArrivalSourceComponent implements PlacedComponent {

    private final Renderer renderer = new ArrivalSourceRenderer();
    private Point position;

    public ArrivalSourceComponent(Point position) {
        this.position = position;
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
