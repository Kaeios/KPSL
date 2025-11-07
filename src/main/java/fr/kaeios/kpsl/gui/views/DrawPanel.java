package fr.kaeios.kpsl.gui.views;

import fr.kaeios.kpsl.gui.api.PlacedComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class DrawPanel extends JPanel {

    private final Set<PlacedComponent> components = new HashSet<>();
    private final Set<Link> links = new HashSet<>();

    public DrawPanel() {
        setLayout(null); // manual placement
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Link link : links) {
            System.out.println("Link: " + link);
            g.drawLine(link.from().x(), link.from().y(), link.to().x(), link.to().y());
        }

        for(PlacedComponent component : components) {
            component.place(g);
        }
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void addComponent(PlacedComponent component) {
        components.add(component);
    }

}
