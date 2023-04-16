package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		checkSpeed(speedLimit);
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}

	private void checkSpeed(int speedLimit){
		int referenceSpeed = this.controller.getReferenceSpeed();
		if (speedLimit < 0 || speedLimit >= 500 || referenceSpeed-speedLimit > referenceSpeed/2){
			this.user.setInWarningState(true);
		}
	}

}
