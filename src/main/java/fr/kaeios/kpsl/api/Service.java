package fr.kaeios.kpsl.api;

public interface Service extends Component {

    Request getCurrentRequest();
    DispatchPolicy getDispatchPolicy();

}
