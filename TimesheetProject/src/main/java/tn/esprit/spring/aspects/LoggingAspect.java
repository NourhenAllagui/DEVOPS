package tn.esprit.spring.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

	private static final Logger l = LogManager.getLogger(LoggingAspect.class);

	@Around("execution(* tn.esprit.spring.services.*.*(..))")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
	long start = System.currentTimeMillis();
	Object obj = pjp.proceed();
	long elapsedTime = System.currentTimeMillis() - start;
	if (elapsedTime > 3000) {
	l.info("METHOD %s TOOK LONGER THAN 3 SECONDS , Method execution time: %d milliseconds."
	, pjp.getSignature() , elapsedTime); }
	return obj;
	}
}
