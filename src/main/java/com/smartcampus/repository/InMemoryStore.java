package com.smartcampus.repository;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStore {

    public static final Map<String, Room> rooms = new ConcurrentHashMap<>();
    public static final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    public static final Map<String, List<SensorReading>> sensorReadings = new ConcurrentHashMap<>();

    private InMemoryStore() {
        // Prevent instantiation
    }

    static {
        Room room1 = new Room("LIB-301", "Library Quiet Study", 80);
        Room room2 = new Room("LAB-101", "Computer Lab 101", 40);

        rooms.put(room1.getId(), room1);
        rooms.put(room2.getId(), room2);

        Sensor sensor1 = new Sensor("TEMP-001", "Temperature", "ACTIVE", 22.5, "LIB-301");
        Sensor sensor2 = new Sensor("CO2-001", "CO2", "MAINTENANCE", 410.0, "LAB-101");

        sensors.put(sensor1.getId(), sensor1);
        sensors.put(sensor2.getId(), sensor2);

        room1.getSensorIds().add(sensor1.getId());
        room2.getSensorIds().add(sensor2.getId());

        sensorReadings.put(sensor1.getId(), new ArrayList<>());
        sensorReadings.put(sensor2.getId(), new ArrayList<>());
    }
}
