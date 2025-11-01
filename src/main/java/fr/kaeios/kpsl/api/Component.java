package fr.kaeios.kpsl.api;

import fr.kaeios.kpsl.api.event.ArrivalHandler;
import fr.kaeios.kpsl.api.event.TimeHandler;

import java.util.List;

public interface Component extends ArrivalHandler, TimeHandler {

    List<Component> getOutputs();

    List<Request> getResidents();

    DispatchPolicy getDispatchPolicy();

    void connectTo(Component component);

    boolean isBusy();

}
