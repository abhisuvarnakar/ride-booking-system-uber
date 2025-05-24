package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.codingshuttle.project.uber.uberApp.services.DistanceService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public Double calculateDistance(Point source, Point destination) {
        //TODO - Call the third party API OSRM.

        try {
            String uri =
                    source.getX() + "," + source.getY() + ";" + destination.getX() + "," + destination.getY();

            OSRMResponseDTO osrmResponseDTO = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDTO.class);

            return Objects.requireNonNull(osrmResponseDTO).getRoutes().get(0).getDistance() / 1000;
        } catch (Exception e) {
            throw new RuntimeConflictException("Error getting data from OSRM: " + e.getMessage());
        }
    }
}

class OSRMResponseDTO {

    private List<OSRMRoutes> routes;

    public List<OSRMRoutes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<OSRMRoutes> routes) {
        this.routes = routes;
    }
}

class OSRMRoutes {

    private Double distance;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
