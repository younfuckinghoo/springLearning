package com.hy.validation.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Collection;

public class SpELParser {

    /**
     * 解析方式上的表达式
     * key 定义在注解上，支持SPEL表达式
     *
     * @param key
     * @param method
     * @param args
     * @return
     */
    public static <T> T parseMethodKey(String key, Method method, Object[] args, Class<T> t) {

        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL 上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }

        return parser.parseExpression(key).getValue(context, t);

    }

    /**
     * 解析方式上的表达式
     * key 定义在注解上，支持SPEL表达式
     *
     * @param key
     * @param method
     * @param args
     * @return
     */
    public static void setMethodValue(String key, Collection<String> values, Method method, Object[] args) {

        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL 上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        for (int i = 0; paraNameArr != null && i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
            parser.parseExpression(key).setValue(context, values);
        }


    }


    static class Student {
        private int id;
        private String name;
        private Student son;
        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Student getSon() {
            return son;
        }

        public void setSon(Student son) {
            this.son = son;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", son=" + son +
                    '}';
        }
    }

    public static void main(String[] args) {
        Student zs = new Student(1, "张三");
        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL 上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("zs", zs);
        Object zs1 = parser.parseExpression("#zs").getValue(context);
        System.out.println(zs1);
        parser.parseExpression("#zs.name").setValue(context,123123);
        System.out.println(zs);
    }


}
