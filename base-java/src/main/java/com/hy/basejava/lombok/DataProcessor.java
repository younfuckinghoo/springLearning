package com.hy.basejava.lombok;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({"com.hy.*"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DataProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("-----------************########$$$$$$$$$$$$$$$$$$$$########************--------------------");
        for (TypeElement annotation : annotations) {
            System.out.println(annotation);
        }
        System.out.println(roundEnv);
        return false;
    }
}
