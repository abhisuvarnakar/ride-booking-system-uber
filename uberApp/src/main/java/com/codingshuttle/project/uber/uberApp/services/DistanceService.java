package com.codingshuttle.project.uber.uberApp.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {

    Double calculateDistance(Point source, Point destination);
}
