package ru.sbt.mipt.oop.rc;

import rc.RemoteControl;

import java.util.HashMap;
import java.util.Map;

public class SmartRemoteControl implements RemoteControl {
    private Map<String, Command> buttonCodeForCommandMap = new HashMap<>();

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        if (buttonCodeForCommandMap.containsKey(buttonCode)) {
            buttonCodeForCommandMap.get(buttonCode).execute();
        }
    }

    public void set(String buttonCode, Command cmd) {
        if (buttonCodeForCommandMap.containsKey(buttonCode)) {
            buttonCodeForCommandMap.replace(buttonCode, cmd);
        } else {
            buttonCodeForCommandMap.put(buttonCode, cmd);
        }
    }

    public Command get(String buttonCode) {
        return buttonCodeForCommandMap.get(buttonCode);
    }
}
