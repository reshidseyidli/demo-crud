package com.example.demo.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.time.LocalDate;
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
            entity.setValyuta(requestEntity.getValyuta());
            entity.setMezenne(requestEntity.getMezenne());
            entity.setTarix(requestEntity.getTarix());
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
    public MyEntity getValyuta(MyEntity myEntity) {
        MyEntity entity = repository.findByTarix(myEntity.getTarix());

        if (entity == null) {
            try {
                entity = getMezenneFromCbarApi();
            } catch (JAXBException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return entity;
    }

    private MyEntity getMezenneFromCbarApi() throws JAXBException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        LocalDate currDate = LocalDate.now();
        int year = currDate.getYear();
        int month = currDate.getMonthValue();

        String strMonth = String.valueOf(month);
        if (strMonth.length() == 1) {
            strMonth = "0" + strMonth;
        }

        int day = currDate.getDayOfMonth();
        String strDay = String.valueOf(day);
        if (strDay.length() == 1) {
            strDay = "0" + strDay;
        }

        String url = "https://www.cbar.az/currencies/" + strDay + "." + strMonth + "." + year + ".xml";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        HttpStatus status = response.getStatusCode();
        if (status != HttpStatus.OK) {
            System.out.println("err" + "Received an invalid response: " + status);
        } else {
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ValCurs valCurs = (ValCurs) jaxbUnmarshaller.unmarshal(new StringReader(response.getBody()));

            List<ValType> listValType = valCurs.getListValType();
            ValType valType = listValType.get(1);
            List<Valute> listValute = valType.getListValute();

            for (Valute valute : listValute) {
                System.out.println("valute : " + valute.getName());
            }
        }

        return null;
    }
}
