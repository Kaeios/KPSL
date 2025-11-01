package fr.kaeios.kpsl.impl;

import fr.kaeios.kpsl.api.DispatchPolicy;
import fr.kaeios.kpsl.api.Request;

import java.util.Collections;
import java.util.List;

public class BasicArrivalSource extends BasicComponent{

    private final double interval;

    private double currentTime = 0.0D;

    public BasicArrivalSource(DispatchPolicy dispatchPolicy, double interval) {
        super(dispatchPolicy);

        this.interval = interval;
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
    public void onArrival(Request request) {}

    @Override
    public void onTick(double elapsedTime) {
        this.currentTime += elapsedTime;

        if(currentTime >= interval) {
            currentTime = 0.0D;
            this.getSelectedOutput().ifPresent(output -> output.onArrival(new DummyRequest()));
        }
    }

}
