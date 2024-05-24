package com.test.usersapi.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("test")
@Configuration("apptest")
public class PropertiesConfig {
}
