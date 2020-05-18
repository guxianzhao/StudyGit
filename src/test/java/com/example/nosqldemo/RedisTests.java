package com.example.nosqldemo;

import com.example.nosqldemo.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void StringTest() {
        String key = "redistest";
        String value = "value" + new Date();
        stringRedisTemplate.opsForValue().set(key, value);
        String getValue = stringRedisTemplate.opsForValue().get(key);
        System.out.println("StringRedisTemplate：" + getValue);

        String tempKey = "redistemptest";
        String tempValue = "value:" + new Date();
        redisTemplate.opsForValue().set(tempKey, tempValue);

        Object getTempValue = redisTemplate.opsForValue().get(key);
        System.out.println("RedisTemplate：" + getTempValue);
    }

    @Test
    void HashTest() {
        String tempKey = "redistest:hashtemp";
        List<String> list = GetStringList();
        for (Integer i = 0; i < 5; i++) {
            redisTemplate.opsForHash().put(tempKey, i.toString(), list.get(i));
        }

        System.out.println("RedisTemplate：" + redisTemplate.opsForHash().entries(tempKey));
        System.out.println("第三个元素：" + redisTemplate.opsForHash().get(tempKey, "2"));
    }

    @Test
    void ListTest() {
        String tempKey = "redistest:listtemp";
        redisTemplate.opsForList().rightPushAll(tempKey, GetStringList());
        List<String> list = redisTemplate.opsForList().range(tempKey, 99, -1);
        System.out.println("RedisTemplate：" + list);
        System.out.println("List的长度：" + redisTemplate.opsForList().size(tempKey));
        System.out.println("第三个元素：" + redisTemplate.opsForList().index(tempKey, 2));
    }

    List<String> GetStringList() {
        List<String> list = new ArrayList<String>();
        for (Integer i = 1; i < 6; i++) {
            list.add("第" + i.toString() + "个元素");
        }
        return list;
    }

    @Test
    void SetTest() {
        String tempKey = "redistest:settemp";
        redisTemplate.opsForSet().add(tempKey, 1, 2, "tex", 1, "1");
        System.out.println("RedisTemplate：" + redisTemplate.opsForSet().members(tempKey));
    }

    @Test
    void SortSetTest() throws InterruptedException {
        String tempKey = "redistest:sortsettemp";
        for (int i = 0; i < 5; i++) {
            // 插入相同的分数时，后进排名会更大，所以做排行榜的时候，需要优化一下
//            redisTemplate.opsForZSet().add(tempKey, String.valueOf((char) (65 + i)), 200);
            Thread.sleep(10);
            redisTemplate.opsForZSet().add(tempKey, String.valueOf((char) (65 + i)), encrypt(200));

//            redisTemplate.opsForZSet().add(tempKey, String.valueOf((char) (65 + i)), (long) (100 * Math.random()));
        }
        System.out.println("RedisTemplate：" + redisTemplate.opsForZSet().range(tempKey, 0, -1));

        Set<ZSetOperations.TypedTuple<String>> redisSet = redisTemplate.opsForZSet().reverseRangeWithScores(tempKey, 0, -1);
        for (ZSetOperations.TypedTuple<String> set : redisSet) {
            System.out.println("value为:" + set.getValue() + "、权重(score)为:" + set.getScore());
        }
        System.out.println("C排名是：" + redisTemplate.opsForZSet().reverseRank(tempKey, "C") + "，得分是：" + redisTemplate.opsForZSet().score(tempKey, "C"));
    }

    static Double encrypt(int coin) {
        double value = coin + (new Date(121, 0, 1, 0, 0, 0).getTime() - System.currentTimeMillis()) / Math.pow(10, 11);
        return value;
    }

    @Test
    void LockTest() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int num=i;
            new Thread(()-> {
                TestLockRedis(num==0);
            }).start();
        }
        Thread.sleep(3000);
    }
    void  TestLockRedis(boolean isSleep){
        String token = null;
        try{
            token = redisUtil.tryLock("lock_name", 10000);
            if(token != null) {
                System.out.println("我拿到了锁哦");
                if (isSleep){
                    Thread.sleep(1000);
                }
                // 执行业务代码
            } else {
                System.out.println("我没有拿到锁唉");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(token!=null) {
                redisUtil.unlock("lock_name", token);
            }
        }
    }
}