package com.epam.rd.java.final_project.car_rental.controller;

import com.epam.rd.java.final_project.car_rental.entity.Car;
import com.epam.rd.java.final_project.car_rental.repository.CarRepository;
import com.epam.rd.java.final_project.car_rental.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarService carService;

    @GetMapping
    private String carList(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortingField,
                           @RequestParam("sortDir") Optional<String> sortingDir
    ){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String sortField = sortingField.orElse("carBrand");
        String sortDir = sortingDir.orElse("asc");

        Page<Car> carPage = carService.findPaginated(
                currentPage,
                pageSize,
                sortField,
                sortDir);

        model.addAttribute("carPage", carPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        int totalPages = carPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

//        model.addAttribute("cars", carRepository.findAll());
        return "car-list";
    }

    @GetMapping("/create")
    private String carCreate(Model model){
        model.addAttribute("car", new Car());
        return "car-create";
    }

//    @ModelAttribute(name = "car")
//    public Car car() {
//        return new Car();
//    }

    @PostMapping("/create")
    public String userSave(@Valid @ModelAttribute("car") Car car,
                           Model model) {
        carRepository.save(car);
        return "redirect:/car";
    }
}
