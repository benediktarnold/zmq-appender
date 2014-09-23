package com.enstratus.logstash;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jeromq.ZMQ;
import org.jeromq.ZMQ.Context;
import org.jeromq.ZMQ.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZMQAppenderConfigurationTest {
	static ZMQAppender appender;
	private static Context context;
	private static Socket receiver;
	
	@Before
	public void setup() {
        String zmqUrl;
		if(System.getProperty("zmqUrl") == null){
            zmqUrl = "tcp://127.0.0.1:20121";
        }else{
            zmqUrl = System.getProperty("zmqUrl");
        }
        //logger = Logger.getRootLogger();
        appender = new ZMQAppender();
        appender.setEndpoint(zmqUrl);
        appender.setThreshold(Level.TRACE);
        appender.setSocketType("push");


        context = ZMQ.context(2);
        receiver = context.socket(ZMQ.PULL);
        receiver.setLinger(1);
        receiver.bind(zmqUrl);

    }

    @After
    public void cleanUp(){
        receiver.close();
        context.term();
    }
	
	@Test
	public void configureLingerAndHWM(){
		appender.setLinger(2);
		appender.setHWM(2001);
		appender.activateOptions();
		
		Socket socket = appender.getSenderSocket();
		assertEquals("Linger is not configured correctly!",2, socket.getLinger());
		assertEquals("HWM is not configured correctly!",2001,socket.getSndHWM());
	}
	
	@Test
	public void defaults(){
		appender.activateOptions();
		
		Socket socket = appender.getSenderSocket();
		assertEquals(socket.getLinger() +" is not the default linger of -1!",-1, socket.getLinger());
		assertEquals(socket.getSndHWM() +" is not the default hwm of 2000!",2000,socket.getSndHWM());
		assertEquals("Default threads is not 1",1,appender.getThreads());
	}
}
