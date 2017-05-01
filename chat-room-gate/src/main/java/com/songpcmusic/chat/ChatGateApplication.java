package com.songpcmusic.chat;

import com.songpcmusic.chat.server.TcpChatServer;
import com.songpcmusic.chat.server.WebSocketChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

/**
 * Created by songpengcheng on 2017/4/29.
 */
@SpringBootApplication
@ComponentScan("com.songpcmusic.chat")
public class ChatGateApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(ChatGateApplication.class, args);
    }

    @Autowired
    private TcpChatServer tcpChatServer;

    @Autowired
    private WebSocketChatServer webSocketChatServer;

    public void run(String... strings) throws Exception {
        tcpChatServer.start();
        webSocketChatServer.start();
    }
}
