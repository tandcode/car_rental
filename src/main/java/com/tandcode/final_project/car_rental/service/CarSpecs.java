package com.tandcode.final_project.car_rental.service;

import com.tandcode.final_project.car_rental.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecs {

    /** if carBrand_Name == null then specification is ignored */
    public static Specification<Car> carCarBrandNameEquals(String carBrand_Name) {
        return (root, query, builder) ->
                carBrand_Name.equals("") ?
//                        carBrand_Name == null ?
                        builder.conjunction() :
                        builder.equal(root.get("carBrand").get("name"), carBrand_Name);
    }

    public static Specification<Car> carQualityClass_NameEquals(String qualityClass_Name) {
        return (root, query, builder) ->
                qualityClass_Name.equals("") ?
                        builder.conjunction() :
                        builder.equal(root.get("qualityClass").get("name"), qualityClass_Name);
    }

    public static Specification<Car> carIsInUsageEquals(Boolean isInUsage) {
        return (root, query, builder) ->
                isInUsage == null ?
                        builder.conjunction() :
                        builder.equal(root.get("isInUsage"), isInUsage);
    }


}
