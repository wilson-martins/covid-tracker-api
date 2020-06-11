package com.mc855.tracker.domain.auditable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Component
@Slf4j
public class AuditInterceptor {

    private final static String SYSTEM_USER = "System";

    @PreUpdate
    public void updateAuditableFields(final Auditable obj) {
        obj.getAudit().setUpdateTimestamp(new Date());
        obj.getAudit().setUpdateUser(SYSTEM_USER);
    }

    @PrePersist
    public void createAuditableFields(final Auditable obj) {
        Date now = new Date();

        if (obj.getAudit().getCreateTimestamp() == null) {
            obj.getAudit().setCreateTimestamp(now);
        }
        obj.getAudit().setUpdateTimestamp(now);

        if (obj.getAudit().getCreateUser() == null) {
            obj.getAudit().setCreateUser(SYSTEM_USER);
        }
        obj.getAudit().setUpdateUser(SYSTEM_USER);
    }
}
