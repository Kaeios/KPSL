package fr.kaeios.kpsl.impl;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.DispatchPolicy;
import fr.kaeios.kpsl.api.Request;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class BasicComponent implements Component {

    private final List<Component> outputs = new LinkedList<>();
    private final DispatchPolicy dispatchPolicy;

    protected BasicComponent(DispatchPolicy dispatchPolicy) {
        this.dispatchPolicy = dispatchPolicy;
    }

    @Override
    public List<Component> getOutputs() {
        return Collections.unmodifiableList(this.outputs);
    }

    @Override
    public DispatchPolicy getDispatchPolicy() {
        return this.dispatchPolicy;
    }

    @Override
    public void connectTo(Component component) {
        this.outputs.add(component);
    }

    protected Optional<Component> getSelectedOutput() {
        return this.dispatchPolicy.chooseOutput(this.outputs);
    }

}
