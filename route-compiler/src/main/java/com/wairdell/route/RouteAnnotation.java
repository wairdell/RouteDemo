package com.wairdell.route;

import com.wairdell.route.annotation.Route;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class RouteAnnotation {


    private final String address;
    private final String className;
    private final String classSimpleName;
    private final TypeMirror typeMirror;

    public RouteAnnotation(Element element) {
        TypeElement typeElement = (TypeElement) element; //因为我们声明的注解Route是作用在类上，这里把Element的强转为TypeElement
        className = typeElement.getQualifiedName().toString();
        classSimpleName = typeElement.getSimpleName().toString(); //得到被Route注解声明类的名字
        typeMirror = typeElement.asType(); //得到被Route注解声明类的类型
        address = element.getAnnotation(Route.class).value(); //得到Route注解时里面的value
    }

    public String getAddress() {
        return address;
    }

    public TypeMirror getClassType() {
        return typeMirror;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }
}
