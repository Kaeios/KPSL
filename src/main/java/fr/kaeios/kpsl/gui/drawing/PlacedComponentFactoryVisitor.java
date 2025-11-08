package fr.kaeios.kpsl.gui.drawing;

import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.impl.sources.PeriodicArrivalSource;

public interface PlacedComponentFactoryVisitor {

    PlacedComponent visitBuffer(Buffer buffer, Point position);
    PlacedComponent visitService(Service service, Point position);
    PlacedComponent visitPeriodicArrivalSource(PeriodicArrivalSource src, Point position);

}
