package org.diamond_badge.footprint.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.DiaryImages;
import org.diamond_badge.footprint.jpa.repo.DiaryImagesRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final DiaryImagesRepository diaryImagesRepository;

	public File save(MultipartFile file, String name) {
		StringBuilder sb = new StringBuilder();
		File dest = null;
		// file image 가 없을 경우
		if (file.isEmpty()) {
			sb.append("none");
		}
		if (!file.isEmpty()) {
			// jpeg, png, gif 파일들만 받아서 처리할 예정
			String contentType = file.getContentType();
			String originalFileExtension = null;
			// 확장자 명이 없으면 이 파일은 잘 못 된 것이다
			if (ObjectUtils.isEmpty(contentType)) {
				sb.append("none");
			}
			if (!ObjectUtils.isEmpty(contentType)) {
				if (contentType.contains("image/jpeg")) {
					originalFileExtension = ".jpg";
				} else if (contentType.contains("image/png")) {
					originalFileExtension = ".png";
				} else if (contentType.contains("image/gif")) {
					originalFileExtension = ".gif";
				}
				sb.append(name + originalFileExtension);
			}

			dest = new File(
				"/Users/jifrozen/project/Diamond_badge/Backend/upload/" + "/" + sb.toString());
			try {
				file.transferTo(dest);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// db에 파일 위치랑 번호 등록
		}
		return dest;

	}

	public List<DiaryImages> savefiles(List<MultipartFile> files, String name, Diary diary) throws
		IOException {
		// 반환을 할 파일 리스트
		List<DiaryImages> fileList = new ArrayList<>();
		// 파일이 빈 것이 들어오면 빈 것을 반환
		if (files.isEmpty()) {
			return fileList;
		}
		int count = 0;
		for (MultipartFile multipartFile : files) {
			if (!multipartFile.isEmpty()) {
				// jpeg, png, gif 파일들만 받아서 처리할 예정
				String contentType = multipartFile.getContentType();
				String originalFileExtension;
				// 확장자 명이 없으면 이 파일은 잘 못 된 것이다
				if (ObjectUtils.isEmpty(contentType)) {
					break;
				} else {
					if (contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					} else if (contentType.contains("image/png")) {
						originalFileExtension = ".png";
					} else if (contentType.contains("image/gif")) {
						originalFileExtension = ".gif";
					}
					// 다른 파일 명이면 아무 일 하지 않는다
					else {
						break;
					}
				}
				count += 1;
				String new_file_name = name + count + originalFileExtension;
				String path = "/Users/jifrozen/project/Diamond_badge/Backend/upload/";

				// 저장된 파일로 변경하여 이를 보여주기 위함
				File file = new File(path + new_file_name);
				multipartFile.transferTo(file);
				// 생성 후 리스트에 추가
				DiaryImages diaryPicture = new DiaryImages(file.getName(), file.getPath(), diary);

				diaryImagesRepository.save(diaryPicture);
				fileList.add(diaryPicture);
			}
		}
		return fileList;
	}

}
