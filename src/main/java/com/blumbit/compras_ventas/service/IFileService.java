package com.blumbit.compras_ventas.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.blumbit.compras_ventas.dto.FileDownloadResponse;

public interface IFileService {

    File retrieveFile(String filePath);

    String createFile(MultipartFile file);

    FileDownloadResponse fileDownload(String filePath);
}
