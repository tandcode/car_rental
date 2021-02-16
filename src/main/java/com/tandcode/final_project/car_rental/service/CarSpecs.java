package com.tandcode.final_project.car_rental.service;

import com.tandcode.final_project.car_rental.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecs {

    /** if carBrandName == null then specification is ignored */
    public static Specification<Car> carCarBrandNameEquals(String carBrandName) {
        return (root, query, builder) ->
                carBrandName.equals("") ?
//                        carBrandName == null ?
                        builder.conjunction() :
                        builder.equal(root.get("carBrand").get("name"), carBrandName);
    }

    public static Specification<Car> carQualityClassNameEquals(String qualityClassName) {
        return (root, query, builder) ->
                qualityClassName.equals("") ?
                        builder.conjunction() :
                        builder.equal(root.get("qualityClass").get("name"), qualityClassName);
    }

    public static Specification<Car> carIsInUsageEquals(Boolean isInUsage) {
        return (root, query, builder) ->
                isInUsage == null ?
                        builder.conjunction() :
                        builder.equal(root.get("isInUsage"), isInUsage);
    }


}
