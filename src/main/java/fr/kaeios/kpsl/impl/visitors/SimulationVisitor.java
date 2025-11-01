package fr.kaeios.kpsl.impl.visitors;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.visitor.ComponentVisitor;

import java.util.*;

public class SimulationVisitor implements ComponentVisitor {

    private final Set<Component> visiteds = new HashSet<>();
    private final Queue<Component> toVisit = new ArrayDeque<>();

    private final double timeStep;

    public SimulationVisitor(double timeStep) {
        this.timeStep = timeStep;
    }

    @Override
    public void visit(Component start) {
        toVisit.add(start);

        while (!toVisit.isEmpty()) {
            Component current = toVisit.poll();
            if (!visiteds.add(current)) continue;

            current.onTick(timeStep);

            for (Component output : current.getOutputs()) {
                if (visiteds.contains(output)) return;

                toVisit.add(output);
            }

        }
    }

}
