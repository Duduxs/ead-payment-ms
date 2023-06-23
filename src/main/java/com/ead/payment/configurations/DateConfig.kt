package com.ead.payment.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import java.time.format.DateTimeFormatter

class DateConfig {

    private val iso8601UTCFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val module = JavaTimeModule()
        module.addSerializer(
            LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(iso8601UTCFormat)
            )
        )

        return ObjectMapper()
            .registerModule(module)
    }
}