package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.adapters.SensorEventHandlerAdapter;
import ru.sbt.mipt.oop.command.ProvisionalSensorCommandSender;
import ru.sbt.mipt.oop.command.SensorCommandSender;
import ru.sbt.mipt.oop.decorator.SecurityDecorator;
import ru.sbt.mipt.oop.event.SensorEventType;
import ru.sbt.mipt.oop.eventhandler.EventDoorHandler;
import ru.sbt.mipt.oop.eventhandler.EventHallDoorHandler;
import ru.sbt.mipt.oop.eventhandler.EventHandler;
import ru.sbt.mipt.oop.eventhandler.EventLightHandler;
import ru.sbt.mipt.oop.objects.SmartHome;
import ru.sbt.mipt.oop.signaling.Signaling;
import ru.sbt.mipt.oop.storage.HomeConditionGsonStorage;
import ru.sbt.mipt.oop.storage.HomeConditionStorage;

import java.util.*;
import java.util.stream.IntStream;


@Configuration
public class SmartHomeConfiguration {
    @Bean
    public HomeConditionStorage homeConditionStorage() {
        String filename = "smart-home-1.js";
        return new HomeConditionGsonStorage(filename);
    }

    @Bean
    public SmartHome smartHome(HomeConditionStorage homeConditionStorage) {
        SmartHome smartHome = homeConditionStorage.readHome();
        smartHome.addSignaling(new Signaling());
        return smartHome;
    }

    @Bean
    SensorCommandSender commandSender() {
        return new ProvisionalSensorCommandSender();
    }

    @Bean
    public EventHandler eventDoorHandler(SmartHome smartHome) {
        return new EventDoorHandler(smartHome);
    }

    @Bean
    public EventHandler eventLightHandler(SmartHome smartHome) {
        return new EventLightHandler(smartHome);
    }

    @Bean
    public EventHandler eventHallDoorHandler(SmartHome smartHome, SensorCommandSender commandSender) {
        return new EventHallDoorHandler(smartHome, commandSender);
    }

    @Bean
    public SensorEventType LightOn() {
        return SensorEventType.LIGHT_ON;
    }

    @Bean
    public SensorEventType LightOff() {
        return SensorEventType.LIGHT_OFF;
    }

    @Bean
    public SensorEventType DoorOpen() {
        return SensorEventType.DOOR_OPEN;
    }

    @Bean
    public SensorEventType DoorClosed() {
        return SensorEventType.DOOR_CLOSED;
    }

    @Bean
    public Map<String, SensorEventType> convertType(List<SensorEventType> sensorEventTypes){
        List<String> ccSensorEventTypes = Arrays.asList("LightIsOn", "LightIsOff", "DoorIsOpen", "DoorIsClosed");
        return IntStream.range(0, ccSensorEventTypes.size())
                .collect(
                        HashMap::new,
                        (m, i) -> m.put(ccSensorEventTypes.get(i), sensorEventTypes.get(i)),
                        Map::putAll
                );
    }

    @Bean
    public SensorEventsManager sensorEventsManager(SmartHome smartHome, List<EventHandler> eventHandlers, Map<String, SensorEventType> convertType) {
        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        sensorEventsManager.registerEventHandler(
                new SensorEventHandlerAdapter(
                        new SecurityDecorator(
                                eventHandlers,
                                smartHome.getSignaling()),
                        convertType
                )
        );
        return sensorEventsManager;
    }
}
