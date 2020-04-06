package ru.sbt.mipt.oop.rc;

import ru.sbt.mipt.oop.objects.SmartHome;

public class ActivateSignalingCommand implements Command {
    private final SmartHome smartHome;
    private final String code;

    public ActivateSignalingCommand(SmartHome smartHome, String code) {
        this.smartHome = smartHome;
        this.code = code;
    }

    @Override
    public void execute() {
        smartHome.getSignaling().activateSignaling(code);
    }
}
