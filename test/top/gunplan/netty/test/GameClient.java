/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class GameClient {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("666 game");
        frame.setBounds(0, 0, 700, 1000);
        JTextArea area = new JTextArea();
        JButton area1 = new JButton("666");
        area.setBounds(0, 0, 700, 400);
        area1.setBounds(0, 500, 700, 400);
        Socket ss = new Socket("127.0.0.1", 8855);
        area1.addActionListener(e -> {
            try {
                ss.getOutputStream().write("666".getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        frame.add(area);
        frame.add(area1);
        new Thread(() -> {
            try {
                InputStream is = ss.getInputStream();
                byte[] buffer = new byte[22];
                while (true) {
                    is.read(buffer);
                    area.append(new String(buffer));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        frame.show(true);
    }

}
