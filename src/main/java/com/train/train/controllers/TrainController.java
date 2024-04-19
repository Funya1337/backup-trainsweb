package com.train.train.controllers;

import com.train.train.entity.Train;
import com.train.train.forms.TrainCreateForm;
import com.train.train.services.train.TrainService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrainController {
    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/simulation")
    public ModelAndView form() {
        return new ModelAndView("simulation-form")
                .addObject("trainCreateForm", new TrainCreateForm());
    }

    @PostMapping("/simulation")
    public ModelAndView createTrains(@ModelAttribute TrainCreateForm createForm) {
        List<Train> trains = trainService.createNewTrains(
                                createForm.getMaxTrains(),
                                // createForm.getStartDate(),
                                LocalDateTime.parse("2016-10-31 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                LocalDateTime.parse("2016-10-31 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                createForm.getFromMoscowToPiter(),
                                createForm.getFromPiterToMoscow());
        trainService.printTrains();
        trainService.execLogic();
        return new ModelAndView("simulation-form")
                .addObject(
                        "allTrains", trainService.convertToTrainModel(trains));
    }
}