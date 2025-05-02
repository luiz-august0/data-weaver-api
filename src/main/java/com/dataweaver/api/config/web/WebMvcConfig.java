package com.dataweaver.api.config.web;

import com.dataweaver.api.config.web.interceptors.TenantInterceptor;
import com.dataweaver.api.config.web.interceptors.UserEntityManagerInterceptor;
import com.dataweaver.api.constants.Paths;
import com.dataweaver.api.controller.interfaces.IReportController;
import com.dataweaver.api.infrastructure.deserializers.EmptyStringAsNullModule;
import com.dataweaver.api.utils.enums.EnumDateFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    private final UserEntityManagerInterceptor userEntityManagerInterceptor;

    private static final long MAX_AGE_SECS = 3600;

    @Value(Paths.prefixPath)
    private String prefixPath;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(EnumDateFormat.YYYYMMDDTHHMMSS.getFormat());
        mapper.registerModule(new EmptyStringAsNullModule());

        final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(mapper);
        final ByteArrayHttpMessageConverter byteArrayConverter = new ByteArrayHttpMessageConverter();
        final ResourceHttpMessageConverter resourceConverter = new ResourceHttpMessageConverter();

        byteArrayConverter.setSupportedMediaTypes(
                List.of(MediaType.APPLICATION_PDF,
                        MediaType.APPLICATION_OCTET_STREAM,
                        MediaType.parseMediaType("text/csv"),
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")));


        converters.clear();
        converters.add(jsonConverter);
        converters.add(byteArrayConverter);
        converters.add(resourceConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(tenantInterceptor);
        registry.addInterceptor(userEntityManagerInterceptor).addPathPatterns(
                IReportController.PATH.replace(Paths.prefixPath, prefixPath) + IReportController.RESULT_PATH + "/**"
        );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE").maxAge(MAX_AGE_SECS);
    }

}