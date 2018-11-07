package tools.swagger;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
/**
* 生成Markdown文件的类
*/
public class SwaggerExport {
	public static void main(String[] args) throws Exception {
	    Path outputFile = Paths.get("build/swagger");   //指定生成的目录和文件的名称swagger.md
	    Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
	            .withMarkupLanguage(MarkupLanguage.MARKDOWN)
	            .withOutputLanguage(Language.ZH)
	            .withPathsGroupedBy(GroupBy.TAGS)
	            .withGeneratedExamples()
	            .withoutInlineSchema()
	            .build();
	    Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(new URL("http://localhost:8080/demo/v2/api-docs"))   //url是可以访问的在线json数据的url
	            .withConfig(config)
	            .build();
	    converter.toFile(outputFile);
	}
}
