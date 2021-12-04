package org.diamond_badge.footprint.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	@Column(name = "STICKER_NAME")
	@NotNull
	@Size(max = 100)
	private String name;

	@OneToMany(mappedBy = "sticker", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<TimeLineSticker> stickers = new ArrayList<>();

}
