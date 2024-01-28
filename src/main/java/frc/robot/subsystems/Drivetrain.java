/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.RobotMap;


public class Drivetrain extends SubsystemBase {

  private Talon leftMaster;
  private Talon rightMaster;
  private Talon leftSlave;
  private Talon rightSlave;
  private DifferentialDrive drive;

  public Drivetrain() {
    //initalize Talon SRX motors
    leftMaster = new Talon(Constants.RobotMap.kLeftMaster);
    rightMaster = new Talon(RobotMap.kRightMaster);
    leftSlave = new Talon(RobotMap.kLeftSlave);
    rightSlave = new Talon(RobotMap.kRightSlave);
    MotorControllerGroup left = new MotorControllerGroup(leftMaster, leftSlave);
    MotorControllerGroup right = new MotorControllerGroup(rightMaster, rightSlave);
    drive =  new DifferentialDrive(left, right);
    drive.setSafetyEnabled(false);
    
    //Set Motor Polarities
    leftMaster.setInverted(false);
    leftSlave.setInverted(false);
    rightMaster.setInverted(true);
    rightSlave.setInverted(true);
    
  }

  /**
   * Function that will Drive the motors in tank mode
   * @param leftPower Power output to left wheels
   * @param rightPower Power output to right wheels
   */

  public void setArcade(double speed, double rotation){
    drive.arcadeDrive(speed, rotation);
  }


  

  /**
   * Moves to the given amount of inches using motion magic
   * @param distance distance to move (inches)
   */
 

  /**
   * Check if Drivetrain is has gotten to a target distance within a particular error
   * @param distance Target distance to move (inches)
   * @param error Allowable error between current position and target distance
   * @return true if current position is within error of distance
   */


  /**
   * Stops motion of Drivetrain
   */
  public void stop() {
    leftMaster.set(0);
    rightMaster.set(0);
  }
}