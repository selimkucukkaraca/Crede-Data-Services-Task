package com.CredeDataServices.Crede.Data.Services.Task.service.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ConnectionService {

    public Document getConnection(String url) throws IOException {
      return Jsoup
              .connect(url)
              .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
              .header("Accept-Language","*")
              .get();
    }

    public Document getConnection(String url, int page) throws IOException{
        return Jsoup
                .connect(url+page)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language","*")
                .get();
    }
}
