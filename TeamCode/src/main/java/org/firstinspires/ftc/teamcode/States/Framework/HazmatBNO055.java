package org.firstinspires.ftc.teamcode.States.Framework;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

/**
 * Created by robotics9277 on 12/15/2017.
 */

public class HazmatBNO055 {
    private static HazmatBNO055 instance = null;
    private BNO055IMU imu = null;



    private HazmatBNO055(OpMode opMode, String name){
        imu = opMode.hardwareMap.get(BNO055IMU.class, name);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu.initialize(parameters);
    }

    public static HazmatBNO055 getInstance(OpMode opmode, String name){
        if(instance == null){
            instance = new HazmatBNO055(opmode, name);
        }
        return instance;
    }

    public double getYaw(){
        try{
            return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        } catch(Exception e){
            android.util.Log.d("Robot", "GYRO ERROR: " + e.getMessage());
        }
        return 0;
    }

    public void close(){
        imu = null;
        instance = null;
    }

    public double getLatency(){
        return (System.nanoTime() - imu.getAngularOrientation().acquisitionTime)/1000000;
    }

    public double getUpdatedYaw(){
        while((System.nanoTime() - imu.getAngularOrientation().acquisitionTime)/1000000 > 5){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }
}
