package ru.sbt.mipt.oop.rc;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.HomeBuilder;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.signaling.ActivatedStatus;
import ru.sbt.mipt.oop.signaling.DeactivatedStatus;
import ru.sbt.mipt.oop.signaling.Signaling;

import static org.junit.jupiter.api.Assertions.*;

class ActivateSignalingCommandTest {

    @Test
    void execute() {
        SmartHome smartHome = new HomeBuilder().getSmartHome();
        smartHome.addSignaling(new Signaling());
        assertTrue(smartHome.getSignaling().getStatus() instanceof DeactivatedStatus);
        ActivateSignalingCommand cmd = new ActivateSignalingCommand(smartHome, "abc");
        cmd.execute();
        assertTrue(smartHome.getSignaling().getStatus() instanceof ActivatedStatus);
    }
}