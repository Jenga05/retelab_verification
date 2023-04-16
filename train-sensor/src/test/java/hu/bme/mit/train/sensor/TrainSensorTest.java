package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainSensorImpl sensor;
    TrainController controller;
    TrainUser user;

    /**
     * Initialize the component with mocks
     */
    @Before
    public void before() {
        controller = mock(TrainController.class);
        user = mock(TrainUser.class);
        sensor = new TrainSensorImpl(controller,user);
    }

    /**
     * Setting the speed above the absolute limit
     * Expecting the user to be in alert state after that
     */
    @Test
    public void SetSpeedAboveAbsoluteLimit() {
        sensor.overrideSpeedLimit(501);
        verify(user,times(1)).setInWarningState(true);
    }

    /**
     * Setting the speed under the absolute limit
     * Expecting the user to be in alert state after that
     */
    @Test
    public void SetSpeedUnderAbsoluteLimit() {
        sensor.overrideSpeedLimit(-1);
        verify(user,times(1)).setInWarningState(true);
    }

    /**
     * Setting the speed under the logical limit
     * Expecting the user to be in alert state after that
     */
    @Test
    public void SetSpeedSlowerThatLimit() {
        when(controller.getReferenceSpeed()).thenReturn(20);
        sensor.overrideSpeedLimit(5);
        verify(user,times(1)).setInWarningState(true);
    }

    /**
     * Setting the speed within all the limits
     * Expecting the user not to be in alert state after that
     */
    @Test
    public void SetSpeedInLimit() {
        when(controller.getReferenceSpeed()).thenReturn(20);
        sensor.overrideSpeedLimit(15);
        Assert.assertFalse(user.isInWarningState());
    }
}
