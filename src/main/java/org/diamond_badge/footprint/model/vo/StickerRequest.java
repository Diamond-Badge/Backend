package org.diamond_badge.footprint.model.vo;

import org.diamond_badge.footprint.jpa.entity.StickerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StickerRequest {

	private double x;
	private double y;
	private StickerType stickerType;

}
