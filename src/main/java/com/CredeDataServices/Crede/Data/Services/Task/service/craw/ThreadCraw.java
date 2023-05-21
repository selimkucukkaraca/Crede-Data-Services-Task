package com.CredeDataServices.Crede.Data.Services.Task.service.craw;

import com.CredeDataServices.Crede.Data.Services.Task.dto.DataDto;
import com.CredeDataServices.Crede.Data.Services.Task.exception.FileException;
import com.CredeDataServices.Crede.Data.Services.Task.service.connection.ConnectionService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class ThreadCraw implements Runnable{
    private List<DataDto> dataDtoList;
    private ConnectionService connection;
    private String urlPage;
    private int index;

    @Override
    public void run() {
        Document connectionPage = null;
        try {
            connectionPage = connection.getConnection(this.urlPage, this.index);
            Elements datas = connectionPage.select("div._ngcontent-ikv-c130");

            datas.forEach(data ->{DataDto dataDto = DataDto.builder()
                            .tenderRegistrationNumber(Objects.requireNonNull(data.selectFirst("span")).text())
                            .qualityTypeAndQuantity(Objects.requireNonNull(data.selectFirst("span")).text())
                            .placeOfWork(Objects.requireNonNull(data.selectFirst("span")).text())
                            .tenderType(Objects.requireNonNull(data.selectFirst("span")).text())
                            .url(Objects.requireNonNull(data.selectFirst("a")).attr("herf"))
                            .build();
                        this.dataDtoList.add(dataDto);
                    });

            writeFile(dataDtoList);

        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    void writeFile(List<DataDto> dataDtoList){
        File path = new File("data.txt");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8))) {
            bufferedWriter.write("");

            for (DataDto data : dataDtoList) {
                bufferedWriter.write(data.tenderRegistrationNumber() + "," + data.qualityTypeAndQuantity() + "," + data.placeOfWork() + "," + data.tenderType());
                bufferedWriter.newLine();
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}