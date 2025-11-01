package fr.kaeios.kpsl.api;

import java.util.List;
import java.util.Optional;

public interface DispatchPolicy {

    Optional<Component> chooseOutput(List<Component> outputs);

}
