package ru.sbt.mipt.oop.rc;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.HomeBuilder;
import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;

import static org.junit.jupiter.api.Assertions.*;

class TurnOffLightCommandTest {

    @Test
    void execute() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        Command cmd = new TurnOffLightCommand(smartHome);
        cmd.execute();
        smartHome.execute(light -> {
            if (light instanceof Light) {
                assertFalse(((Light) light).isOn());
            }
        });
    }
}