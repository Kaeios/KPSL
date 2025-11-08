package fr.kaeios.kpsl.gui;

import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.gui.api.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ServiceRenderer implements Renderer<Service> {

    @Override
    public Image render(Service component) {
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.BLACK);

        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1.0f);
        graphics.setComposite(comp);

        graphics.drawOval(0, 0, 50, 50);
        graphics.drawString(String.format("%03d", component.getResidents().size()), 15, 30);

        return image;
    }

}
