package fr.kaeios.kpsl.impl;

import fr.kaeios.kpsl.api.Request;
import fr.kaeios.kpsl.api.queue.QueueingPolicy;

import java.util.Optional;
import java.util.Queue;

public class FIFOPolicy implements QueueingPolicy {

    private static final FIFOPolicy INSTANCE = new FIFOPolicy();

    @Override
    public Optional<Request> chooseRequest(Queue<Request> requests) {
        if(requests.isEmpty()) return Optional.empty();

        return Optional.ofNullable(requests.poll());
    }

    public static FIFOPolicy getInstance() {
        return INSTANCE;
    }

}
