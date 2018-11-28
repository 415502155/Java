package cn.edugate.esb.params;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;

public class EduJsonFilterAdvice {
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature msig = (MethodSignature) pjp.getSignature();
        EduJsonFilter annotation = msig.getMethod().getAnnotation(
                EduJsonFilter.class);
        EduJsonFilters annotations = msig.getMethod().getAnnotation(
                EduJsonFilters.class);
 
        if (annotation == null && annotations == null) {
            return pjp.proceed();
        }
 
        ObjectMapper mapper = new ObjectMapper();
        if (annotation != null) {
            Class<?> mixin = annotation.mixin();
            Class<?> target = annotation.target();
             
            if (target != null) {
                mapper.getSerializationConfig().addMixInAnnotations(target,
                        mixin);
            } else {
                mapper.getSerializationConfig().addMixInAnnotations(
                        msig.getMethod().getReturnType(), mixin);
            }
        }
         
        if (annotations != null) {
            EduJsonFilter[] filters= annotations.value();
            for(EduJsonFilter filter :filters){
                Class<?> mixin = filter.mixin();
                Class<?> target = filter.target();
                 
                if (target != null) {
                    mapper.getSerializationConfig().addMixInAnnotations(target,
                            mixin);
                } else {
                    mapper.getSerializationConfig().addMixInAnnotations(
                            msig.getMethod().getReturnType(), mixin);
                }
            }
             
        }
        WebContext.getInstance().getResponse().setContentType("application/json;charset=utf-8");

        try {
            mapper.writeValue(WebContext.getInstance().getResponse()
                    .getOutputStream(), pjp.proceed());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
