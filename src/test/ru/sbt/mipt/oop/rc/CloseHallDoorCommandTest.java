package ru.sbt.mipt.oop.rc;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.HomeBuilder;
import ru.sbt.mipt.oop.objects.Door;
import ru.sbt.mipt.oop.objects.Room;
import ru.sbt.mipt.oop.objects.SmartHome;

import static org.junit.jupiter.api.Assertions.*;

class CloseHallDoorCommandTest {

    @Test
    void execute() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        Command cmd = new CloseHallDoorCommand(smartHome);
        cmd.execute();
        smartHome.execute(room -> {
            if (room instanceof Room && ((Room) room).getName().equals("hall")) {
                ((Room) room).execute(door -> {
                    if (door instanceof Door) {
                        assertFalse(((Door) door).isOpen());
                    }
                });
            }
        });
    }
}