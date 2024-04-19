package com.train.train.forms;

import java.time.LocalDate;

public class TrainCreateForm {
    private Integer maxTrains;
    private Integer fromMoscowToPiter;
    private Integer fromPiterToMoscow;
    private LocalDate startDate;

    public Integer getMaxTrains() {
        return maxTrains;
    }

    public TrainCreateForm setMaxTrains(Integer maxTrains) {
        this.maxTrains = maxTrains;
        return this;
    }

    public Integer getFromMoscowToPiter() {
        return fromMoscowToPiter;
    }

    public TrainCreateForm setFromMoscowToPiter(Integer fromMoscowToPiter) {
        this.fromMoscowToPiter = fromMoscowToPiter;
        return this;
    }

    public Integer getFromPiterToMoscow() {
        return fromPiterToMoscow;
    }

    public TrainCreateForm setFromPiterToMoscow(Integer fromPiterToMoscow) {
        this.fromPiterToMoscow = fromPiterToMoscow;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public TrainCreateForm setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }
}
