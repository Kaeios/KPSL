package fr.kaeios.kpsl.gui;

import fr.kaeios.kpsl.gui.api.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;

public class BufferRenderer implements Renderer {

    @Override
    public Image render() {
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.BLACK);

        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1.0f);
        graphics.setComposite(comp);

        for(int i = 0; i < 50; i += (50/3))
            graphics.drawRect(i, 5, 50/3, 40);


        String populationCountLabel =  String.format("%03d", 10).chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining("   "));


        graphics.drawString(populationCountLabel, 5, 30);

        return image;
    }

}
