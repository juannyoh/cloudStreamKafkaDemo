package com.dituhui.saas.alan.a.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dituhui.saas.websocket.MyEndpointConfigure;

//@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan(basePackages = {"com.dituhui.saas.websocket","com.dituhui.saas.alan.a.out" })
@RestController
public class SourceApplication {
	
	@Autowired
	private SendService service;
	
	 @Bean
    public MyEndpointConfigure newConfigure()
    {
        return new MyEndpointConfigure();
    }
	
	@RequestMapping(value = "/send/{msg}", method = RequestMethod.GET)
	public String send(@PathVariable("msg") String msg){
		service.sendMessage(msg);
		return "ok";
	}

	public static void main(String[] args) {

		String[] args2 = new String[]{"--server.port=8800","--spring.profiles.active=aout"};
		SpringApplication.run(SourceApplication.class, args2);

	}
	
//	@Bean
//    public CountDownLatch closeLatch() {
//        return new CountDownLatch(1);
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//    	String[] args2 = new String[]{"--server.port=8899","--spring.profiles.active=aout"};
//        ApplicationContext ctx = new SpringApplicationBuilder().sources(App.class).web(true).run(args2);
//        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
//        closeLatch.await();
//    }
	
}
