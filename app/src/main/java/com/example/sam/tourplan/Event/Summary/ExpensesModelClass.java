package com.example.sam.tourplan.Event.Summary;

import com.example.sam.tourplan.Event.EventModelClass;

/**
 * Created by ASUS on 4/30/2017.
 */

public class ExpensesModelClass{



    private int expensesId;
    private int eventId;
    private String expensesFor;
    private int expensesAmount;

    public ExpensesModelClass(int eventId, String expensesFor, int expensesAmount) {

        this.eventId = eventId;
        this.expensesFor = expensesFor;
        this.expensesAmount = expensesAmount;
    }

    public ExpensesModelClass(int expensesId, int eventId, String expensesFor, int expensesAmount) {
        this.expensesId = expensesId;
        this.eventId = eventId;
        this.expensesFor = expensesFor;
        this.expensesAmount = expensesAmount;
    }

    public ExpensesModelClass() {

    }



    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(int expensesId) {
        this.expensesId = expensesId;
    }

    public String getExpensesFor() {
        return expensesFor;
    }

    public void setExpensesFor(String expensesFor) {
        this.expensesFor = expensesFor;
    }

    public int getExpensesAmount() {
        return expensesAmount;
    }

    public void setExpensesAmount(int expensesAmount) {
        this.expensesAmount = expensesAmount;
    }
}
