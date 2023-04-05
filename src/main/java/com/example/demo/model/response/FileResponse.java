package com.example.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.net.URI;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

	protected URI pathAbsolute;

}
