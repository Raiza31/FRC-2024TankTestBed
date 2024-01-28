// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class ElevatorHeight extends Command {

  Elevator elevator;
  double elevatorTargetHeight;

  /** Creates a new ElevatorHeight. */
  public ElevatorHeight(Elevator elevator, double elevatorTargetHeight) {

    // Use addRequirements() here to declare subsystem dependencies.
    this.elevator = elevator;
    this.elevatorTargetHeight = elevatorTargetHeight;
    addRequirements(elevator);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putBoolean("Target Reached", false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elevator.elevatorRev(-elevatorTargetHeight);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Target Reached", true);
    elevator.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return elevator.isElevatorWithinError(elevatorTargetHeight, 0.1);
  }
}
