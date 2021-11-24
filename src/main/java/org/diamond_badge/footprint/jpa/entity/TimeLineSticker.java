package org.diamond_badge.footprint.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

}
