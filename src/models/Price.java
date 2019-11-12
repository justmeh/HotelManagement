package models;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Price implements IPrice, Serializable {

    private List<IInterval> discountIntervalList;
    private IInterval summer,spring,fall,winter;

    public Price() {
        this.discountIntervalList = new ArrayList<>();
        this.summer = new Season(LocalDate.of(0,6,1),LocalDate.of(0,8,31));
        this.spring = new Season(LocalDate.of(0,3,1),LocalDate.of(0,5,31));
        this.fall = new Season(LocalDate.of(0,9,1),LocalDate.of(0,11,30));
        this.winter = new Season(LocalDate.of(0,12,1),LocalDate.of(0,2,29));
    }

    @Override
    public ICost generateCost(LocalDate startingDate, LocalDate endingDate) {
        for(IInterval interval : discountIntervalList){
            if(interval.contains(startingDate,endingDate))
                return interval.createCost();
        }

        if(summer.contains(startingDate,endingDate)){
            return summer.createCost();
        }

        if(spring.contains(startingDate,endingDate)){
            return spring.createCost();
        }

        if(fall.contains(startingDate,endingDate)){
            return fall.createCost();
        }

        if(winter.contains(startingDate,endingDate)){
            return winter.createCost();
        }

        return null;
    }

    @Override
    public void createDiscountInterval(LocalDate startingDate, LocalDate endingDate, int pricePerNight) {
        IInterval newDiscountInterval = new Interval(startingDate,endingDate,pricePerNight);
        discountIntervalList.add(newDiscountInterval);
    }

    @Override
    public void setSummerPrice(int pricePerNight) {
        summer.setPrice(pricePerNight);
    }

    @Override
    public void setSpringPrice(int pricePerNight) {
        spring.setPrice(pricePerNight);
    }

    @Override
    public void setFallPrice(int pricePerNight) {
        fall.setPrice(pricePerNight);
    }

    @Override
    public void setWinterPrice(int pricePerNight) {
        winter.setPrice(pricePerNight);
    }

    @Override
    public int getSummerPrice() {
        return summer.getPrice();
    }

    @Override
    public int getSpringPrice() {
        return spring.getPrice();
    }

    @Override
    public int getFallPrice() {
        return fall.getPrice();
    }

    @Override
    public int getWinterPrice() {
        return winter.getPrice();
    }
}
