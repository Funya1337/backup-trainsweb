package com.train.train.services.model;

import com.train.train.entity.Train;
import com.train.train.models.TrainModel;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {

    @Override
    public TrainModel getTrainModel(Train train) {
        return new TrainModel()
                .setUuid(train.getUuid())
                .setDistance(train.getDistance())
                .setSeat(train.getSeat())
                .setStatus(train.getStatus())
                .setInterval(train.getInterval())
                .setSerialNumber(train.getSerialNumber())
                .setDaysInWork(train.getDaysInWork())
                .setCycle(train.getCycle())
                .setFromPiterToMoscow(train.getFromPiterToMoscow())
                .setFromMoscowToPiter(train.getFromMoscowToPiter())
                .setStartDate(train.getStartDate());
    }
}
