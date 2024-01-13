package br.com.ticketservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AbstractAuditingEntity {

	@CreatedBy
	@Column(name = "created_by", nullable = false, length = 50, updatable = false)
	@JsonIgnore
	private String createdBy = "system";

	@CreatedDate
	@Column(name = "created_date", nullable = false)
	@JsonIgnore
	private LocalDateTime createdDate = LocalDateTime.now();

	@LastModifiedBy
	@Column(name = "last_modified_by", length = 50)
	@JsonIgnore
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	@JsonIgnore
	private LocalDateTime lastModifiedDate = LocalDateTime.now();

	@Column(nullable = false)
	private boolean deleted = false;

}
