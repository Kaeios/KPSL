package fr.kaeios.kpsl.gui.drawing;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.api.visitor.ComponentVisitor;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.views.MainView;

import java.util.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DrawingVisitor implements ComponentVisitor {

    private final PlacedComponentFactoryVisitor factory = new DefaultPlacedComponentFactory();

    private final Set<Component> visited = new HashSet<>();
    private final Map<Component, Point> positions = new HashMap<>();
    private final Map<Component, PlacedComponent> placeds = new HashMap<>();
    private final MainView view;

    // Layout spacing
    private static final int X_SPACING = 200;
    private static final int Y_SPACING = 150;

    // Map of node -> subtree height (number of rows it occupies)
    private final Map<Component, Integer> subtreeHeight = new HashMap<>();

    public DrawingVisitor(MainView view) {
        this.view = view;
    }

    @Override
    public void visit(Component start) {
        computeSubtreeHeight(start);
        layout(start, 0, 0);
        drawAll();                 // render them on screen
    }

    private int computeSubtreeHeight(Component node) {
        if (!subtreeHeight.containsKey(node)) {
            var children = node.getOutputs();
            if (children.isEmpty()) {
                subtreeHeight.put(node, 1);
            } else {
                int height = 0;
                for (Component child : children) {
                    height += computeSubtreeHeight(child);
                }
                subtreeHeight.put(node, height);
            }
        }
        return subtreeHeight.get(node);
    }

    private void layout(Component node, int x, int startRow) {
        if (!visited.add(node)) return;

        var children = node.getOutputs();

        // Determine X coordinate for this node
        int nodeX = x;
        positions.put(node, new Point(nodeX, startRow * Y_SPACING));

        if (!children.isEmpty()) {
            int row = startRow;

            for (Component child : children) {
                int h = subtreeHeight.get(child);

                // Determine spacing to child
                int spacingToChild;
                if (node instanceof Buffer) {
                    spacingToChild = X_SPACING / 4;            // no space after buffer
                } else if (node instanceof Service) {
                    spacingToChild = X_SPACING; // reduced space after service
                } else {
                    spacingToChild = X_SPACING;    // normal spacing
                }

                // Layout child at adjusted X position
                layout(child, nodeX + spacingToChild, row);
                row += h;
            }

            // Center parent vertically over children
            int firstChildRow = startRow;
            int lastChildRow = row - 1;
            positions.computeIfPresent(node, (k, oldPos) -> new Point(oldPos.x(), ((firstChildRow + lastChildRow) / 2) * Y_SPACING));
        }
    }





    /**
     * Draws all components and connecting lines.
     */
    private void drawAll() {

        // Process components in the order they were visited (post-order)
        List<Component> drawOrder = new ArrayList<>(visited);
        Collections.reverse(drawOrder);

        for (Component c : drawOrder) {
            Point p = positions.get(c);
            PlacedComponent placed = c.accept(factory, p);
            placeds.put(c, placed);
            view.addComponent(placed);
        }

        placeds.forEach((comp, graph) -> {
            comp.getOutputs().forEach(out -> {
                PlacedComponent placedComponent = placeds.get(out);
                if(placedComponent == null) return;

                view.addLink(graph.getOutputPos(), placedComponent.getInputPos());
            });
        });
    }


}