// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants;

public class Elevator extends SubsystemBase {

  private CANSparkMax elevator;
  public SparkPIDController elevatorPID;
  private RelativeEncoder elevatorEncoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, allowedErr;
  public double height;

  /** Creates a new Elevator. */
  public Elevator() {
    elevator = new CANSparkMax(Constants.ElevatorConstants.kElevator, MotorType.kBrushless);
    elevator.restoreFactoryDefaults();

    elevator.setOpenLoopRampRate(2);
    elevator.setClosedLoopRampRate(2);

    elevator.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
    elevator.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);

    elevatorPID = elevator.getPIDController();
    elevatorEncoder = elevator.getEncoder();

    elevatorPID.setFeedbackDevice(elevatorEncoder);

    elevator.setIdleMode(IdleMode.kBrake);

    kP = 0.1;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0;

    kMinOutput = -1;
    kMaxOutput = 1;

    allowedErr = 0.1;

    elevatorPID.setP(kP);
    elevatorPID.setI(kI);
    elevatorPID.setD(kD);
    elevatorPID.setIZone(kIz);
    elevatorPID.setFF(kFF);
    elevatorPID.setOutputRange(kMinOutput, kMaxOutput);

    elevatorPID.setSmartMotionMaxVelocity(ElevatorConstants.kElevatorVelocity * 10, 0);
    elevatorPID.setSmartMotionMaxAccel(ElevatorConstants.kElevatorAccel * 10, 0);
    elevatorPID.setSmartMotionAllowedClosedLoopError(allowedErr, 0);

    elevator.setSmartCurrentLimit(35);
    elevator.burnFlash();
  }

  public void setPower(double power){
    elevator.set(power);
  }

  public void stop(){
    elevator.set(0);
  }

  public double getElevatorPos(){
    return elevatorEncoder.getPosition();
  }

    public double getElevatorTicks(){
    return getElevatorPos() * ElevatorConstants.kElevatorCountsPerRev * ElevatorConstants.kElevatorGearRatio;
  }

  public double getElevatorInches(){
    return getElevatorPos() * (ElevatorConstants.kElevatorCountsPerRev * ElevatorConstants.kElevatorGearRatio) / ElevatorConstants.kTicksPerInch;
  }

  public void elevatorRev(double rev){
    elevatorPID.setReference(rev, CANSparkMax.ControlType.kSmartMotion, 0, 0);
  }

  public boolean isElevatorWithinError(double target, double error){
    return(Math.abs(target - getElevatorPos()) <= error);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("EncPos", -getElevatorPos());
    SmartDashboard.putNumber("Inches", -getElevatorPos() * 0.666 * ElevatorConstants.kElevatorGearRatio);
  }
}
