package com.myron.ims.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 *
 * @author myron
 */
public class SpringSpelUtils {

    /**解析spel表达式*/
    private static ExpressionParser parser = new SpelExpressionParser();
    /**将方法参数纳入Spring管理*/
    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    /**
     * 解析spel表达式
     * @param method 方法
     * @param args 获取参数对象数组
     * @param template spel表达式
     * @return
     */
    public static String parse(Method method, Object[] args, String template) {
        //获取方法参数名
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(template);
        String spelDescription = expression.getValue(context, String.class);
        return spelDescription;
    }
}
