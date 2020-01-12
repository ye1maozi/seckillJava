package com.learn.seckill.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.LOCAL_VARIABLE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {

    boolean required() default true;//默认不能为空

    String message() default "手机号码格式错误";//校验不通过输出信息


    Class<?>[]groups() default { };  // 约束注解在验证时所属的组别

    Class<?extends Payload>[] payload() default { }; // 约束注解的有效负载
}
