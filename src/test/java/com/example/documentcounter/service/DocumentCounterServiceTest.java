package com.example.documentcounter.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentCounterServiceTest {
    private DocumentCounterService documentCounterService;

    @BeforeEach
    void setUp() {
        documentCounterService = new DocumentCounterService();
    }

    @Test
    void testCountDocuments() {
        String rootFolderPath = "path/to/root/folder";
        int totalDocuments = documentCounterService.countDocuments(rootFolderPath);
        Assertions.assertEquals(10, totalDocuments);
    }

    @Test
    void testCountPages() {
        String rootFolderPath = "path/to/root/folder";
        int totalPages = documentCounterService.countPages(rootFolderPath);
        Assertions.assertEquals(50, totalPages);
    }
}

