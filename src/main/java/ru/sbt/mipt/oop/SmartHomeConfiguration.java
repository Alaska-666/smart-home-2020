package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import rc.RemoteControlRegistry;
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
import ru.sbt.mipt.oop.rc.*;
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
    public Command activateSignalingCommand(SmartHome smartHome) {
        String code = "12345";
        return new ActivateSignalingCommand(smartHome, code);
    }

    @Bean
    public Command closeHallDoorCommand(SmartHome smartHome) {
        return new CloseHallDoorCommand(smartHome);
    }

    @Bean
    public Command turnOnHallLightCommand(SmartHome smartHome) {
        return new TurnOnHallLightCommand(smartHome);
    }

    @Bean
    public Command turnOnAlarmCommand(SmartHome smartHome) {
        return new TurnOnAlarmCommand(smartHome);
    }

    @Bean
    public Command turnOnLightCommand(SmartHome smartHome) {
        return new TurnOnLightCommand(smartHome);
    }

    @Bean
    public Command turnOffLightCommand(SmartHome smartHome) {
        return new TurnOffLightCommand(smartHome);
    }

    @Bean
    public RemoteControl remoteControl(Collection<Command> collections) {
        SmartRemoteControl remoteControl = new SmartRemoteControl();
        Iterator<Command> iterator = collections.iterator();
        remoteControl.set("A", iterator.next());
        remoteControl.set("B", iterator.next());
        remoteControl.set("C", iterator.next());
        remoteControl.set("D", iterator.next());
        remoteControl.set("1", iterator.next());
        remoteControl.set("2", iterator.next());
        return remoteControl;
    }

    @Bean
    public RemoteControlRegistry remoteControlRegistry(RemoteControl remoteControl) {
        String rcId = "newRemoteControl";
        RemoteControlRegistry remoteControlRegistry = new RemoteControlRegistry();
        remoteControlRegistry.registerRemoteControl(remoteControl, rcId);
        return remoteControlRegistry;
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
