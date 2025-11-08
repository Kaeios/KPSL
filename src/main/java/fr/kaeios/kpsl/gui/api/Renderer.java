package fr.kaeios.kpsl.gui.api;

import fr.kaeios.kpsl.api.Component;

import java.awt.*;

public interface Renderer<T extends Component> {

    Image render(T component);

}
