package com.example.demo.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FolderResponse {

	protected URI pathAbsolute;

	private List<FileResponse> files;

	private List<FolderResponse> subFolders;

}
