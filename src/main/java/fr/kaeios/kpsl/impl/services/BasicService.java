package fr.kaeios.kpsl.impl.services;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.DispatchPolicy;
import fr.kaeios.kpsl.api.Request;
import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.drawing.PlacedComponentFactoryVisitor;
import fr.kaeios.kpsl.impl.BasicComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicService extends BasicComponent implements Service {

    private final double serviceTime;
    private final int serverCount;

    private List<RequestHandler> currentRequests;

    public BasicService(double serviceTime, DispatchPolicy dispatchPolicy, int serverCount) {
        super(dispatchPolicy);
        this.serviceTime = serviceTime;
        this.serverCount = serverCount;
        this.currentRequests = new ArrayList<>(serverCount);
    }

    @Override
    public List<Request> getCurrentRequest() {
        return currentRequests.stream().map(handler -> handler.request).toList();
    }

    @Override
    public List<Request> getResidents() {
        return getCurrentRequest();
    }

    @Override
    public boolean isBusy() {
        return this.currentRequests.size() >= this.serverCount;
    }

    @Override
    public PlacedComponent accept(PlacedComponentFactoryVisitor visitor, Point position) {
        return visitor.visitService(this, position);
    }

    @Override
    public void onArrival(Request request) {
        if(!isBusy()) {
            this.currentRequests.add(new RequestHandler(request));
        } else {
            throw new IllegalStateException("Service is too busy to accept new requests");
        }
    }

    @Override
    public void onTick(double elapsedTime) {
        this.currentRequests.forEach(handler -> {
            handler.currentTime += elapsedTime;

            if(handler.currentTime >= serviceTime) {
                Optional<Component> output = this.getSelectedOutput();
                output.ifPresent(component -> component.onArrival(handler.request));
            }
        });

        List<RequestHandler> toRemove =  this.currentRequests.stream()
                .filter(handler -> handler.currentTime >= serviceTime)
                .toList();

        this.currentRequests.removeAll(toRemove);
    }

    private static final class RequestHandler {

        final Request request;
        double currentTime = 0.0D;

        private RequestHandler(Request request) {
            this.request = request;
        }

    }

}
