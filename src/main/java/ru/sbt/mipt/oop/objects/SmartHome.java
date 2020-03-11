package ru.sbt.mipt.oop.objects;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome{
    private Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public Light findLightByID(Room room, String id) {
        if (room == null) return null;
        for (Light light : room.getLights()) {
            if (light.getId().equals(id)) {
                return light;
            }
        }
        return null;
    }

    public Room findRoomByLight(String id) {
        for (Room room : rooms) {
            for (Light light : room.getLights()) {
                if (light.getId().equals(id)) {
                    return room;
                }
            }
        }
        return null;
    }

    public Room findRoomByDoor(String id) {
        for (Room room : rooms) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id)) {
                    return room;
                }
            }
        }
        return null;
    }

    public Door findDoorByID(Room room, String id) {
        if (room == null) return null;
        for (Door door : room.getDoors()) {
            if (door.getId().equals(id)) {
                return door;
            }
        }
        return null;
    }
}
