package fr.kaeios.kpsl.api;

import fr.kaeios.kpsl.api.event.ArrivalHandler;
import fr.kaeios.kpsl.api.event.TimeHandler;
import fr.kaeios.kpsl.api.visitor.ComponentVisitor;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.gui.drawing.PlacedComponentFactoryVisitor;

import java.util.List;

public interface Component extends ArrivalHandler, TimeHandler {

    List<Component> getOutputs();

    List<Request> getResidents();

    DispatchPolicy getDispatchPolicy();

    void connectTo(Component component);

    boolean isBusy();

    PlacedComponent accept(PlacedComponentFactoryVisitor visitor, Point position);

}
