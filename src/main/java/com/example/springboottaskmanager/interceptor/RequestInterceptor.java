/*
package com.example.springboottaskmanager.Interceptor;

import com.example.springboottaskmanager.model.Audit;
import com.example.springboottaskmanager.repository.AuditRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Autowired
    AuditRepo auditRepo;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Audit audit = new Audit();
        audit.setRequestSessionId(request.getRequestedSessionId());
        audit.setOperation(request.getMethod());
        audit.setStatusCode(response.getStatus());
        auditRepo.save(audit);
    }
}
*/
