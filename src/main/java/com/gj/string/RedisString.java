package com.gj.string;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Gjing
 **/
@Component
public class RedisString {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 往redis插入值，可以设置过期时间
     */
    public void set() {
        // 存入永久数据
        stringRedisTemplate.opsForValue().set("test2", "1");
        // 也可以向redis里存入数据和设置缓存时间
        stringRedisTemplate.opsForValue().set("test1", "hello redis", 1000, TimeUnit.SECONDS);
    }

    /**
     * 批量插入，key值存在会覆盖原值
     */
    public void multiSet() {
        Map<String,String> map = new HashMap<>(16);
        map.put("testMultiSet1", "value0");
        map.put("testMultiSet2", "value2");
        stringRedisTemplate.opsForValue().multiSet(map);
    }

    /**
     *  批量插入，如果里面的所有key都不存在，则全部插入，返回true，如果其中一个在redis中已存在，全不插入，返回false
     */
    public void multiSetIfAbsent() {
        Map<String,String> map = new HashMap<>(16);
        map.put("testMultiSet4", "value1");
        map.put("testMultiSet3", "value3");
        Boolean absent = stringRedisTemplate.opsForValue().multiSetIfAbsent(map);
        System.out.println(absent);
    }

    /**
     * 如果不存在则插入，返回true为插入成功,false失败
     */
    public void setIfAbsent() {
        Boolean absent = stringRedisTemplate.opsForValue().setIfAbsent("test", "hello redis");
        System.out.println(absent);
    }
    /**
     * 获取值,key不存在返回null
     */
    public void get() {
        System.out.println(stringRedisTemplate.opsForValue().get("testMultiSet1"));
    }

    /**
     * 批量获取，key不存在返回null
     */
    public void multiGet() {
        List<String> list = stringRedisTemplate.opsForValue().multiGet(Arrays.asList("test", "test2"));
        assert list != null;
        System.out.println(list.toString());
    }

    /**
     * 获取指定key的value长度
     */
    public void getLength() {
        Long size = stringRedisTemplate.opsForValue().size("test");
        System.out.println(size);
    }

    /**
     * 往指定key末尾追加，返回当前key的value大小，如果key不存在会新创建一个
     */
    public void append() {
        Integer append = stringRedisTemplate.opsForValue().append("test3", "database");
        System.out.println(append);
    }

    /**
     * 获取指定key，并重新设置值，返回旧值，如果key不存在会新创建一个
     */
    public void getAndSet() {
        String set = stringRedisTemplate.opsForValue().getAndSet("test", "set test");
        System.out.println(set);
    }

    /**
     * 获取指定key的值进行减1，如果value不是integer类型，会抛异常，如果key不存在会创建一个，默认value为0
     */
    public void decrement() {
        stringRedisTemplate.opsForValue().decrement("test2");
        stringRedisTemplate.opsForValue().decrement("test1");
    }

    /**
     * 获取指定key的值进行加1，如果value不是integer类型，会抛异常，如果key不存在会创建一个，默认value为0
     */
    public void increment() {
        stringRedisTemplate.opsForValue().increment("test2");
        stringRedisTemplate.opsForValue().increment("test1");
    }

    /**
     * 删除指定key,成功返回true，否则false
     */
    public void delete() {
        Boolean delete = stringRedisTemplate.opsForValue().getOperations().delete("test1");
        System.out.println(delete);
    }

    /**
     * 删除多个key，返回删除key的个数
     */
    public void deleteMulti() {
        Long delete = stringRedisTemplate.opsForValue().getOperations().delete(Arrays.asList("test1", "test2"));
        System.out.println(delete);
    }
}
