package com.example.kotlinspringpractice.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val objectMapper: ObjectMapper
) : WebMvcConfigurer {

    /**
     * 메시지 컨버터 목록을 확장하여 커스텀 컨버터 추가
     */
    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        // 커스텀 메시지 컨버터를 컨버터 목록의 가장 높은 우선순위로 설정
        converters.add(0, MyHttpMessageConverter(objectMapper))
    }
}