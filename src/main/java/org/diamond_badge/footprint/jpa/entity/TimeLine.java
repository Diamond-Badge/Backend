package org.diamond_badge.footprint.jpa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TIMELINE")
public class TimeLine {

	@Id
	@Column(name = "TIMELINE_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long timeLineSeq;

	@Column(name = "EMOTION")
	@Enumerated(EnumType.STRING)
	@NotNull
	private EmotionType emotionType;

	@Column(name = "CREATED_AT")
	@NotNull
	private LocalDateTime createdAt;

	@Column(name = "MODIFIED_AT")
	@NotNull
	private LocalDateTime modifiedAt;

	@OneToMany(mappedBy = "timeLine", cascade = CascadeType.ALL)
	private List<Diary> diarys = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SEQ")
	@JsonIgnore
	private User user;

	public TimeLine(EmotionType emotionType, LocalDateTime createdAt, User user) {
		this.emotionType = emotionType;
		this.createdAt = this.modifiedAt = createdAt;
		this.user = user;

	}

	public void newDiary(Diary diary) {
		this.diarys.add(diary);
	}

	public void setEmotionType(EmotionType emotionType) {
		this.emotionType = emotionType;
	}

}
