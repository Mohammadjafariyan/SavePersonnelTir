package test.java;

import org.junit.Test;
import service.MyUtility;

import java.util.Calendar;
import java.util.Date;

public class MyUtilityTests {


    @Test
    public void GetMonthLenghtTest(){

        assert MyUtility.GetMonthDays(1397,6).length==31;
        assert (int)MyUtility.GetMonthDays(1397,6)[30]==31;
        assert MyUtility.GetMonthDays(1397,7).length==30;
        assert (int)MyUtility.GetMonthDays(1397,7)[29]==30;

    }

    @Test
    public void CovertTest(){


        Date date=MyUtility.ConvertToGaregoiran(1373,07,07);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);


        int d=gc.get(Calendar.MONTH);
        assert gc.get(Calendar.YEAR)==1994;
        assert gc.get(Calendar.MONTH)+1==9;
        assert gc.get(Calendar.DAY_OF_MONTH)==29;

    }

    @Test
    public void GetMonthBeginInGaregorianTest(){

        Date date=MyUtility.GetMonthBeginInGaregorian(1373,07);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);

        assert gc.get(Calendar.YEAR)==1994;
        assert gc.get(Calendar.MONTH)+1==9;
        assert gc.get(Calendar.DAY_OF_MONTH)==23;

    }

    @Test
    public void GetMonthEndInGaregorianTest(){

        Date date=MyUtility.GetMonthEndInGaregorian(1373,07);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);

        assert gc.get(Calendar.YEAR)==1994;
        assert gc.get(Calendar.MONTH)+1==10;
        assert gc.get(Calendar.DAY_OF_MONTH)==22;

    }
}
