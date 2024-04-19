package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.entity.status.Status;
import com.train.train.models.TrainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class TrainServiceImpl implements TrainService {
    private List<Train> trainsList;
    private static final String SERIAL_PREFIX = "ВП";
    private static final String DEFAULT_STATUS = String.valueOf(Status.IDLE);
    private static final Integer DEFAULT_DISTANCE = 0;
    private static final Integer DEFAULT_WORK_DAYS = 0;
    private static final String DEFAULT_CYCLE = "IS0";
    private static final Integer DEFAULT_SEAT = 450;
    private static final Integer DEFAULT_INTERVAL_MIN = 0;
    private static final Integer intervalSplitNum = 4;
    private static Integer fromMoscowToPiter;
    private static Integer fromPiterToMoscow;
    private static Integer maxTrains;
    private HashMap<Integer, ArrayList<LocalDateTime>> timeMapStart = new HashMap<Integer, ArrayList<LocalDateTime>>();
    private HashMap<Integer, ArrayList<LocalDateTime>> timeMapEnd = new HashMap<Integer, ArrayList<LocalDateTime>>();
    private Integer trainsInWorkPerDays;

    @Override
    public List<Train> createNewTrains(Integer maxxTrains, LocalDateTime startDate, LocalDateTime endDate, Integer msk_sp, Integer sp_msk) {
        trainsList = new ArrayList<>();
        fromMoscowToPiter = msk_sp;
        fromPiterToMoscow = sp_msk;
        maxTrains = maxxTrains;

        for (int i = 0; i < maxxTrains; i++) {
            Train train = new Train()
                    .setUuid(generateUuid())
                    .setSerialNumber(generateSerialNumber())
                    .setStatus(DEFAULT_STATUS)
                    .setDistance(DEFAULT_DISTANCE)
                    .setDaysInWork(DEFAULT_WORK_DAYS)
                    .setCycle(DEFAULT_CYCLE)
                    .setSeat(DEFAULT_SEAT)
                    .setInterval(DEFAULT_INTERVAL_MIN)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setFromMoscowToPiter(0)
                    .setFromPiterToMoscow(0);

            trainsList.add(train);
        }

        return trainsList;
    }

    @Override
    public List<TrainModel> convertToTrainModel(List<Train> trainsList) {
        List<TrainModel> trainModelsList = new ArrayList<>();

        for (Train train : trainsList) {
            TrainModel trainModel = new TrainModel()
                    .setUuid(train.getUuid())
                    .setSerialNumber(train.getSerialNumber())
                    .setStatus(train.getStatus())
                    .setDistance(train.getDistance())
                    .setDaysInWork(train.getDaysInWork())
                    .setCycle(train.getCycle())
                    .setSeat(train.getSeat())
                    .setInterval(train.getInterval())
                    .setStartDate(train.getStartDate())
                    .setFromMoscowToPiter(train.getFromMoscowToPiter())
                    .setFromPiterToMoscow(train.getFromPiterToMoscow());

            trainModelsList.add(trainModel);
        }

        return trainModelsList;
    }

    @Override
    public List<Integer> distributePeopleRandomly(Integer peopleCount, int interval) {
        List<Integer> distributePeople = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < interval; i++) {
            int num = i == interval - 1 ? peopleCount : random.nextInt(peopleCount - interval + i + 1);
            distributePeople.add(num);
            peopleCount -= num;
        }
        return distributePeople;
    }

    public List<Integer> findTrainNumbers(List<Integer> fromMoscowToPiter, List<Integer> fromPiterToMoscow) {
        List<Integer> trainNums = new ArrayList<>();

        for (int i = 0; i < fromMoscowToPiter.size(); i++) {
            trainNums.add((int)  Math.ceil((double) Math.max(fromMoscowToPiter.get(i), fromPiterToMoscow.get(i)) / DEFAULT_SEAT));
        }
        return trainNums;
    }

    @Override
    public List<Double> countIntervals(List<Integer> trainNum) {
        List<Double> intervals = new ArrayList<>();

        for (int amount : trainNum) {
            double num = 360.0 / amount;
            double roundNum = Math.floor(num);
            if (num - roundNum < 0.5 && num - roundNum != 0) {
                roundNum += 0.5;
            } else if (num - roundNum == 0) {
                roundNum = num;
            } else {
                roundNum += 1;
            }

            intervals.add(roundNum);
        }

        return intervals;
    }

    @Override
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateSerialNumber() {
        Random random = new Random();
        return String.join("-", SERIAL_PREFIX, String.valueOf(random.nextInt(99999) + 10000));
    }

    @Override
    public List<Train> resetStatus(List<Integer> trainNums) {
        for (int i = 0; i < trainNums.get(0); i++) {
                trainsList.get(i).setStatus(String.valueOf(Status.IN_WORK));
        }

        for (int j = trainNums.get(0); j < trainsList.size(); j++) {
            trainsList.get(j).setStatus(String.valueOf(Status.IDLE));
        }

        return trainsList;
    }

    @Override
    public void execLogic() {
        List<Integer> dist1 = distributePeopleRandomly(fromMoscowToPiter, intervalSplitNum);
        List<Integer> dist2 = distributePeopleRandomly(fromPiterToMoscow, intervalSplitNum);

        List<Integer> trainNum = findTrainNumbers(dist1, dist2);

        List<Double> intervals = countIntervals(trainNum);

        // ----------------------

        for (int i = 0; i < intervalSplitNum; ++i) {
            timeMapStart.put(i, new ArrayList<LocalDateTime>());
            timeMapEnd.put(i, new ArrayList<LocalDateTime>());
        }

        // ----------------------

        LocalDateTime time1111 = LocalDateTime.parse("2016-10-31 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        updateTime(time1111, time1111.plusHours(6), intervals.get(0).intValue(), 0);
        updateTime(time1111.plusHours(6), time1111.plusHours(12), intervals.get(1).intValue(), 1);
        updateTime(time1111.plusHours(12), time1111.plusHours(18), intervals.get(2).intValue(), 2);
        updateTime(time1111.plusHours(18), time1111.plusHours(24), intervals.get(3).intValue(), 3);


        trainsInWorkPerDays = trainNum.get(0);

        // интервалы меняются 0-6 | 6-12 (ситуация 1)

        System.out.println("SITUATION 1");

        for (LocalDateTime timeStart : timeMapStart.get(1)) {
            List<LocalDateTime> timeEndList = timeMapEnd.get(0);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(1).intValue() + 2)) <= 0;
                if (checked) {
                    timeMapStart.get(0).get(i).plusHours(7);
                    timeMapEnd.get(0).get(i).plusHours(7);
                    break;
                    // timeEndList.get(i).plusHours(7);
                } else {
                    System.out.println(timeStart + " " + timeEndList.get(i));
                    // System.out.println();
                    ++trainsInWorkPerDays;
                    break;
                    // тут нужно добавить логику нового поезда в работу
                }
            }
        }

        // интервалы меняются 12-18 | 0-6 6-12 (ситуация 2)

        for (LocalDateTime timeStart : timeMapStart.get(2)) {
            List<LocalDateTime> timeEndList = timeMapEnd.get(0);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(2).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(0).get(i).plusHours(7);
                    timeMapEnd.get(0).get(i).plusHours(7);
                    break;
                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
            timeEndList = timeMapEnd.get(1);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(2).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(1).get(i).plusHours(7);
                    timeMapEnd.get(1).get(i).plusHours(7);
                    break;
                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
        }

        // интервалы меняются 18-00 | 0-6 6-12 12-18 (ситуация 3)

        for (LocalDateTime timeStart : timeMapStart.get(3)) {
            List<LocalDateTime> timeEndList = timeMapEnd.get(0);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(3).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(0).get(i).plusHours(7);
                    timeMapEnd.get(0).get(i).plusHours(7);
                    break;
                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
            timeEndList = timeMapEnd.get(1);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(3).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(1).get(i).plusHours(7);
                    timeMapEnd.get(1).get(i).plusHours(7);
                    break;
                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
            timeEndList = timeMapEnd.get(2);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(3).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(2).get(i).plusHours(7);
                    timeMapEnd.get(2).get(i).plusHours(7);
                    break;
                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
        }


        // ----------------------------
        // LOGS
        // ----------------------------


        System.out.println("TRAINS IN WORK PER DAYS: ");
        System.out.println(trainsInWorkPerDays);

        System.out.println("START: ");

        for (Integer key : timeMapStart.keySet()) {
            ArrayList<LocalDateTime> value = timeMapStart.get(key);
            System.out.println("Key: " + key + ", Value: " + value);
        }

        System.out.println("END: ");

        for (Integer key : timeMapEnd.keySet()) {
            ArrayList<LocalDateTime> value = timeMapEnd.get(key);
            System.out.println("Key: " + key + ", Value: " + value);
        }
        

        resetStatus(trainNum);

        printList(dist1);
        printList(dist2);
        printList(trainNum);
        printList(intervals);
        //printTrains();
    }

    public void updateTime(LocalDateTime startTime, LocalDateTime endTime, int intervalMins, int key) {
        LocalDateTime current = startTime;

        int x = 360;
        int splitTime = x / intervalMins;

        for (int i = 0; i < splitTime; ++i) {
            timeMapStart.get(key).add(current);
            timeMapEnd.get(key).add(current.plusHours(7));
            current = current.plusMinutes(intervalMins);
        }
    }

    // DEBUG METHODS:

    @Override
    public void printTrains() {
        System.out.println("-----------------");
        System.out.println("UUID Serial Number Status Distance Days in Work Cycle Seat Interval Start Date From Moscow to Piter From Piter to Moscow");
        for (Train train : trainsList) {
            System.out.println(train.getUuid() + " " + train.getSerialNumber() + " " + train.getStatus() + " " + train.getDistance() + " " + train.getDaysInWork() + " " + train.getCycle() + " " + train.getSeat() + " " + train.getInterval() + " " + train.getStartDate() + " " + train.getEndDate() + " " + train.getFromMoscowToPiter() + " " + train.getFromPiterToMoscow());
        }
        System.out.println("-----------------");
    }

    public void printList(List<? extends Number> data) {
        System.out.println(Arrays.toString(data.toArray()));
    }
}

//
// [index] : [[all starts], [all ends]]
// [0] : []
// ...
//
//
//
//