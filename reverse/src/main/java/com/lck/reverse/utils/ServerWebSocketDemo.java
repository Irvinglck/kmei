package com.lck.reverse.utils;

import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

@Slf4j
public class ServerWebSocketDemo extends WebSocketServer {

    public ServerWebSocketDemo()  {
    }

    public ServerWebSocketDemo(int port) {
        super(new InetSocketAddress(port));
        System.out.println("websocket Server start at port:"+port);
    }

    /**
     * 触发连接事件
     */
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        log.info("new connection === {}" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    /**
     *
     * 连接断开时触发关闭事件
     */
    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        log.info("websocket 关闭事件 {}"+ webSocket.getResourceDescriptor());
    }
    /**
     * 客户端发送消息到服务器时触发事件
     */
    @Override
    public void onMessage(WebSocket webSocket, String s) {
        log.info("you have a new message: {} " + s);
        //向客户端发送消息
        webSocket.send(s);

    }
    /**
     * 触发异常事件
     */
    @Override
    public void onError(WebSocket webSocket, Exception e) {
        //e.printStackTrace();
        if( webSocket != null ) {
            log.info("some errors like port binding failed may not be assignable to a specific websocket ");
            //some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {

    }
    /**
     * 启动服务端
     * @param args
     */
    public static void main(String[] args) {
        new ServerWebSocketDemo(8887).start();
    }
}
