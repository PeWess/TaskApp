package com.example.springboottaskmanager.aspect;

import com.example.springboottaskmanager.exception.body.NotFoundException;
import com.example.springboottaskmanager.exception.body.UnknownException;
import com.example.springboottaskmanager.exception.body.UserExitsException;
import com.example.springboottaskmanager.model.Audit;
import com.example.springboottaskmanager.repository.AuditRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;
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
    /**
     * Репозиторий записей аудита.
     */
    private final AuditRepo auditRepo;

    /**
     * Прослушиваем все методы, помеченные анотацией {@link Auditable}.
     */
    @Pointcut("@annotation(Auditable)")
    public void auditableMethods() {
    }

    /**
     * Метод создает новую запись для аудита, записывает в нее тип запроса через
     * метод {@link #getRequestType(ProceedingJoinPoint) GetRequestType},
     * затем вызывает оборачиваемый метод, и, если ловит исключение,
     * записывает код и тело ошибки и сохраняет запись в таблицу,
     * иначе оставляет поле с телом ошибки пустым,
     * а в таблицу добавляет запись со статусом 200(ОК).
     * @param jp {@link ProceedingJoinPoint}, точка, где применяется совет.
     * @return - Вызов оборачиваемого метода.
     * @throws - Любые возникающие в момент вызова оборачиваемого метода ошибки.
     */
    @Around("auditableMethods()")
    public Object createAudit(final ProceedingJoinPoint jp) throws Throwable {
        Audit audit = new Audit();
        audit.setOperation(getRequestType(jp));
        audit.setMethodName(jp.getSignature().getName());
        try {
            return jp.proceed();
        } catch (NotFoundException notFoundE) {
            audit.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            audit.setErrorMessage(notFoundE.getMessage());
            throw notFoundE;
        } catch (MethodArgumentNotValidException methodArgumentNotValidE) {
            audit.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            audit.setErrorMessage(methodArgumentNotValidE.getMessage());
            throw methodArgumentNotValidE;
        } catch (UserExitsException userExitsE) {
            audit.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());
            audit.setErrorMessage(userExitsE.getMessage());
            throw userExitsE;
        } catch (Exception e) {
            audit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            UnknownException unknownE = new UnknownException(e.getMessage());
            audit.setErrorMessage(unknownE.getMessage());
            throw unknownE;
        } finally {
            if (audit.getStatus() == null) {
                audit.setStatus(HttpStatus.OK.getReasonPhrase());
            }
            auditRepo.save(audit);
        }
    }

    /**
     * Метод возвращает вид обрабтываемого запроса.
     * @param jp - {@link ProceedingJoinPoint}
     * @return Вид запроса
     */
    private String getRequestType(final ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();

        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            return "POST";
        }

        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null) {
            return "GET";
        }

        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (deleteMapping != null) {
            return "Delete";
        }

        return "N/A";
    }
}
