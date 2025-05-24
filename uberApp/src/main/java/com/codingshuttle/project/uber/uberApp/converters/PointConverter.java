package com.codingshuttle.project.uber.uberApp.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

@Converter(autoApply = false)
public class PointConverter implements AttributeConverter<Point, String> {

    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    public String convertToDatabaseColumn(Point attribute) {
        if (attribute == null) return null;
        return new WKTWriter().write(attribute);
    }

    @Override
    public Point convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        try {
            return (Point) new WKTReader(factory).read(dbData);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to convert WKT to Point: " + dbData, e);
        }
    }
}
