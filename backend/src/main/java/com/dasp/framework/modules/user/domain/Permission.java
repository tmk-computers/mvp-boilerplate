package com.dasp.framework.modules.user.domain;

import com.dasp.framework.core.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission extends BaseAuditEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;
}
