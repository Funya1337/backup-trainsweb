package com.train.train.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TrainModel {
    private String uuid;
    private String serialNumber;
    private String status;
    private Integer distance;
    private Integer daysInWork;
    private String cycle;
    private Integer seat;
    private Integer interval;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer fromMoscowToPiter;
    private Integer fromPiterToMoscow;

    public String getUuid() {
        return uuid;
    }

    public TrainModel setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public TrainModel setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TrainModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getDistance() {
        return distance;
    }

    public TrainModel setDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public Integer getDaysInWork() {
        return daysInWork;
    }

    public TrainModel setDaysInWork(Integer daysInWork) {
        this.daysInWork = daysInWork;
        return this;
    }

    public String getCycle() {
        return cycle;
    }

    public TrainModel setCycle(String cycle) {
        this.cycle = cycle;
        return this;
    }

    public Integer getSeat() {
        return seat;
    }

    public TrainModel setSeat(Integer seat) {
        this.seat = seat;
        return this;
    }

    public Integer getInterval() {
        return interval;
    }

    public TrainModel setInterval(Integer interval) {
        this.interval = interval;
        return this;
    }

    public Integer getFromMoscowToPiter() {
        return fromMoscowToPiter;
    }

    public TrainModel setFromMoscowToPiter(Integer fromMoscowToPiter) {
        this.fromMoscowToPiter = fromMoscowToPiter;
        return this;
    }

    public Integer getFromPiterToMoscow() {
        return fromPiterToMoscow;
    }

    // public TrainModel setEndDate(LocalDate endDate) {
    //     this.endDate = endDate;
    //     return this;
    // }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public TrainModel setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public TrainModel setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public TrainModel setFromPiterToMoscow(Integer fromPiterToMoscow) {
        this.fromPiterToMoscow = fromPiterToMoscow;
        return this;
    }
}