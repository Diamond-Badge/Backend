package org.diamond_badge.footprint.jpa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DIARY")
public class Diary {

	@Id
	@Column(name = "DIARY_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long diarySeq;
	@Column(name = "DIARY_LOCATION")
	@NotNull
	@Size(max = 512)
	private String location;
	@Column(name = "DIARY_PLACE")
	@NotNull
	@Size(max = 100)
	private String place;
	@Column(name = "DIARY_CONTENT", length = 512)
	@Size(max = 512)
	private String content;
	@Column(name = "USER_NAME")
	@NotNull
	@Size(max = 100)
	private String userName;
	@Column(name = "USER_EMAIL")
	@NotNull
	@Size(max = 512)
	private String userEmail;
	@Column(name = "DIARY_LATITUDE")
	@NotNull
	private double latitude;
	@Column(name = "DIARY_LONGTITUDE")
	@NotNull
	private double longtitude;
	@Column(name = "IS_WRITTEN")
	@NotNull
	private boolean isWritten;
	@Column(name = "DIARY_LIKE")
	@NotNull
	private int likeCount;
	@Column(name = "CREATED_AT")
	@NotNull
	@CreatedDate
	private LocalDateTime createdAt;
	@Column(name = "MODIFIED_AT")
	@NotNull
	@LastModifiedDate
	private LocalDateTime modifiedAt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIMELINE_SEQ")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private TimeLine timeLine;

	@OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();

	@OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
	private List<DiaryImages> diaryImages = new ArrayList<>();

	public Diary(String location, double latitude, double longtitude,
		String userName, String userEamil) {
		this.location = this.place = location;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.isWritten = false;
		this.userName = userName;
		this.userEmail = userEamil;
		this.likeCount = 0;
	}

	public void setPalce(String place) {
		this.place = place;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTimeLine(TimeLine timeLine) {
		this.timeLine = timeLine;
	}

	public void setIsWritten(boolean isWritten) {
		this.isWritten = isWritten;
	}

	public void updateLikeCount() {
		this.likeCount = this.likes.size();
	}

	public void cancleLike(Like like) {
		this.likes.remove(like);
	}

}
