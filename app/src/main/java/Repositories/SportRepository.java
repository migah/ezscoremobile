package Repositories;

import java.util.List;

import Entities.Sport;
import Gateways.SportGateway;

/**
 * Created by michaelfranch on 08/05/2017.
 */

public class SportRepository {
    private final SportGateway sportGateway;

    /**
     * Constructor with reference to SportGateway
     */
    public SportRepository(){
        sportGateway = new SportGateway();
    }

    /**
     * Returns a list of sport from database through SportGateway
     * @return list of sports
     */
    public List<Sport> getSports() {
        return sportGateway.getSports();
    }
}
