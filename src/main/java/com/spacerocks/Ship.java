package com.spacerocks;
import javafx.scene.shape.Polygon;

public class Ship extends GameObject {
        private boolean thrusting;

    public Ship( double xposition, double yposition) {
            super(new Polygon(-10, -10, 20, 0, -10, 10), 0, xposition, yposition);

            this.thrusting = false;

            // change initial angle
            this.turn(270);
        }

        public boolean isThrusting() {
            return thrusting;
        }

        public void setThrusting(boolean thrusting) {
            this.thrusting = thrusting;
        }

        public void accelerate(){
            if (this.getSpeed() < 6){
                this.setSpeed(this.getSpeed() + 1);
            }
        }

        public void slowDown(){
            if (this.getSpeed() > 0){
                this.setSpeed(this.getSpeed() - 0.1);
            } else if (this.getSpeed() < 0) {
                this.setSpeed(0);
            }
        }
    }
