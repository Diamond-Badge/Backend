package org.diamond_badge.footprint.jpa.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "DIARY")
public class Diary {
	@OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
	Set<Like> likes = new HashSet<>();
	@JsonIgnore
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
	private int like;
	@Column(name = "CREATED_AT")
	@NotNull
	private LocalDateTime createdAt;
	@Column(name = "MODIFIED_AT")
	@NotNull
	private LocalDateTime modifiedAt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIMELINE_SEQ")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private TimeLine timeLine;

	public Diary(String location, double latitude, double longtitude, LocalDateTime createdAt, LocalDateTime modifiedAt,
		String userName, String userEamil) {
		this.location = this.place = location;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.isWritten = false;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.userName = userName;
		this.userEmail = userEamil;
		this.like = 0;
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

}
