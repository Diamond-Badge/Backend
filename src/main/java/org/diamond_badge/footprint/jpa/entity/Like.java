package org.diamond_badge.footprint.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "LIKES")
@Entity
public class Like {

	@JsonIgnore
	@Id
	@Column(name = "LIKE_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SEQ")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIARY_SEQ")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Diary diary;

	public Like(User user, Diary diary) {
		this.user = user;
		this.diary = diary;
	}

}
