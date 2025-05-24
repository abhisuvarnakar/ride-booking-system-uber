package com.cs.project.uber.uberApp.repositories;

import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    // ST_Distance(point1, point2)
    // ST_DWithin(point1, 10000)
    @Query(value = "with driver as (" +
            " SELECT d.*, ST_Distance(d.current_location, :pickupLocation) as distance" +
            " FROM t_driver d" +
            " where d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 10000)" +
            " )" +
            " SELECT driver.* from driver" +
            " ORDER BY distance LIMIT 10", nativeQuery = true)
    List<Driver> findTenNearestDrivers(Point pickupLocation);

    @Query(value = "SELECT d.*" +
            " FROM t_driver d" +
            " WHERE d.available = true" +
            " AND ST_DWithin(d.current_location, :pickupLocation, 15000)" +
            " ORDER BY d.rating DESC LIMIT 10", nativeQuery = true)
    List<Driver> findTenNearByTopRatedDrivers(Point pickupLocation);

    Optional<Driver> findByUser(User user);
}
