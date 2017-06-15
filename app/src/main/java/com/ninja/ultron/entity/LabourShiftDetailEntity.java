package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourShiftDetailEntity {

        private int id;
        private int Hours;
        private String ShiftName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHours() {
            return Hours;
        }

        public void setHours(int hours) {
            this.Hours = hours;
        }

        public String getShiftName() {
            return ShiftName;
        }

        public void setShiftName(String shiftName) {
            this.ShiftName = shiftName;
        }

}
