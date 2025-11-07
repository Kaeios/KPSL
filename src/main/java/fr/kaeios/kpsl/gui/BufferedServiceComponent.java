package fr.kaeios.kpsl.gui;

import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.api.Renderer;

import java.awt.*;

public class BufferedServiceComponent implements PlacedComponent {

    private final Renderer serviceRenderer;
    private final Renderer bufferRenderer;

    private Point position;

    public BufferedServiceComponent(Point initialPosition) {
        this.serviceRenderer = new ServiceRenderer();
        this.bufferRenderer = new BufferRenderer();

        this.position = initialPosition;
    }

    @Override
    public void place(Graphics graphics) {
        Image serviceImage = this.serviceRenderer.render();
        Image bufferImage = this.bufferRenderer.render();

        graphics.drawImage(serviceImage, position.x() + 50, position.y(), null);
        graphics.drawImage(bufferImage, position.x(), position.y(), null);
    }

    @Override
    public Point getInputPos() {
        return new Point(position.x(), position.y() + 25);
    }

    @Override
    public Point getOutputPos() {
        return new Point(position.x() + 100, position.y() + 25);
    }

}
