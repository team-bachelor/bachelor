package cn.org.bachelor.acm.dac.util;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassUtils {
    //包下的类(这⾥扫描了所有)
    private final static String CLASS_SUFFIX = "*.class";

    public static List<Class<?>> getClasses(String pack) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                org.springframework.util.ClassUtils.convertClassNameToResourcePath(pack) + CLASS_SUFFIX;
        List<Class<?>> list = new ArrayList<>();
        try {
            org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (org.springframework.core.io.Resource resource : resources) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                list.add(clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
