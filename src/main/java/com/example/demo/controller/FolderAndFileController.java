package com.example.demo.controller;

import com.example.demo.Service.FolderService;
import com.example.demo.model.response.FolderResponse;
import com.example.demo.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folder")
public class FolderAndFileController extends BaseRestController {

	@Autowired
	private FolderService folderService;

	@GetMapping("/{pathRelative}")
	public Response<FolderResponse> getTreeFolderVideo(@PathVariable(required = false) String pathRelative) {
		return success(folderService.getTreeFolderVideo(pathRelative), "Danh sách thư mục con cháu (bao gồm file) trong thư mục gốc");
	}

}
