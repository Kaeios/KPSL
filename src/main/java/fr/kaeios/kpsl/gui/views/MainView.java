package fr.kaeios.kpsl.gui.views;

import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class MainView extends JFrame {

    private final Set<PlacedComponent> components = new HashSet<>();
    private final Set<Link> links = new HashSet<>();

    private final DrawPanel drawPanel = new DrawPanel();

    public MainView() {
        setContentPane(drawPanel);
    }

    public void disp() {
        setupWindow();
        renderView();
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("KPSL GUI");
        setLayout(null);  // We'll manually place components
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setResizable(false);
    }

    public void renderView() {
        drawPanel.repaint();
    }

    public void addComponent(PlacedComponent component) {
        this.drawPanel.addComponent(component);
    }

    // New method to add a link (edge) between two points
    public void addLink(Point from, Point to) {
         this.drawPanel.addLink(new Link(from, to));
    }

}
