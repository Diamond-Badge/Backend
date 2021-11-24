package org.diamond_badge.footprint.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
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
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private Diary diary;

	public Like(User user, Diary diary) {
		this.user = user;
		this.diary = diary;
	}

}
