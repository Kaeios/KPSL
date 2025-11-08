package fr.kaeios.kpsl.gui;

import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.api.Renderer;
import fr.kaeios.kpsl.impl.sources.PeriodicArrivalSource;

import java.awt.*;

public class ArrivalSourceComponent implements PlacedComponent {

    private final Renderer<PeriodicArrivalSource> renderer = new ArrivalSourceRenderer();
    private final PeriodicArrivalSource arrivalSource;

    private Point position;

    public ArrivalSourceComponent(PeriodicArrivalSource arrivalSource, Point position) {
        this.arrivalSource = arrivalSource;
        this.position = position;
    }

    @Override
    public void place(Graphics graphics) {
        Image img = renderer.render(this.arrivalSource);

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
