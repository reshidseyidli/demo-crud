package com.example.demo.service.impl;

import com.example.demo.dto.request.MyEntityRequestDto;
import com.example.demo.dto.response.MyEntityResponseDto;
import com.example.demo.exception.MyEntityNotFound;
import com.example.demo.model.MyEntity;
import com.example.demo.model.pojo.ValCurs;
import com.example.demo.model.pojo.ValType;
import com.example.demo.model.pojo.Valute;
import com.example.demo.repository.MyEntityRepository;
import com.example.demo.service.MyEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyEntityServiceImpl implements MyEntityService {

    private final MyEntityRepository repository;

    @Override
    public List<MyEntity> findAll() {
        log.info("getting all...");
        return repository.findAll();
    }

    @Override
    public MyEntity findById(Long id) {
        log.info("getting by id...");
        Optional<MyEntity> entity = repository.findById(id);
        return entity.orElseThrow(MyEntityNotFound::new);
    }

    @Override
    public MyEntity save(MyEntity requestEntity) {
        log.info("saving...");
        return repository.save(requestEntity);
    }

    @Override
    public void update(Long id, MyEntity requestEntity) {
        log.info("updating...");
        Optional<MyEntity> dbEntity = repository.findById(id);
        if (dbEntity.isPresent()) {
            MyEntity entity = dbEntity.get();
            entity.setId(requestEntity.getId());
            entity.setCurrency(requestEntity.getCurrency());
            entity.setRate(requestEntity.getRate());
            entity.setDate(requestEntity.getDate());
            repository.save(requestEntity);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("deleting...");
        Optional<MyEntity> entity = repository.findById(id);
        entity.ifPresent(repository::delete);
    }

    @Override
    public MyEntityResponseDto getValyuta(MyEntityRequestDto request) throws IOException {
        MyEntityResponseDto responseDto = new MyEntityResponseDto();
        MyEntity entity = repository.findByDateAndCode(request.getDate(), request.getCode());
        if (entity == null) {
            //write to DB
            System.out.println("data not found in database");
            getMezenneFromCbarApiFillDB(request.getDate());
        }
        System.out.println("req date : " + request.getDate());
        System.out.println("req code : " + request.getCode());
        entity = repository.findByDateAndCode(request.getDate(), request.getCode());
        if (entity == null) {
            System.out.println("data not found in CBAR api");
        } else {
            responseDto = new MyEntityResponseDto();
            BigDecimal deyer = request.getConversationAmount().multiply(entity.getRate());
            responseDto.setResult(deyer);
        }
        return responseDto;
    }

    private void getMezenneFromCbarApiFillDB(String requestDate) throws IOException {
        //DATE FORMAT ==> DD.MM.YYYY
        String[] arrDate = requestDate.split("\\.");
        String day = arrDate[0];
        String month = arrDate[1];
        String year = arrDate[2];

        String urlText = "https://www.cbar.az/currencies/" + day + "." + month + "." + year + ".xml";
        URL url = new URL(urlText);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches (false);
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        // Process response - need to get XML response back.
        InputStream stream = connection.getInputStream();
        InputStreamReader isReader = new InputStreamReader(stream );

        // Put output stream into a String
        BufferedReader br = new BufferedReader(isReader);
        StringBuilder reqBody = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            reqBody.append(line);
        }
        br.close();

        connection.disconnect();

        int status = connection.getResponseCode();
        if (status != 200) {
            System.out.println("err" + "Received an invalid response: " + status);
        } else {
            JAXBContext jaxbContext;
            ValCurs valCurs;

            try {
                jaxbContext = JAXBContext.newInstance(ValCurs.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                valCurs = (ValCurs) jaxbUnmarshaller.unmarshal(new StringReader(reqBody.toString()));
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }

            List<ValType> listValType = valCurs.getListValType();
            ValType valType = listValType.get(1);
            List<Valute> listValute = valType.getListValute();

            for (Valute valute : listValute) {
                System.out.println(valute);
                MyEntity myEntity = new MyEntity();
                myEntity.setCurrency(Integer.valueOf(valute.getName()));
                myEntity.setCode(valute.getCode());
                myEntity.setRate(valute.getValue());
                myEntity.setDate(valCurs.getDate());
                repository.save(myEntity);
            }
        }
    }
}
