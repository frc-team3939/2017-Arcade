package org.usfirst.frc.team3939.robot;

//test 

import edu.wpi.first.wpilibj.RobotDrive; 
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	
	CANJaguar LeftBackMotor, LeftFrontMotor, RightBackMotor, RightFrontMotor;
	CANJaguar.ControlMode currControlMode;
	RobotDrive myRobot;
	Joystick stick = new Joystick(0);
	int maxOutputSpeed;
	
	
	
	
	
	
	
	public Robot() {
		LeftBackMotor = new CANJaguar(1); //Left Back
        LeftFrontMotor = new CANJaguar(3); //Left Front
        RightBackMotor = new CANJaguar(5); //Right Back
        RightFrontMotor = new CANJaguar(4); //Right Front 
        myRobot = new RobotDrive(LeftFrontMotor, LeftBackMotor, RightFrontMotor, RightBackMotor);
    	
	}

	
	@Override
	public void robotInit() {
	}

	public void initaimdrive() {
    	LeftFrontMotor.disableControl();
        LeftFrontMotor.configMaxOutputVoltage(12.0);
        LeftFrontMotor.configNeutralMode(CANJaguar.NeutralMode.Brake);
        LeftFrontMotor.setPositionMode(CANJaguar.kQuadEncoder, 4320, 75, .0, 0);
        LeftFrontMotor.enableControl();
        
        RightFrontMotor.disableControl();
        RightFrontMotor.configMaxOutputVoltage(12.0);
        RightFrontMotor.configNeutralMode(CANJaguar.NeutralMode.Brake);
        RightFrontMotor.setPositionMode(CANJaguar.kQuadEncoder, 4320, 75, .0, 0);
        RightFrontMotor.enableControl();
        
        LeftBackMotor.disableControl();
        LeftBackMotor.configMaxOutputVoltage(12.0);
        LeftBackMotor.configNeutralMode(CANJaguar.NeutralMode.Coast);
        LeftBackMotor.setPositionMode(CANJaguar.kQuadEncoder, 4320, 75, .0, 0);
        LeftBackMotor.enableControl();
        
        RightBackMotor.disableControl();
        RightBackMotor.configMaxOutputVoltage(12.0);
        RightBackMotor.configNeutralMode(CANJaguar.NeutralMode.Coast);
        RightBackMotor.setPositionMode(CANJaguar.kQuadEncoder, 4320, 75, .0, 0);
        RightBackMotor.enableControl();
        
    }
 
    void initMotor( CANJaguar motor ) {
        try {
            if ( currControlMode == CANJaguar.JaguarControlMode.Position )
            {
                motor.configMaxOutputVoltage(12.0);
                motor.configNeutralMode(CANJaguar.NeutralMode.Brake);
                motor.setPositionMode(CANJaguar.kQuadEncoder, 4320, 0, .0, 0);
            }
            else
            {
            	motor.configNeutralMode(CANJaguar.NeutralMode.Brake);
                motor.setPercentMode();
            }
            motor.enableControl();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
    
    void setMode( CANJaguar.ControlMode controlMode ) {
        
        currControlMode = controlMode;

        if ( currControlMode == CANJaguar.JaguarControlMode.Position )
        {
        
        }
        else // kPercentVbus
        {
                maxOutputSpeed = 1;
        }
        
        initMotor(LeftBackMotor);
        initMotor(LeftFrontMotor);
        initMotor(RightBackMotor);
        initMotor(RightFrontMotor);    
        //initMotor(Jag_08);
    }    
    
    void checkForRestartedMotor( CANJaguar motor, String strDescription )
    {
        if ( currControlMode != CANJaguar.JaguarControlMode.Speed )   // kSpeed is the default
        {
            try {
            	if ( !motor.isAlive() )
                {
                    Timer.delay(0.10); // Wait 100 ms
                    initMotor( motor );
                    String error = "\n\n>>>>" + strDescription + "Jaguar Power Cycled - re-initializing";
                    System.out.println(error);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }    

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * if-else structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomous() {
			}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		myRobot.setSafetyEnabled(true);  
		while (isOperatorControl() && isEnabled()) {
			myRobot.arcadeDrive(-stick.getY(), (-stick.getZ()));	
			
			Timer.delay(0.005); // wait for a motor update time
		}
	}

	/**
	 * Runs during test mode 
	 */ 
	@Override
	public void test() {
	}


}
