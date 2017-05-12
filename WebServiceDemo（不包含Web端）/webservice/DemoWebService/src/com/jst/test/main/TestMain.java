package com.jst.test.main;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jst.common.service.CacheService;
import com.jst.common.utils.RedisUtil;
import com.jst.test.service.TestService;

import redis.clients.jedis.Jedis;

public class TestMain {
	
	
	public static ClassPathXmlApplicationContext ctx  = null;
	
	public static void main(String[] args) throws Exception {
		ctx = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
		CacheService testService = (CacheService) ctx.getBean("cacheService");
		Jedis jedis = RedisUtil.getRedis();
		System.out.println(jedis.dbSize());
		//testService.getMenu();
		//estService.
		/*List list = testService.getAllList();
		System.out.println("aaa");*/
		//testService.getMenu();
	/*	testService.get(22);
		System.out.println("----------------------");*/
		//testService.getMenu();
		
	}
}
