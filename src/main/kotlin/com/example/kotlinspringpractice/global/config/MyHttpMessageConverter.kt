package com.example.kotlinspringpractice.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

/**
 * Custom HTTP MessageConverter
 *
 * `MappingJackson2HttpMessageConverter`를 확장하여, JSON 직렬화 및 역직렬화 시
 * Java 8의 날짜 및 시간 API를 지원하기 위한 커스터마이징 수행
 */
class MyHttpMessageConverter(
    objectMapper: ObjectMapper
) : MappingJackson2HttpMessageConverter(objectMapper) {

    init {
        objectMapper.registerModule(JavaTimeModule())
        setObjectMapper(objectMapper)
    }

    /**
     * 메시지를 작성할 수 있는지 확인
     * `MediaType`에 따라 메시지를 작성할 수 있는지 결정
     *
     * @param clazz 작성할 클래스 타입
     * @param mediaType 미디어 타입
     * @return 메시지 작성 가능 여부
     */
    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return canWrite(mediaType)
    }
}