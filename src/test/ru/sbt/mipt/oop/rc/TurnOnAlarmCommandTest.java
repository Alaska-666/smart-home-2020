package ru.sbt.mipt.oop.rc;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.HomeBuilder;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.signaling.AlarmStatus;
import ru.sbt.mipt.oop.signaling.Signaling;

import static org.junit.jupiter.api.Assertions.*;

class TurnOnAlarmCommandTest {

    @Test
    void execute() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        smartHome.addSignaling(new Signaling());
        smartHome.getSignaling().activateSignaling("abc");
        Command cmd = new TurnOnAlarmCommand(smartHome);
        cmd.execute();
        assertTrue(smartHome.getSignaling().getStatus() instanceof AlarmStatus);
    }
}