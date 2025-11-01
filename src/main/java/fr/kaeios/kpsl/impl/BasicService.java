package fr.kaeios.kpsl.impl;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.DispatchPolicy;
import fr.kaeios.kpsl.api.Request;
import fr.kaeios.kpsl.api.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BasicService extends BasicComponent implements Service {

    private final double serviceTime;

    private Request currentRequest;
    private double currentTime = 0.0D;

    public BasicService(double serviceTime, DispatchPolicy dispatchPolicy) {
        super(dispatchPolicy);
        this.serviceTime = serviceTime;
    }

    @Override
    public Request getCurrentRequest() {
        return currentRequest;
    }

    @Override
    public List<Request> getResidents() {
        return currentRequest == null ? Collections.emptyList() : Collections.singletonList(currentRequest);
    }

    @Override
    public boolean isBusy() {
        return this.currentRequest != null && this.currentTime < this.serviceTime;
    }

    @Override
    public void onArrival(Request request) {
        if(!isBusy()) {
            this.currentRequest = request;
            this.currentTime = 0.0D;
        } else {
            throw new IllegalStateException("Trying to accept new request while previous not finished");
        }
    }

    @Override
    public void onTick(double elapsedTime) {
        if(currentRequest == null) return;

        this.currentTime += elapsedTime;

        if(currentTime > serviceTime) {
            Optional<Component> output = this.getSelectedOutput();

            output.ifPresent(component -> component.onArrival(this.currentRequest));

            this.currentRequest = null;
            this.currentTime = 0.0D;
        }
    }

}
