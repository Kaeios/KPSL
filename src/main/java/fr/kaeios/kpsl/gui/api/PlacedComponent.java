package fr.kaeios.kpsl.gui.api;

import java.awt.*;

public interface PlacedComponent {

    void place(Graphics graphics);

    Point getInputPos();
    Point getOutputPos();

}
