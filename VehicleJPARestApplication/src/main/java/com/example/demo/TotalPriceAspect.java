package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
@Aspect
public class TotalPriceAspect {


    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(public * com.example..*(..))")
    public void allMethods() {

    }

    @Around("allMethods() && @annotation(TotalPrice)")
    public Object profile(final ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            final Object value = joinPoint.proceed();
            return value;
        } catch (Throwable t) {
            throw t;
        } finally {
            Query query = entityManager.createNativeQuery("SELECT ROUND(SUM(retailPrice)) FROM vehicle;");
            System.out.println();
            System.out.println();
            System.out.println(" *********MY ASPECT*********  $" + query.getResultList().get(0) +" IS THE " +
                    "TOTAL AMOUNT FOR ALL THE VEHICLES " + joinPoint.getSignature().getName() + ".");
            System.out.println();
            System.out.println();
        }
    }

}