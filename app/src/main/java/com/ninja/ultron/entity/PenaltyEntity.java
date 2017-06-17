package com.ninja.ultron.entity;

/**
 * Created by Sangameswaran on 17-06-2017.
 */

public class PenaltyEntity {
    int id;
    String name;
    int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
