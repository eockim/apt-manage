package com.hj.aptmanage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="public.api")
@Data
public class ApiProperty {

    String aptListKey;
}
