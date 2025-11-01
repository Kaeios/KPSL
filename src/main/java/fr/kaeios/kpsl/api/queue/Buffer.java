package fr.kaeios.kpsl.api.queue;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.Request;

import java.util.List;
import java.util.Optional;

public interface Buffer extends Component {

    int getPopulationCapacity();

    List<Request> getPopulation();

    Optional<Request> pollRequest();

}
