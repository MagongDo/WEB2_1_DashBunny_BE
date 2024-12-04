/*
package com.devcourse.web2_1_dashbunny_be.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultipartJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        this.setSupportedMediaTypes(List.of(
                MediaType.MULTIPART_FORM_DATA,
                MediaType.APPLICATION_JSON
        ));
    }
}
*/
