package com.example.springboottaskmanager.aspect;

import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.model.Audit;
import com.example.springboottaskmanager.repository.AuditRepo;
import jakarta.validation.ConstraintViolationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;

@Component
@Aspect
public class AuditAspect {
    @Autowired
    AuditRepo auditRepo;

    @Pointcut("@annotation(Auditable)")
    public void auditableMethods() {
    }

    //Метод, создающий запись в таблице Audit после каждого вызова метода. Записывает тип метода через вызов getRequestType,
    //затем, если вызов метода возвращает исключение, записывает соответствующий HTTP статус и сообщение оишбки и сохраняет запись в Audit.
    //Если исключений не было, записывает статус 200 и сохраняет запись в Audit
    @Around("auditableMethods()")
    public Object CreateAudit(ProceedingJoinPoint jp) throws Throwable{
        Audit audit = new Audit();
        audit.setOperation(GetRequestType(jp));
        try {
            return jp.proceed();
        } catch (NotFoundException nfe) {
            audit.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            audit.setErrorMessage(nfe.getMessage());
            throw nfe;
        } catch (ConstraintViolationException cve) {
            audit.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            audit.setErrorMessage(cve.getMessage());
            throw cve;
        }catch (MethodArgumentNotValidException manve) {
            audit.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            audit.setErrorMessage(manve.getMessage());
            throw manve;
        }
        catch (Exception e) {
            audit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            UnknownException ue = new UnknownException(e.getMessage());
            audit.setErrorMessage(ue.getMessage());
            throw ue;
        } finally {
            if(audit.getStatus() == null)
                audit.setStatus(HttpStatus.OK.getReasonPhrase());
            auditRepo.save(audit);
        }
    }

    private String GetRequestType(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature)jp.getSignature();
        Method method = methodSignature.getMethod();

        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if(postMapping != null)
            return "POST";

        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if(getMapping != null)
            return "GET";

        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if(deleteMapping != null)
            return "Delete";

        return "N/A";
    }

/*    private String getParameters(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        String result = "";

        for(int i = 0; i < parameters.length; i++) {
            result += parameters[i].toString() + "\n";
        }
        return result;
    }*/
}
