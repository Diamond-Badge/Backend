package org.diamond_badge.footprint.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "DIARY_IMAGES")
public class DiaryImages {

	@Id
	@Column(name = "DIARY_IMAGES_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long diaryImagesSeq;

	@Column(name = "path")
	@ApiModelProperty(notes = "경로")
	private String path;

	@Column(name = "file_name")
	@ApiModelProperty(notes = "파일명")
	private String fileName;

	@ManyToOne
	@JoinColumn(name = "diarySeq")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Diary diary;

	public DiaryImages(String fileName, String path, Diary diary) {
		this.fileName = fileName;
		this.path = path;
		this.diary = diary;
	}
}
