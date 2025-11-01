package fr.kaeios.kpsl.impl;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.DispatchPolicy;
import fr.kaeios.kpsl.api.Request;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.api.queue.QueueingPolicy;

import java.util.*;

public class BasicQueue implements Buffer {

    private final int capacity;
    private final Queue<Request> requests;

    private List<Component> outputs = new ArrayList<>();

    private final QueueingPolicy queueingPolicy;
    private final DispatchPolicy dispatchPolicy;

    public BasicQueue(int capacity, QueueingPolicy queueingPolicy, DispatchPolicy dispatchPolicy) {
        this.capacity = capacity;
        this.requests = new ArrayDeque<>(capacity);

        this.queueingPolicy = queueingPolicy;
        this.dispatchPolicy = dispatchPolicy;
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
    public List<Component> getOutputs() {
        return Collections.unmodifiableList(this.outputs);
    }

    @Override
    public List<Request> getResidents() {
        return this.getPopulation();
    }

    @Override
    public DispatchPolicy getDispatchPolicy() {
        return this.dispatchPolicy;
    }

    @Override
    public void connectTo(Component component) {
        this.outputs.add(component);
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public void onArrival(Request request) {
        if(getPopulation().size() >= getPopulationCapacity()) return; // Drop request

        // Add request to queue
        this.requests.add(request);
    }

    @Override
    public void onTick(double elapsedTime) {
        Optional<Component> output = this.getDispatchPolicy().chooseOutput(this.outputs);
        if(output.isEmpty()) return;

        Optional<Request> oRequest = pollRequest();
        if(oRequest.isEmpty()) return;

        Request request = oRequest.get();
        output.get().onArrival(request);
        requests.remove(request);
    }

}
