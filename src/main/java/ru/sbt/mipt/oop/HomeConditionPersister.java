package ru.sbt.mipt.oop;

import java.io.IOException;

public interface HomeConditionPersister {
    public SmartHome readHome() throws IOException;
}