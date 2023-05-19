package com.CredeDataServices.Crede.Data.Services.Task.service.craw;

import com.CredeDataServices.Crede.Data.Services.Task.dto.DataDto;
import com.CredeDataServices.Crede.Data.Services.Task.service.connection.ConnectionService;
import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CrawService {

    private final ConnectionService connectionService;

    @Value("${page.url}")
    private String urlPage;

    @Value("${page.url.root}")
    private String urlRoot;

    @Value("${page.total.number}")
    private String elementPage;

    public Integer getTotalElementPage() throws IOException {
        Elements element = connectionService.getConnection(urlRoot).select(elementPage);

        return element
                .stream()
                .map(CrawService::convertToNumber)
                .max(Comparator.comparing(Integer::valueOf)).get();
    }

    private static Integer convertToNumber(Element element){
        try {
            return Integer.parseInt(Jsoup.parse(String.valueOf(element)).body().text());
        }
        catch (Exception e){
            return 0;
        }
    }

    public void crawData() throws IOException, InterruptedException{

        Integer totalPages = getTotalElementPage();
        List<DataDto> dataDtoList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int index = 1; index < totalPages; index ++){
            executorService.execute(
                    ThreadCraw.builder().dataDtoList(dataDtoList)
                            .connection(connectionService)
                            .urlPage(urlPage).index(index).build());
        }

        executorService.shutdown();
        executorService.awaitTermination(200, TimeUnit.SECONDS);
    }
}
