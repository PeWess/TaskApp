package com.example.springboottaskmanager.aspect;

import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.model.Audit;
import com.example.springboottaskmanager.repository.AuditRepo;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;


/**
 * Класс, заполняющий и добавляющий апись для аудита.
 */
@Component
@RequiredArgsConstructor
@Aspect
public class AuditAspect {
    final private AuditRepo auditRepo;

    /**
     * Прослушиваем все методы, помеченные анотацией {@link Auditable}.
     */
    @Pointcut("@annotation(Auditable)")
    public void auditableMethods() {
    }

    /**
     * Метод создает новую запись для аудита, записывает в нее тип запроса через метод {@link #GetRequestType(ProceedingJoinPoint) GetRequestType},
     * затем вызывает оборачиваемый метод, и, если ловит исключение, записывает код и тело ошибки и сохраняет запись в таблицу, иначе оставляет поле с телом ошибки пустым,
     * а в таблицу добавляет запись со статусом 200(ОК).
     * @param jp - {@link ProceedingJoinPoint}, точка в программе, где применяется совет.
     * @return - Вызов оборачиваемого метода.
     * @throws - Любые возникающие в в момент вызова оборачиваемого метода ошибки.
     */
    @Around("auditableMethods()")
    public Object CreateAudit(ProceedingJoinPoint jp) throws Throwable{
        Audit audit = new Audit();
        audit.setOperation(GetRequestType(jp));
        try {
            return jp.proceed();
        } catch (NotFoundException notFoundException) {
            audit.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            audit.setErrorMessage(notFoundException.getMessage());
            throw notFoundException;
        } catch (ConstraintViolationException constraintViolationException) {
            audit.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            audit.setErrorMessage(constraintViolationException.getMessage());
            throw constraintViolationException;
        }catch (MethodArgumentNotValidException methodArgumentNotValidException) {
            audit.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            audit.setErrorMessage(methodArgumentNotValidException.getMessage());
            throw methodArgumentNotValidException;
        }
        catch (Exception e) {
            audit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            UnknownException unknownException = new UnknownException(e.getMessage());
            audit.setErrorMessage(unknownException.getMessage());
            throw unknownException;
        } finally {
            if(audit.getStatus() == null)
                audit.setStatus(HttpStatus.OK.getReasonPhrase());
            auditRepo.save(audit);
        }
    }

    /**
     * Метод возвращает вид обрабтываемого запроса
     * @param jp - {@link ProceedingJoinPoint}
     * @return Вид запроса
     */
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
}
