package com.buinam.schedulemanger.model.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	// @Column(name = "status")
	// private String status;
	// @Column(name = "created_date", updatable = false, nullable = false)
	// @CreatedDate
	// private LocalDateTime createDate;
	// @Column(name = "updated_date", nullable = false)
	// @LastModifiedDate
	// private LocalDateTime updateDate;
	@Column(name = "create_user", updatable = false, nullable = false)
	private String createUser;
	@Column(name = "update_user", nullable = false)
	private String updateUser;
}

