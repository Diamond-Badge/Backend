package org.diamond_badge.footprint.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "STATISTICS")
public class Statistics {

	@Id
	@Column(name = "STATISTICS_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long statisticsSeq;

	//감정
	private int EXCITED;
	private int HAPPY;
	private int SAD;
	private int ANGRY;
	private int DEPRESSE;

	//위치
	private String first;
	private String second;
	private String third;

	//활발
	private int diaryCnt;
	private int locationCnt;

	@OneToOne(mappedBy = "statistics")
	private User user;

	public Statistics(User user) {
		this.EXCITED = this.ANGRY = this.DEPRESSE = this.HAPPY = this.SAD = this.diaryCnt = this.locationCnt = 0;
		this.first = this.second = this.third = "측정중입니다.";
		this.user = user;
	}

	public void setActive(int diaryCnt, int locationCnt) {
		this.diaryCnt = diaryCnt;
		this.locationCnt = locationCnt;
	}

}
