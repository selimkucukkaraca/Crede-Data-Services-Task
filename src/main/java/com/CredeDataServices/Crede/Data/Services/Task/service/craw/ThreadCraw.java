package com.CredeDataServices.Crede.Data.Services.Task.service.craw;

import com.CredeDataServices.Crede.Data.Services.Task.dto.DataDto;
import com.CredeDataServices.Crede.Data.Services.Task.service.connection.ConnectionService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Iterator;
import java.util.List;

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

            datas.stream()
                    .forEach(data ->{DataDto dataDto = DataDto.builder()
                            .tenderRegistrationNumber(data.selectFirst("span").text())
                            .qualityTypeAndQuantity(data.selectFirst("span").text())
                            .placeOfWork(data.selectFirst("span").text())
                            .tenderType(data.selectFirst("span").text())
                            .url(data.selectFirst("a").attr("herf"))
                            .build();
                        this.dataDtoList.add(dataDto);
                    });

            writeFile(dataDtoList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void writeFile(List<DataDto> dataDtoList){
        File path = new File("data.txt");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write("");

            Iterator<DataDto> iterator = dataDtoList.iterator();

            while (iterator.hasNext()){
                try {
                    DataDto data = iterator.next();
                    bufferedWriter.write(data.tenderRegistrationNumber() + "," + data.qualityTypeAndQuantity() + "," + data.placeOfWork() + "," + data.tenderType());
                    bufferedWriter.newLine();
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}