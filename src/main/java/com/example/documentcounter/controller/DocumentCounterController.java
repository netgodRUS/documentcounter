package com.example.documentcounter.controller;
import com.example.documentcounter.service.DocumentCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentCounterController {
    private final DocumentCounterService documentCounterService;

    @Autowired
    public DocumentCounterController(DocumentCounterService documentCounterService) {
        this.documentCounterService = documentCounterService;
    }

    @GetMapping("/count/{rootFolderPath}")
    public String countDocumentsAndPages(@PathVariable String rootFolderPath) {
        int totalDocuments = documentCounterService.countDocuments(rootFolderPath);
        int totalPages = documentCounterService.countPages(rootFolderPath);
        return "Total Documents: " + totalDocuments + "\nTotal Pages: " + totalPages;
    }
}

