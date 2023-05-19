package com.CredeDataServices.Crede.Data.Services.Task.service.read;

import com.CredeDataServices.Crede.Data.Services.Task.dto.DataDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadDataService {
    public List<DataDto> getData() throws IOException{
        List<DataDto> dataDtoList = new ArrayList<>();
        File file = new File("data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;

        while ((s = br.readLine()) != null){
            String[] data = s.split(",");
            DataDto datas = DataDto.builder()
                    .tenderRegistrationNumber(data[0])
                    .qualityTypeAndQuantity(data[1])
                    .placeOfWork(data[2])
                    .tenderType(data[3])
                    .url(data[4])
                    .build();
            dataDtoList.add(datas);
        }
        return dataDtoList;
    }
}
