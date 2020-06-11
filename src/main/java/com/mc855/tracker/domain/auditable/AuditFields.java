package com.mc855.tracker.domain.auditable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditFields implements Serializable {

    @Column(name = "create_user", updatable = false)
    private String createUser;
    @Column(name = "create_dt", updatable = false)
    private Date createTimestamp;

    @Column(name = "update_user")
    private String updateUser;
    @Column(name = "update_dt")
    private Date updateTimestamp;
}
