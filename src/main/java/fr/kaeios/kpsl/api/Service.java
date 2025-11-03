package fr.kaeios.kpsl.api;

import java.util.List;

public interface Service extends Component {

    List<Request> getCurrentRequest();

}
