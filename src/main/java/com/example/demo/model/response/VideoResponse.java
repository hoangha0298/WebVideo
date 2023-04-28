package com.example.demo.model.response;

import com.example.demo.model.DTO.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VideoResponse extends FileResponse {

	// độ dài đơn vị byte
	private Long length;

	private Integer lengthSecond;

	private String type;

	private Video.Attributes attributes;

	private String pathImageRelative;

}
