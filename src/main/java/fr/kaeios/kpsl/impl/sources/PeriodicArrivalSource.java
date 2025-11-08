package fr.kaeios.kpsl.impl.sources;

import fr.kaeios.kpsl.api.DispatchPolicy;
import fr.kaeios.kpsl.api.Request;
import fr.kaeios.kpsl.api.visitor.ComponentVisitor;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.drawing.PlacedComponentFactoryVisitor;
import fr.kaeios.kpsl.impl.BasicComponent;
import fr.kaeios.kpsl.impl.requests.DummyRequest;

import java.util.Collections;
import java.util.List;

public class PeriodicArrivalSource extends BasicComponent {

    private final double period;

    private double currentTime = 0.0D;

    public PeriodicArrivalSource(DispatchPolicy dispatchPolicy, double period) {
        super(dispatchPolicy);

        this.period = period;
    }

    @Override
    public List<Request> getResidents() {
        return Collections.emptyList();
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public PlacedComponent accept(PlacedComponentFactoryVisitor visitor, Point position) {
        return visitor.visitPeriodicArrivalSource(this, position);
    }

    @Override
    public void onArrival(Request request) {}

    @Override
    public void onTick(double elapsedTime) {
        this.currentTime += elapsedTime;

        if(currentTime >= period) {
            currentTime = 0.0D;
            this.getSelectedOutput().ifPresent(output -> output.onArrival(new DummyRequest()));
        }
    }

}
