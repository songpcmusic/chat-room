package com.songpcmusic.chat;

import com.songpcmusic.chat.domain.Protocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by songpengcheng on 2017/4/29.
 */
public class TcpClient {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost", 9000), 5000);

            Protocol protocol = new Protocol();
            protocol.setHeaderLength(Protocol.HEADER_LENGTH);
            protocol.setVersion(Protocol.VERSION);
            protocol.setOperation(1);
            protocol.setExtra(1);
            protocol.setBody("test message");
            protocol.setPacketLength(Protocol.HEADER_LENGTH + protocol.getBody().getBytes().length);

            int packetLength = protocol.getBody().getBytes().length + Protocol.HEADER_LENGTH;
            ByteBuffer byteBuffer = ByteBuffer.allocate(packetLength);
            byteBuffer.putInt(packetLength);
            byteBuffer.putShort(Protocol.HEADER_LENGTH);
            byteBuffer.putShort(Protocol.VERSION);
            byteBuffer.putInt(protocol.getOperation());
            byteBuffer.putInt(protocol.getExtra());
            byteBuffer.put(protocol.getBody().getBytes());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            for (int i = 0; i < 100; i++) {
                out.write(byteBuffer.array());
                out.flush();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
