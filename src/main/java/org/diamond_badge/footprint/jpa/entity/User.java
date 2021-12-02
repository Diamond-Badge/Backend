package org.diamond_badge.footprint.jpa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {
	@JsonIgnore
	@Id
	@Column(name = "USER_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userSeq;

	@Column(name = "USERNAME", length = 100)
	@NotNull
	@Size(max = 100)
	private String username;

	@Column(name = "EMAIL", length = 512, unique = true)
	@NotNull
	@Size(max = 512)
	private String email;

	@Column(name = "PROFILE_IMAGE_URL", length = 512)
	@NotNull
	@Size(max = 512)
	private String profileImageUrl;

	@Column(name = "PROVIDER_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	@NotNull
	private ProviderType providerType;

	@Column(name = "ROLE_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	@NotNull
	private RoleType roleType;

	@Column(name = "IS_PRIVATE")
	@NotNull
	private boolean isPrivate;

	@Column(name = "CREATED_AT")
	@NotNull
	private LocalDateTime createdAt;

	@Column(name = "MODIFIED_AT")
	@NotNull
	private LocalDateTime modifiedAt;

	@OneToMany(mappedBy = "user")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<TimeLine> timeLines = new ArrayList<>();

	public User(
		@NotNull @Size(max = 100) String username,
		@NotNull @Size(max = 512) String email,
		@NotNull @Size(max = 512) String profileImageUrl,
		@NotNull ProviderType providerType,
		@NotNull RoleType roleType,
		@NotNull LocalDateTime createdAt,
		@NotNull LocalDateTime modifiedAt
	) {
		this.username = username;
		this.email = email != null ? email : "NO_EMAIL";
		this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
		this.providerType = providerType;
		this.roleType = roleType;
		this.isPrivate = false;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void setPrivate() {
		if (this.isPrivate == false)
			this.isPrivate = true;
		else
			this.isPrivate = false;
	}

	public void addTimeLine(TimeLine timeLine) {
		this.timeLines.add(timeLine);
	}
}

