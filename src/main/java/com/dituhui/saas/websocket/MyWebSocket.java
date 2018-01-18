package com.dituhui.saas.websocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.dituhui.saas.alan.a.in.SpringUtils;

@ServerEndpoint(value = "/websocket/{ak}"/* ,configurator=MyEndpointConfigure.class */)
@Component
public class MyWebSocket {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("ak") String ak,Session session) {
		try {
			redisTemplate=(RedisTemplate<String, String>) SpringUtils.getBean("redisTemplate");
			
			InetAddress address = InetAddress.getLocalHost();
			System.out.println("ak:"+ak+"["+address.getHostAddress()+"]");
			this.session = session;
			redisTemplate.opsForValue().set(ak, address.getHostAddress());
			webSocketSet.add(this); // 加入set中
			addOnlineCount(); // 在线数加1
			System.out.println("["+address.getHostAddress()+"]有新连接加入！当前在线人数为" + getOnlineCount());
			sendMessage("11111");
		} catch (IOException e) {
			System.out.println("IO异常");
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(@PathParam("ak") String ak,String payload, Session session) {

		while (true) {
			try {
				payload = LogCacheUtils.take();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println("来自客户端的消息:" + payload);

			// 群发消息
			for (MyWebSocket item : webSocketSet) {
				try {
					item.sendMessage(payload);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 发生错误时调用
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	/**
	 * 群发自定义消息
	 * */
	public static void sendInfo(String message) throws IOException {
		for (MyWebSocket item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				continue;
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		MyWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		MyWebSocket.onlineCount--;
	}
}
