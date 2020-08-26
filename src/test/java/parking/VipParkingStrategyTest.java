package parking;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VipParkingStrategyTest {

    @Mock
    CarDao carDao;


    @InjectMocks
    VipParkingStrategy vipParkingStrategy;

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
        // given
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());

        Car car = this.createMockCar("A");
        doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);

        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot = mock(ParkingLot.class);

        when(parkingLot.isFull()).thenReturn(true);
        parkingLots.add(parkingLot);
        // when
        vipParkingStrategy.park(parkingLots, car);

        // then
        verify(vipParkingStrategy).createReceipt(parkingLot ,car);

    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        // given
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());

        Car car = this.createMockCar("A");
        doReturn(false).when(vipParkingStrategy).isAllowOverPark(car);

        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot = mock(ParkingLot.class);

        when(parkingLot.isFull()).thenReturn(true);
        parkingLots.add(parkingLot);
        // when
        vipParkingStrategy.park(parkingLots, car);

        // then
        verify(vipParkingStrategy).createNoSpaceReceipt(car);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */

        // given
        Car car = this.createMockCar("A");
        when(carDao.isVip(car.getName())).thenReturn(true);
        // then
        assertTrue(vipParkingStrategy.isAllowOverPark(car));
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        // given
        Car car = this.createMockCar("B");
        when(carDao.isVip(car.getName())).thenReturn(true);
        // then
        assertFalse(vipParkingStrategy.isAllowOverPark(car));
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        // given
        Car car = this.createMockCar("A");
        when(carDao.isVip(car.getName())).thenReturn(false);
        // then
        assertFalse(vipParkingStrategy.isAllowOverPark(car));
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        // given
        Car car = this.createMockCar("B");
        when(carDao.isVip(car.getName())).thenReturn(false);
        // then
        assertFalse(vipParkingStrategy.isAllowOverPark(car));
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
