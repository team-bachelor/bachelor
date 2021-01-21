package cn.org.bachelor.common.auth;


import cn.org.bachelor.common.auth.annotation.AcmDomain;
import cn.org.bachelor.common.auth.controller.AuthorizeController;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TKGenerator
 * @Description:
 * @Author Alexhendar
 * @Date 2018/10/9 12:38
 * @Version 1.0
 **/
@AcmDomain(value = "1231231", tags = {"2222222"})
public class TKGenerator {

    public static InputStream getResourceAsStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    @Test
    public void test(){
        Api ann = AnnotationUtils.findAnnotation(getClass(),
                Api.class);
        AcmDomain ann1 = AnnotationUtils.findAnnotation(getClass(),
                AcmDomain.class);
        System.out.println(ann);
        System.out.println(ann1);
    }

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(getResourceAsStream("generatorTKConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
