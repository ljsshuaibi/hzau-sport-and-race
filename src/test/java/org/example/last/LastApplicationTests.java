package org.example.last;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class LastApplicationTests {
	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void test(){
		System.out.println(redisTemplate);
		ValueOperations valueOperations = redisTemplate.opsForValue();
		HashOperations	hashOperations = redisTemplate.opsForHash();
		ListOperations listOperations = redisTemplate.opsForList();
		SetOperations setOperations = redisTemplate.opsForSet();
		ZSetOperations zSetOperations = redisTemplate.opsForZSet();
	}
	@Test
	public void test1(){
		redisTemplate.opsForValue().set("city","ljs");
		String	name = (String) redisTemplate.opsForValue().get("city");
		redisTemplate.opsForValue().set("code","1234",3, TimeUnit.MINUTES);
		System.out.println(name);
	}
}
