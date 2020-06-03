package com.example.nosqldemo.mysql.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * mybatis-plus 配置
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.plus.config
 * @description: mybatis-plus 配置
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 17:29
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@MapperScan(basePackages = {"com.example.nosqldemo.mysql.mapper"})
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
