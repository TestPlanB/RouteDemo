package com.example.lib;

import com.example.annotation.Route;
import com.google.auto.service.AutoService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;



//注解处理器//@SupportedAnnotationTypes("com.example.routerapi.Route")
@AutoService(Process.class)
@SupportedAnnotationTypes("com.example.annotation.Route")
@SupportedOptions("moduleName")
public class RouterProcessor extends AbstractProcessor {
    //记录模块名
    private String moduleName;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Map<String, String> options = processingEnvironment.getOptions();
        moduleName = options.get("moduleName");

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        String value="";
        Messager messager = processingEnv.getMessager();
        String className="";
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(Route.class);
        for (Element element : elementsAnnotatedWith) {
            value = element.getAnnotation(Route.class).value();
            className = element.toString();

            messager.printMessage(Diagnostic.Kind.NOTE,"-------------");
            messager.printMessage(Diagnostic.Kind.NOTE,className);


        }



        if(value.equals("")){
            return false;
        }
        //自动生成类的源代码
        String code="package com.test.output;\n" +
                "\n" +
                "import android.app.Activity;\n" +
                "\n" +
                "import com.example.route.Router;\n" +
                "\n" +
                "import com.example.route.IRouterLoad;\n" +
                "\n" +
                "import java.util.Map;\n" +
                "\n" +
                "public class "+moduleName.toUpperCase()+"Route implements IRouterLoad {\n" +
                "    @Override\n" +
                "    public void loadInfo() {\n" +
                "         Router.getInstance().register("+"\""+value+"\""+","+"\""+className+"\""+");" +
                "    }\n" +
                "}";
        //通过环境获取当前Filer，具有操作文件功能，避免写死路径
        Filer filer = processingEnv.getFiler();
        try {
            //因为有一个注解就执行一次process，所以同一个模块下有标志文件就不需要再创建了
            if(new File("com.test.output."+moduleName.toUpperCase()+"Route").exists()){
                return false;
            }
            JavaFileObject sourceFile = filer.createSourceFile("com.test.output."+moduleName.toUpperCase()+"Route");
            OutputStream outputStream = sourceFile.openOutputStream();
            outputStream.write(code.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }
}