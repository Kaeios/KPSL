package fr.kaeios.kpsl.gui;

import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Renderer;
import fr.kaeios.kpsl.gui.api.Point;

import javax.swing.*;
import java.awt.*;

public class ServiceComponent implements PlacedComponent {

    private final Renderer<Service> renderer = new ServiceRenderer();
    private final Service service;

    private Point position;


    public ServiceComponent(Service service, Point initialPosition) {
        this.service = service;
        this.position = initialPosition;
    }

    @Override
    public void place(Graphics graphics) {
        Image img = renderer.render(this.service);

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
