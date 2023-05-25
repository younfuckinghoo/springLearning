package com.hy.basejava.lombok;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"com.hy.basejava.lombok.Data"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class DataProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("-----------************########$$$$$$$$$$$$$$$$$$$$########************--------------------");
        // SupportedAnnotationTypes 允许的所有注解
        for (TypeElement annotation : annotations) {
            // 这个注解类的限定名
            Name annotationQualifiedName = annotation.getQualifiedName();
            System.out.println("annotation qualifiedName ---->"+annotationQualifiedName);
            TypeMirror typeMirror = annotation.asType();

            // 获取这个注解的类
            System.out.println("annotation class ---->"+typeMirror.getClass());

            // 使用这个注解的所有的类
            Set<? extends Element> annotatedWith = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : annotatedWith) {
                if (element instanceof TypeElement){
                    TypeElement typeElement = (TypeElement) element;
                    Name typeElementQualifiedName = typeElement.getQualifiedName();
                    System.out.println("type typeElementQualifiedName------>"+typeElementQualifiedName);
                    // 使用注解的这个类的所有元素
                    List<? extends Element> enclosedElements = element.getEnclosedElements();
                    for (Element enclosedElement : enclosedElements) {
                        if(enclosedElement instanceof VariableElement){
                            VariableElement variableElement = (VariableElement) enclosedElement;
                            System.out.println("元素 Modifiers 限定符 --"+variableElement.getModifiers());
                            System.out.println("ConstantValue---"+variableElement.getConstantValue());
                            System.out.println("字段名称---"+variableElement.getSimpleName());
                            System.out.println("字段类型---"+variableElement.asType());

                        }
                        if(enclosedElement instanceof ExecutableElement){
                            ExecutableElement executableElement = (ExecutableElement) enclosedElement;
                            System.out.println("元素 Modifiers 限定符 --"+executableElement.getModifiers());
                            System.out.println("方法名称---"+executableElement.getSimpleName());
                            System.out.println("方法入参列表---"+executableElement.getParameters());
                            System.out.println("方法返回值---"+executableElement.getReturnType());
                        }

                    }
                }
            }

        }

        System.out.println("RoundEnv>>>>>>>>"+roundEnv);
        return false;
    }
}
