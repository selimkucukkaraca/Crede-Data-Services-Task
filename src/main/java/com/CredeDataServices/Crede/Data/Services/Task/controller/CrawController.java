package com.CredeDataServices.Crede.Data.Services.Task.controller;

import com.CredeDataServices.Crede.Data.Services.Task.dto.DataDto;
import com.CredeDataServices.Crede.Data.Services.Task.service.craw.CrawService;
import com.CredeDataServices.Crede.Data.Services.Task.service.read.ReadDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class CrawController {

    private final ReadDataService readDataService;
    private final CrawService crawService;

    @PostMapping("/crawl")
    public ResponseEntity<Void> crawlingData()  throws IOException, InterruptedException{
        crawService.crawData();
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/crawls")
    public ResponseEntity<List<DataDto>> readData() throws IOException{
        return ResponseEntity.ok(readDataService.getData());
    }
}
