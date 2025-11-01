package fr.kaeios.kpsl.api.queue;

import fr.kaeios.kpsl.api.Request;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface QueueingPolicy {

    Optional<Request> chooseRequest(Queue<Request> requests);

}
