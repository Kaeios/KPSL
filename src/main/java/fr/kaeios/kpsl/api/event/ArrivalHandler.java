package fr.kaeios.kpsl.api.event;

import fr.kaeios.kpsl.api.Request;

public interface ArrivalHandler {

    void onArrival(Request request);

}
