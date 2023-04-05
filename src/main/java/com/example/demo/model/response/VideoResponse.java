package com.example.demo.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VideoResponse extends FileResponse {

	// độ dài đơn vị byte
	private Long length;

	private Long lengthSecond;

	private String type;

	private String pathPreviewRelative;

	private String pathImageRelative;
}
