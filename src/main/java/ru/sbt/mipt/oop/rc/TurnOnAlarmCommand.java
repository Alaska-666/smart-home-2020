package ru.sbt.mipt.oop.rc;

import ru.sbt.mipt.oop.objects.SmartHome;

public class TurnOnAlarmCommand implements Command {
    private final SmartHome smartHome;

    public TurnOnAlarmCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        smartHome.getSignaling().turnOnAlarm();
    }
}
