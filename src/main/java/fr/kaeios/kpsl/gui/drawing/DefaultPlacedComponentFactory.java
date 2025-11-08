package fr.kaeios.kpsl.gui.drawing;

import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.gui.ArrivalSourceComponent;
import fr.kaeios.kpsl.gui.BufferComponent;
import fr.kaeios.kpsl.gui.ServiceComponent;
import fr.kaeios.kpsl.gui.api.PlacedComponent;
import fr.kaeios.kpsl.gui.api.Point;
import fr.kaeios.kpsl.impl.sources.PeriodicArrivalSource;

public class DefaultPlacedComponentFactory implements PlacedComponentFactoryVisitor {

    @Override
    public PlacedComponent visitBuffer(Buffer buffer, Point pos) {
        return new BufferComponent(buffer, pos);
    }

    @Override
    public PlacedComponent visitService(Service service, Point pos) {
        return new ServiceComponent(service, pos);
    }

    @Override
    public PlacedComponent visitPeriodicArrivalSource(PeriodicArrivalSource src, Point pos) {
        return new ArrivalSourceComponent(src, pos);
    }

}
