package tn.esprit.spring.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect 
@Component
public class TrackExecutionTime {

	private static final Logger l = LogManager.getLogger(TrackExecutionTime.class);

	
	@Around("@annotation(tn.esprit.spring.aspects.TrackExecTime)")
	public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
		long stratTime=System.currentTimeMillis();
		Object obj=pjp.proceed();
		long endTime=System.currentTimeMillis();
		l.info("Method %s exectued in %d" , pjp.getSignature() , (endTime-stratTime));
		return obj;
	}
	
}
