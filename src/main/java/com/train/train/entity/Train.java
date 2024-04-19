package com.train.train.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class  Train {
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

    public Train() {
    }

    public String getUuid() {
        return uuid;
    }

    public Train setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Train setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Train setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getDistance() {
        return distance;
    }

    public Train setDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public Integer getSeat() {
        return seat;
    }

    public Train setSeat(Integer seat) {
        this.seat = seat;
        return this;
    }

    public Integer getDaysInWork() {
        return daysInWork;
    }

    public Train setDaysInWork(Integer daysInWork) {
        this.daysInWork = daysInWork;
        return this;
    }

    public Integer getInterval() {
        return interval;
    }

    public Train setInterval(Integer interval) {
        this.interval = interval;
        return this;
    }

    public Integer getFromMoscowToPiter() {
        return fromMoscowToPiter;
    }

    public Train setFromMoscowToPiter(Integer fromMoscowToPiter) {
        this.fromMoscowToPiter = fromMoscowToPiter;
        return this;
    }

    public Integer getFromPiterToMoscow() {
        return fromPiterToMoscow;
    }

    public Train setFromPiterToMoscow(Integer fromPiterToMoscow) {
        this.fromPiterToMoscow = fromPiterToMoscow;
        return this;
    }

    // public LocalDate getStartDate() {
    //     return startDate;
    // }

    // public Train setStartDate(LocalDate startDate) {
    //     this.startDate = startDate;
    //     return this;
    // }

    // public LocalDate getEndDate() {
    //     return this.endDate;
    // }

    // public Train setEndDate(LocalDate endDate) {
    //     this.endDate = endDate;
    //     return this;
    // }


    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public Train setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public Train setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getCycle() {
        return cycle;
    }

    public Train setCycle(String cycle) {
        this.cycle = cycle;
        return this;
    }
}
