package ru.sbt.mipt.oop.rc;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.HomeBuilder;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;

import static org.junit.jupiter.api.Assertions.*;

class TurnOnLightCommandTest {

    @Test
    void execute() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        Command cmd = new TurnOnLightCommand(smartHome);
        cmd.execute();
        smartHome.execute(light -> {
            if (light instanceof Light) {
                assertTrue(((Light) light).isOn());
            }
        });
    }
}