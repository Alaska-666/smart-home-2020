package ru.sbt.mipt.oop.rc;

import ru.sbt.mipt.oop.objects.Light;
import ru.sbt.mipt.oop.objects.Room;
import ru.sbt.mipt.oop.objects.SmartHome;

public class TurnOnHallLightCommand implements Command {
    private final SmartHome smartHome;

    public TurnOnHallLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        smartHome.execute(room -> {
            if (room instanceof Room) {
                ((Room) room).execute(light -> {
                    if (light instanceof Light && ((Room) room).getName().equals("hall")) {
                        ((Light) light).setOn(true);
                    }
                });
            }
        });
    }
}
