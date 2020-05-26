package com.liukun.annotation_compiler;

import com.google.auto.service.AutoService;
import com.liukun.annotation.BindPath;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;


/**
 * Author: liukun on 2020/5/26.
 * Mail  : 3266817262@qq.com
 * Description:
 */
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {
    Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    /**
     * 声明注解器的Java版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();

    }

    /**
     * 声明注解器要识别的注解有哪些
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("oooooooo");
        //获取当前模块中的用到了BindPath注解的Activity的类对象（也就是类节点）
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            BindPath annotation = typeElement.getAnnotation(BindPath.class);
            String key = annotation.value();
            //获取包名+类名
            Name activityName = typeElement.getQualifiedName();
            map.put(key, activityName + ".class");
        }


        //写文件；
        if (map.size() > 0) {
            Writer writer = null;
            //需要生成的类名，让类名不重复
            String activityName = "ActivityUtil" + System.currentTimeMillis();
            System.out.println(activityName+"sssssss");
            //生成Java文件
            try {
                JavaFileObject sourceFile = mFiler.createSourceFile("com.liukun.util."
                        + activityName);

                writer = sourceFile.openWriter();
                StringBuffer buffer = new StringBuffer();
                buffer.append("package com.liukun.util;\n");
                buffer.append("import com.liukun.arouter.ARouter;\n" +
                        "import com.liukun.arouter.IRouter;\n" +
                        "\n" + "public class " + activityName + " implements IRouter{\n" +
                        "  @Override\n" +
                        "  public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String className = map.get(key);
                    buffer.append("ARouter.getInstance().addActivity(\"" +
                            key + "\"," + className + ");\n");

                }
                buffer.append("\n}\n}");
                writer.write(buffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
