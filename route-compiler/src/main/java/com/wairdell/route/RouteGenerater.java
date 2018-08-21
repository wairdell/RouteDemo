package com.wairdell.route;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

public class RouteGenerater {

    private List<RouteAnnotation> routeAnnotationList;

    private Messager messager;

    public static ClassName CONTEXT_CLASS = ClassName.get("android.content", "Context");

    public static ClassName INTENT_CLASS = ClassName.get("android.content", "Intent");

    public static ClassName BUNDLE_CLASS = ClassName.get("android.os", "Bundle");

    public static ClassName LOG_CLASS = ClassName.get("android.util", "Log");


    public RouteGenerater(List<RouteAnnotation> routeAnnotationList, Messager messager) {
        this.routeAnnotationList = routeAnnotationList;
        this.messager = messager;
    }

    public JavaFile generateJavaFile() {
        //创建一个名为RouteCenter类
        TypeSpec.Builder classBuilder = TypeSpec
                .classBuilder("RouteCenter")
                .addModifiers(Modifier.PUBLIC);

        String addressParam = "address";
        String contextParam = "context";
        String extrasParam = "extras";

        MethodSpec.Builder methodBuilder = MethodSpec
                .methodBuilder("startActivity") //方法名为startActivity
                .addModifiers(Modifier.PUBLIC) //添加修饰符 public
                .addModifiers(Modifier.STATIC) //添加修饰符 static
                .addParameter(CONTEXT_CLASS, contextParam) //添加 类型为android.content.Context 名字为context 的参数
                .addParameter(String.class, addressParam) //添加 类型为java.lang.String 名字为 address 的参数
                .addParameter(BUNDLE_CLASS, extrasParam) //添加 类型为android.os.Bundle 名字为 extras 的参数
                .returns(Void.TYPE); //添加返回值
        String intentVariable = "intent";
        //开始声明方法内的代码
        CodeBlock.Builder codeBuilder = CodeBlock
                .builder()
                .addStatement("$T $N = new $T()", INTENT_CLASS, intentVariable, INTENT_CLASS) //添加一个局部变量 intent
                .beginControlFlow("switch($N)", addressParam); //添加一个switch语句
        String caseName;
        String address;
        //for循环往switch添加case
        for (RouteAnnotation routeAnnotation : routeAnnotationList) {
            address = routeAnnotation.getAddress();
            //如果Route注解里没有填入值，则用类名作为case 条件
            caseName = (address != null && !"".equals(address)) ? address : routeAnnotation.getClassSimpleName();
            //添加代码 intent.setClass(context, class) class是被Route注解声明的Activity
            codeBuilder.
                    addStatement("case $S:\n$N.setClass($N, $T.class);\nbreak", caseName, intentVariable, contextParam, routeAnnotation.getClassType());

        }
        //添加代码 没找到路由打印日志
        codeBuilder.addStatement("default:\n$T.e($S, $S + \" \" + $N);\nbreak", LOG_CLASS, "RouteCenter", "not found Activity by route address", addressParam).endControlFlow();
        //添加代码intent.putExtras(extras);
        codeBuilder.beginControlFlow("if($N != null)", extrasParam).addStatement("$N.putExtras($N)", intentVariable, extrasParam).endControlFlow();
        //添加代码context.startActivity(intent)
        codeBuilder.addStatement("$N.startActivity($N)", contextParam, intentVariable);


        methodBuilder.addCode(codeBuilder.build()); //往方法里添加代码
        classBuilder.addMethod(methodBuilder.build()); //往类里添加代码

        //最后通过包名和类生成 Java 文件
        return JavaFile.builder("com.wairdell.route", classBuilder.build()).build();
    }

}
