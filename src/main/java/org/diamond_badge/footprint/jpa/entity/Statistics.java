package org.diamond_badge.footprint.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private int DEPRESSED;

	//위치
	private String first;
	private String second;
	private String third;

	//활발
	private int diaryCnt;
	private int locationCnt;

	private String userEmail;

	public Statistics(String userEmail) {
		this.EXCITED = this.ANGRY = this.DEPRESSED = this.HAPPY = this.SAD = this.diaryCnt = this.locationCnt = 0;
		this.first = this.second = this.third = "측정중입니다.";
		this.userEmail = userEmail;
	}

	public void setActive(int diaryCnt, int locationCnt) {
		this.diaryCnt = diaryCnt;
		this.locationCnt = locationCnt;
	}

	public void setEmotion(int EXCITED, int HAPPY, int SAD, int ANGRY, int DEPRESSED) {
		this.EXCITED = EXCITED;
		this.HAPPY = HAPPY;
		this.ANGRY = ANGRY;
		this.SAD = SAD;
		this.DEPRESSED = DEPRESSED;
	}

	public void setLocation(String first, String second, String third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

}
