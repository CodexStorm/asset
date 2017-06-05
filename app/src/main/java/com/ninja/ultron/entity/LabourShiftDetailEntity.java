package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourShiftDetailEntity {

        private int id;
        private int hours;
        private String shiftName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public String getShiftName() {
            return shiftName;
        }

        public void setShiftName(String shiftName) {
            this.shiftName = shiftName;
        }

}
