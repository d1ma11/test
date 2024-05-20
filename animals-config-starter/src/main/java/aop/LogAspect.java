package aop;

import annotation.Logging;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class LogAspect {

    private static final String ENTER = ">> {}";
    private static final String EXIT = "<< {}";

    @Around("@annotation(logging) && execution(public * *(..))")
    public Object execute(ProceedingJoinPoint joinPoint, Logging logging) throws Throwable {
        Logger LOGGER = LoggerFactory.getLogger(fetchJoinPointObject(joinPoint).getClass());

        String annotationValue = fetchAnnotationValue(joinPoint, logging);

        if (logging.entering()) {
            log(LOGGER, logging, ENTER, annotationValue);
        }

        Object result = joinPoint.proceed();

        if (logging.exiting()) {
            log(LOGGER, logging, EXIT, annotationValue);
        }

        return result;
    }

    private String fetchAnnotationValue(ProceedingJoinPoint joinPoint, Logging logging) {
        return Optional.ofNullable(logging)
                .map(Logging::value)
                .filter(StringUtils::isNotBlank)
                .orElse(joinPoint.getSignature().getName());
    }

    private Object fetchJoinPointObject(ProceedingJoinPoint joinPoint) {
        Object targetObject = joinPoint.getTarget();
        return targetObject != null ? targetObject : joinPoint.getThis();
    }

    private static void log(Logger logger, Logging logging, String entryOrExit, String annotationValue) {
        switch (logging.level()) {
            case "TRACE" -> logger.trace(entryOrExit, annotationValue);
            case "DEBUG" -> logger.debug(entryOrExit, annotationValue);
            case "INFO" -> logger.info(entryOrExit, annotationValue);
            case "WARN" -> logger.warn(entryOrExit, annotationValue);
            case "ERROR" -> logger.error(entryOrExit, annotationValue);
        }
    }

}
