package com.example.eadmobileapp.models;

import java.util.List;

public class Queue {

    private String name;
    private Integer octane92Queue;
    private Integer octane95Queue;
    private Integer dieselQueue;
    private Integer superDieselQueue;

    public Queue(String name, Integer octane92Queue, Integer octane95Queue, Integer dieselQueue, Integer superDieselQueue) {
        this.name = name;
        this.octane92Queue = octane92Queue;
        this.octane95Queue = octane95Queue;
        this.dieselQueue = dieselQueue;
        this.superDieselQueue = superDieselQueue;
    }
    //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOctane92Queue() {
        return octane92Queue;
    }

    public void setOctane92Queue(Integer octane92Queue) {
        this.octane92Queue = octane92Queue;
    }

    public Integer getOctane95Queue() {
        return octane95Queue;
    }

    public void setOctane95Queue(Integer octane95Queue) {
        this.octane95Queue = octane95Queue;
    }

    public Integer getDieselQueue() {
        return dieselQueue;
    }

    public void setDieselQueue(Integer dieselQueue) {
        this.dieselQueue = dieselQueue;
    }

    public Integer getSuperDieselQueue() {
        return superDieselQueue;
    }

    public void setSuperDieselQueue(Integer superDieselQueue) {
        this.superDieselQueue = superDieselQueue;
    }


}
