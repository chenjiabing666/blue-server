package com.techwells.blue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger的配置类
 * @author 陈加兵
 *
 */
@Configuration
public class SwaggerConfig {
	/**
	 * 创建用户API文档
	 * @return
	 */
	@Bean
	public Docket createRestUserApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())   //api的信息
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.techwells.blue"))   //扫描包
				.paths(PathSelectors.any()).build();

	}

	/**
	 * 创建API信息
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("蓝色按钮")   //标题
				.description("蓝色按钮的接口文档")  //描述  
				.contact(    //添加开发者的一些信息
						new Contact("爱撒谎的男孩", "https://chenjiabing666.github.io",
								"18796327106@163.com")).version("1.0").build();
	}

}
