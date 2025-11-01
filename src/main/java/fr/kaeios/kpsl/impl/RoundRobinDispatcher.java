package fr.kaeios.kpsl.impl;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.DispatchPolicy;

import java.util.List;
import java.util.Optional;

public class RoundRobinDispatcher implements DispatchPolicy {

    private static final RoundRobinDispatcher INSTANCE = new RoundRobinDispatcher();

    private int index = 0;

    @Override
    public Optional<Component> chooseOutput(List<Component> outputs) {
        if(outputs.isEmpty()) return Optional.empty();

        int size = outputs.size();

        for(int i = 0; i < size; i++) {
            Component component = outputs.get((index + i) % size);

            if(component == null) continue;
            if(component.isBusy()) continue;

            index = (index + i) % size;
            return Optional.of(component);
        }

        return Optional.empty();
    }

    public static RoundRobinDispatcher getInstance() {
        return INSTANCE;
    }

}
