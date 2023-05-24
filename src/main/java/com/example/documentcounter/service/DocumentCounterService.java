package com.example.documentcounter.service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DocumentCounterService {
    private int totalDocuments;
    private int totalPages;

    public int countDocuments(String rootFolderPath) {
        totalDocuments = 0;
        totalPages = 0;
        File rootFolder = new File(rootFolderPath);
        if (!rootFolder.exists() || !rootFolder.isDirectory()) {
            throw new IllegalArgumentException("Invalid root folder path.");
        }

        processFolder(rootFolder);
        return totalDocuments;
    }

    public int countPages(String rootFolderPath) {
        totalDocuments = 0;
        totalPages = 0;
        File rootFolder = new File(rootFolderPath);
        if (!rootFolder.exists() || !rootFolder.isDirectory()) {
            throw new IllegalArgumentException("Invalid root folder path.");
        }

        processFolder(rootFolder);
        return totalPages;
    }

    private void processFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processFolder(file);
                } else {
                    processFile(file);
                }
            }
        }
    }

    private void processFile(File file) {
        String fileName = file.getName();
        if (fileName.toLowerCase().endsWith(".docx")) {
            countPagesInWordDocument(file);
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            countPagesInPdfDocument(file);
        }
    }

    private void countPagesInWordDocument(File file) {
        try (XWPFDocument document = new XWPFDocument(OPCPackage.open(file.getAbsolutePath()))) {
            int pageCount = document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
            totalDocuments++;
            totalPages += pageCount;
        } catch (IOException e) {
            throw new RuntimeException("Error reading Word document: " + file.getAbsolutePath(), e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void countPagesInPdfDocument(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            int pageCount = document.getNumberOfPages();
            totalDocuments++;
            totalPages += pageCount;
        } catch (IOException e) {
            throw new RuntimeException("Error reading PDF document: " + file.getAbsolutePath(), e);
        }
    }
}

