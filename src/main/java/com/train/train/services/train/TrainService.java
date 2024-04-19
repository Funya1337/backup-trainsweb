package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.models.TrainModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TrainService {
    List<Train> createNewTrains(Integer maxTrains, LocalDateTime startDate, LocalDateTime endDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow);
    List<TrainModel> convertToTrainModel(List<Train> trainsList);
    List<Integer> distributePeopleRandomly(Integer peopleCount, int interval);
    List<Integer> findTrainNumbers(List<Integer> fromMoscowToPiter, List<Integer> fromPiterToMoscow);
    List<Double> countIntervals(List<Integer> trainNum);
    List<Train> resetStatus(List<Integer> trainNums);

    void execLogic();
    
    String generateUuid();
    String generateSerialNumber();

    // DEBUG SIGNATURES
    void printTrains();
}
