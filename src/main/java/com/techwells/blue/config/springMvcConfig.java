package com.techwells.blue.config;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;

/**
 * springmvc的配置类
 */
@Configuration
public class springMvcConfig implements WebMvcConfigurer {

    //注入返回Json格式的日期格式
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        ObjectMapper objectMapper=new ObjectMapper();  //创建ObjectMapper
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  //指定返回Json数据的日期格式
        MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();//创建转化器
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    //添加跨域的功能
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE",
								"OPTIONS")
                        .allowCredentials(false).maxAge(3600);
                ;   //对所有的路径都支持跨域的访问
            }
        };
    }
    
	/**
	 * 配置开启路径后缀匹配规则（但是配置好之后swagger的文档不能访问）
	 */
//	@Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        //开启路径后缀匹配
//        configurer.setUseRegisteredSuffixPatternMatch(true);
//    }
//	
//	  /**
//     * 设置匹配*.do后缀请求
//     * @param dispatcherServlet
//     * @return
//     */
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
//        servletServletRegistrationBean.addUrlMappings("*.do");
//        return servletServletRegistrationBean;
//    }
    
}
