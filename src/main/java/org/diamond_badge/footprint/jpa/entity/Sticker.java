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
@Table(name = "STICKER")
@Entity
public class Sticker {
	@JsonIgnore
	@Id
	@Column(name = "STICKER_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stickerSeq;

}
