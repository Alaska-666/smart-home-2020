package ru.sbt.mipt.oop.rc;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.HomeBuilder;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.Room;
import ru.sbt.mipt.oop.objects.SmartHome;

import static org.junit.jupiter.api.Assertions.*;

class TurnOnHallLightCommandTest {

    @Test
    void execute() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        Command cmd = new TurnOnHallLightCommand(smartHome);
        cmd.execute();
        smartHome.execute(room -> {
            if (room instanceof Room && ((Room) room).getName().equals("hall")) {
                ((Room) room).execute(light -> {
                    if (light instanceof Light) {
                        assertTrue(((Light) light).isOn());
                    }
                });
            }
        });
    }
}