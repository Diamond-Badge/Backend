package org.diamond_badge.footprint.jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "STICKER")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Sticker {
	@JsonIgnore
	@Id
	@Column(name = "STICKER_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stickerSeq;

	@Column(name = "STICKER_X")
	@NotNull
	private double x;
	@Column(name = "STICKER_Y")
	@NotNull
	private double y;
	@Column(name = "CREATED_AT")
	@NotNull
	@CreatedDate
	private LocalDateTime createdAt;
	@Column(name = "MODIFIED_AT")
	@NotNull
	@LastModifiedDate
	private LocalDateTime modifiedAt;
	@Column(name = "STICKER_TYPE")
	@Enumerated(EnumType.STRING)
	@NotNull
	private StickerType stickerType;

	@ManyToOne
	@JoinColumn(name = "timeLineSeq")
	@JsonIgnore
	private TimeLine timeLine;

}
