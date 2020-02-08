package com.jfbian.jedisdemo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
* @ClassName:  JedisTest
* @Description:测试jedis
* @author: bianjianfeng
* @date:   2020年1月24日 下午12:03:57
*/
public class JedisTest {
    @Test
    public void test() throws Exception {
        //获取jedis连接
        final Jedis jedis = new Jedis("192.168.17.132");

        //设置String类型的数据
        jedis.set("name", "设置的String数值");
        System.out.println(jedis.get("name"));

        //设置有存在10秒，自动删除的数据
        jedis.setex("activeCode", 10, "激活码");
        System.out.println(jedis.get("activeCode"));
        Thread.sleep(10_000);
        System.out.println(jedis.get("activeCode"));

        //关闭jedis连接
        jedis.close();
    }

    /**
     * hash 数据结构操作
     */
    @Test
    public void test3() {
        //1. 获取连接
        final Jedis jedis = new Jedis("192.168.17.132");//如果使用空参构造，默认值 "localhost",6379端口
        //2. 操作
        // 存储hash
        jedis.hset("user", "name", "lisi");
        jedis.hset("user", "age", "23");
        jedis.hset("user", "gender", "female");

        // 获取hash
        final String name = jedis.hget("user", "name");
        System.out.println(name);

        // 获取hash的所有map中的数据
        final Map<String, String> user = jedis.hgetAll("user");

        // keyset
        final Set<String> keySet = user.keySet();
        for (final String key : keySet) {
            //获取value
            final String value = user.get(key);
            System.out.println(key + ":" + value);
        }

        //3. 关闭连接
        jedis.close();
    }

    /**
     * list 数据结构操作
     */
    @Test
    public void test4() {
        //1. 获取连接
        final Jedis jedis = new Jedis("192.168.17.132");//如果使用空参构造，默认值 "localhost",6379端口
        //2. 操作
        // list 存储
        jedis.lpush("mylist", "a", "b", "c");//从左边存
        jedis.rpush("mylist", "a", "b", "c");//从右边存

        // list 范围获取
        final List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);

        // list 最左侧 弹出
        final String element1 = jedis.lpop("mylist");//c
        System.out.println(element1);

        // list 最右侧 弹出
        final String element2 = jedis.rpop("mylist");//c
        System.out.println(element2);

        // list 范围获取
        final List<String> mylist2 = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist2);

        //3. 关闭连接
        jedis.close();
    }

    /**
     * set 数据结构操作
     */
    @Test
    public void test5() {
        //1. 获取连接
        final Jedis jedis = new Jedis("192.168.17.132");//如果使用空参构造，默认值 "localhost",6379端口
        //2. 操作

        // set 存储
        jedis.sadd("myset", "java", "php", "c++");

        // set 获取
        final Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);

        //3. 关闭连接
        jedis.close();
    }

    /**
     * sortedset 数据结构操作
     */
    @Test
    public void test6() {
        //1. 获取连接
        final Jedis jedis = new Jedis("192.168.17.132");//如果使用空参构造，默认值 "localhost",6379端口
        //2. 操作
        // sortedset 存储
        jedis.zadd("mysortedset", 3, "亚瑟");
        jedis.zadd("mysortedset", 30, "后裔");
        jedis.zadd("mysortedset", 55, "孙悟空");

        // sortedset 获取
        final Set<String> mysortedset = jedis.zrange("mysortedset", 0, -1);

        System.out.println(mysortedset);

        //3. 关闭连接
        jedis.close();
    }

    /**
     * jedis连接池使用
     */
    @Test
    public void test7() {

        //0.创建一个配置对象
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(10);

        //1.创建Jedis连接池对象
        final JedisPool jedisPool = new JedisPool(config, "192.168.17.132", 6379);

        //2.获取连接
        final Jedis jedis = jedisPool.getResource();
        //3. 使用
        jedis.set("hehe", "heihei");

        //4. 关闭 归还到连接池中
        jedis.close();

    }

    /**
     * jedis连接池工具类使用
     */
    @Test
    public void test8() {

        //通过连接池工具类获取
        final Jedis jedis = JedisPoolUtils.getJedis();

        //3. 使用
        jedis.set("hello", "world");

        //4. 关闭 归还到连接池中
        jedis.close();

    }
}
