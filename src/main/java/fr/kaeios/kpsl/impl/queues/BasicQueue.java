package fr.kaeios.kpsl.impl.queues;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.DispatchPolicy;
import fr.kaeios.kpsl.api.Request;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.api.queue.QueueingPolicy;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.drawing.PlacedComponentFactoryVisitor;
import fr.kaeios.kpsl.impl.BasicComponent;

import java.util.*;

public class BasicQueue extends BasicComponent implements Buffer {

    private final int capacity;
    private final Queue<Request> requests;

    private final QueueingPolicy queueingPolicy;

    public BasicQueue(int capacity, QueueingPolicy queueingPolicy, DispatchPolicy dispatchPolicy) {
        super(dispatchPolicy);
        this.capacity = capacity;
        this.requests = new ArrayDeque<>(capacity);

        this.queueingPolicy = queueingPolicy;
    }

    @Override
    public int getPopulationCapacity() {
        return capacity;
    }

    @Override
    public List<Request> getPopulation() {
        return this.requests.stream().toList();
    }

    @Override
    public Optional<Request> pollRequest() {
        return queueingPolicy.chooseRequest(this.requests);
    }

    @Override
    public List<Request> getResidents() {
        return this.getPopulation();
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public PlacedComponent accept(PlacedComponentFactoryVisitor visitor, Point position) {
        return visitor.visitBuffer(this, position);
    }

    @Override
    public void onArrival(Request request) {
        if(getPopulation().size() >= getPopulationCapacity()) return; // Drop request

        // Add request to queue
        this.requests.add(request);
    }

    @Override
    public void onTick(double elapsedTime) {
        Optional<Component> output = this.getSelectedOutput();

        while(output.isPresent()) {
            Optional<Request> oRequest = pollRequest();
            if(oRequest.isEmpty()) return;

            Request request = oRequest.get();
            output.get().onArrival(request);
            requests.remove(request);

            output = this.getSelectedOutput();
        }

    }

}
