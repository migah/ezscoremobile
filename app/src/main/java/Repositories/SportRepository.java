package Repositories;

import java.util.List;

import Entities.Sport;
import Gateways.SportGateway;

/**
 * Created by michaelfranch on 08/05/2017.
 */

public class SportRepository {
    private final SportGateway sportGateway;

    public SportRepository(){
        sportGateway = new SportGateway();
    }

    public List<Sport> getSports() {
        return sportGateway.getSports();
    }
}
