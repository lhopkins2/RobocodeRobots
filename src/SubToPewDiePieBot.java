package LH;
import robocode.*;
import robocode.util.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * SubToPewDiePie - a robot by (your name here)
 */
public class SubToPewDiePie extends Robot
{
    /**
     * global Variables
     */
    boolean botScanned = false;
    boolean inCorner = false;

    /**
     * run: SubToPewDiePie's default behavior
     */
    public void run() {
        // Initialization of the robot should be put here

        // After trying out your robot, try uncommenting the import at the top,
        // and the next line:

        // setColors(Color.red,Color.blue,Color.green); // body,gun,radar

        // Pre-game Settings
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);
        corner();
        // Robot main loop
        int gunIncrement = 3;
        int timer = 0;
        while(true) {
            // Replace the next 4 lines with any behavior you would like
            for (int i = 0; i < 30; i += 1) {
                turnGunRight(gunIncrement);
                turnRadarRight(gunIncrement);
                scan();
                //dodgeing();
            }
            gunIncrement *= -1;
            if (timer == 100) {
                timer = 0;
                turnRadarRight(360);
            }
            timer++;
        }
    }

    /**
     * corner: Find out which corner to go to and then goes to it
     */
    public void corner() {
        double robotX = getX();
        double robotY = getY();

        if (inCorner == false) {
            if (robotX > 400 && robotY > 400 && robotX != 782 && robotY != 782) {
                String corner = "TR"; //identifyer for later querys
                inCorner = false;
                if (getHeading() < 180) {
                    turnLeft(getHeading());
                } else {
                    turnRight(360 - getHeading());
                }
                ahead(800);
                turnRight(90);
                ahead(600);
                if (getGunHeading() < 180) {
                    turnGunLeft(getGunHeading());
                } else {
                    turnGunRight(360 - getGunHeading());
                }
                turnGunRight(180);
                if (getRadarHeading() < 180) {
                    turnRadarLeft(getRadarHeading());
                } else {
                    turnRadarRight(360 - getRadarHeading());
                }
                turnRadarRight(180);
            }
            //-----------------------------------
            else if (robotX <= 400 && robotY > 400 && robotX != 18 && robotY != 782) {
                String corner = "TL"; //identifyer for later querys
                inCorner = false;
                if (getHeading() < 180) {
                    turnLeft(getHeading());
                } else {
                    turnRight(360 - getHeading());
                }
                ahead(800);
                turnLeft(90);
                ahead(600);
                if (getGunHeading() < 180) {
                    turnGunLeft(getGunHeading());
                } else {
                    turnGunRight(360 - getGunHeading());
                }
                turnGunRight(90);
                if (getRadarHeading() < 180) {
                    turnRadarLeft(getRadarHeading());
                } else {
                    turnRadarRight(360 - getRadarHeading());
                }
                turnRadarRight(90);
            }
            //-----------------------------------
            else if (robotX > 400 && robotY <= 400 && robotX != 782 && robotY != 18) {
                String corner = "BR"; //identifyer for later querys
                inCorner = false;
                if (getHeading() < 180) {
                    turnLeft(getHeading());
                } else {
                    turnRight(360 - getHeading());
                }
                back(800);
                turnRight(90);
                ahead(600);
                if (getGunHeading() < 180) {
                    turnGunLeft(getGunHeading());
                } else {
                    turnGunRight(360 - getGunHeading());
                }
                turnGunLeft(90);
                if (getRadarHeading() < 180) {
                    turnRadarLeft(getRadarHeading());
                } else {
                    turnRadarRight(360 - getRadarHeading());
                }
                turnRadarLeft(90);
            }
            //-----------------------------------
            else if (robotX <= 400 && robotY <= 400 && robotX != 18 && robotX != 18) {
                String corner = "BL"; //identifyer for later querys
                inCorner = false;
                if (getHeading() < 180) {
                    turnLeft(getHeading());
                } else {
                    turnRight(360 - getHeading());
                }
                back(800);
                turnLeft(90);
                ahead(600);
                if (getGunHeading() < 180) {
                    turnGunLeft(getGunHeading());
                } else {
                    turnGunRight(360 - getGunHeading());
                }
                if (getRadarHeading() < 180) {
                    turnRadarLeft(getRadarHeading());
                } else {
                    turnRadarRight(360 - getRadarHeading());
                }
            }
            else {
                inCorner = true;
            }
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        // Replace the next line with any behavior you would like
        //turnGunRight(2.0 * Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getRadarHeading()));
        //turnRadarRight(Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getRadarHeading()));
        targeting(e.getDistance());
        if(botScanned){
            stop();
            turnRadarRight(Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getRadarHeading()));
            turnGunRight(2.0 * Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getRadarHeading()));
            targeting(e.getDistance());
            scan();
            resume();
        }
        scan();
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitRobot(HitRobotEvent e){
        corner();
    }

    public void onHitByBullet(HitByBulletEvent e){
        dodgeing();
    }

    public void dodgeing() {
        turnRight(90);
        ahead(200);
    }

    public void targeting(double distance){
        if(distance > 250 || getEnergy() < 25){
            fire(1);
        }
        else if(distance <250 && distance >50){
            fire(2);
        }
        else{
            fire(3);
        }
    }
}