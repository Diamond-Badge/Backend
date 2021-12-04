package org.diamond_badge.footprint.jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@Table(name = "TIMELINE_STICKER")
@Entity
public class TimeLineSticker {
	@JsonIgnore
	@Id
	@Column(name = "TIMELINE_STICKER_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long timeLineStickerSeq;

	@Column(name = "STICKER_X")
	@NotNull
	private double x;
	@Column(name = "STICKER_Y")
	@NotNull
	private double y;
	@Column(name = "STICKER_Z")
	@NotNull
	private double z;
	@Column(name = "CREATED_AT")
	@NotNull
	private LocalDateTime createdAt;
	@Column(name = "MODIFIED_AT")
	@NotNull
	private LocalDateTime modifiedAt;

	@ManyToOne
	@JoinColumn(name="timeLineSeq")
	@JsonIgnore
	private TimeLine timeLine;

	@ManyToOne
	@JoinColumn(name="stickerSeq")
	@JsonIgnore
	private Sticker sticker;
}
