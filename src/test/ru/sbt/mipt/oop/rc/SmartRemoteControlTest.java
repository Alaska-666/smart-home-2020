package ru.sbt.mipt.oop.rc;

import org.junit.jupiter.api.Test;
import rc.RemoteControl;
import ru.sbt.mipt.oop.HomeBuilder;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.Room;
import ru.sbt.mipt.oop.objects.SmartHome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SmartRemoteControlTest {

    @Test
    void correctOnButtonPressed() {
        SmartHome smartHome = new SmartHome();
        Room room = new Room("hall");
        room.addDoors(Collections.singletonList(new Door(true, "1")));
        room.addLights(new ArrayList<>());
        smartHome.addRoom(room);
        Command cmd = new CloseHallDoorCommand(smartHome);
        SmartRemoteControl remoteControl = new SmartRemoteControl();
        remoteControl.set("A", cmd);
        remoteControl.onButtonPressed("A", "");
        smartHome.execute(door -> {
            if (door instanceof Door && ((Door) door).getId().equals("1")) {
                assertFalse(((Door) door).isOpen());
            }
        });
    }

    @Test
    void incorrectOnButtonPressed() {
        SmartHome smartHome = new SmartHome();
        Room room = new Room("hall");
        room.addDoors(Collections.singletonList(new Door(true, "1")));
        room.addLights(new ArrayList<>());
        smartHome.addRoom(room);
        Command cmd = new CloseHallDoorCommand(smartHome);
        SmartRemoteControl remoteControl = new SmartRemoteControl();
        remoteControl.set("A", cmd);
        remoteControl.onButtonPressed("B", "");
        smartHome.execute(door -> {
            if (door instanceof Door && ((Door) door).getId().equals("1")) {
                assertTrue(((Door) door).isOpen());
            }
        });
    }

    @Test
    void setNewButton() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        Command cmd = new CloseHallDoorCommand(smartHome);
        SmartRemoteControl remoteControl = new SmartRemoteControl();
        remoteControl.set("A", cmd);
        assertTrue(remoteControl.get("A") instanceof CloseHallDoorCommand);
    }

    @Test
    void updateButton() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        Command cmd = new CloseHallDoorCommand(smartHome);
        SmartRemoteControl remoteControl = new SmartRemoteControl();
        remoteControl.set("A", cmd);
        Command newCommand = new TurnOnHallLightCommand(smartHome);
        remoteControl.set("A", newCommand);
        assertTrue(remoteControl.get("A") instanceof TurnOnHallLightCommand);
    }
}