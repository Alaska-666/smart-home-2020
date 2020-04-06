package ru.sbt.mipt.oop.rc;

import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.SmartHome;

public class TurnOffLightCommand implements Command {
    private final SmartHome smartHome;

    public TurnOffLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        smartHome.execute(light -> {
            if (light instanceof Light) {
                ((Light) light).setOn(false);
            }
        });
    }
}
