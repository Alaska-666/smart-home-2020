package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.event.SensorEvent;
import ru.sbt.mipt.oop.event.SensorEventType;
import ru.sbt.mipt.oop.eventhandler.EventHandler;

import java.util.Map;

public class SensorEventHandlerAdapter implements com.coolcompany.smarthome.events.EventHandler {
    private final EventHandler eventHandler;
    private final Map<String, SensorEventType> convertTypeMap;

    public SensorEventHandlerAdapter(EventHandler eventHandler, Map<String, SensorEventType> convertTypeMap) {
        this.eventHandler = eventHandler;
        this.convertTypeMap = convertTypeMap;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        SensorEventType type = convertTypeMap.get(event.getEventType());
        if (type == null) return;
        eventHandler.handleEvent(new SensorEvent(type, event.getObjectId()));
    }
}
