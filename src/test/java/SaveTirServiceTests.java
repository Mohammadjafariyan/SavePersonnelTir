package test.java;

import main.java.models.ExportTirTableModel;
import main.java.models.TirEntity;
import org.junit.Test;
import service.MyServiceException;
import service.SaveTirService;

import java.util.LinkedList;
import java.util.List;

public class SaveTirServiceTests {



    @Test
    public void GetPerMonthByPersonnel() throws Exception {
        SaveTirService saveTirService = new SaveTirService();

        String[][]  strings = saveTirService.GetPerMonthByPersonnel(1,1397,5, false);


        assert strings.length==31;

        String msg=strings[0][3];
        String msg2=strings[0][2];

        assert strings[0][3].equals("1397-05-01");

        for (int i = 0; i < strings.length; i++) {
            String m=null;
            int j=i+1;
            if(j<10)
                m="1397-05-0"+j;
            else
                m="1397-05-"+j;

            assert m!=null;
            String m2=strings[i][3];

            assert  m2.equals(m);
        }

        try{
             strings = saveTirService.GetPerMonthByPersonnel(9999,1397,5, false);

            assert true==false;

        }catch (Exception e){
            assert true;
        }
    }
    @Test
    public void calculateTotal() throws MyServiceException {
        SaveTirService saveTirService = new SaveTirService();

        List<TirEntity> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            TirEntity entity = new TirEntity();
            entity.setCount(5);
            list.add(entity);
        }

        int total = saveTirService.calculateTotal(list);
        assert total == 5 * 100;
        try {

            list = new LinkedList<>();
            for (int i = 0; i < 100; i++) {
                TirEntity entity = new TirEntity();
                entity.setCount(5);
                entity.setCode(i);
                list.add(entity);
            }

            int total2 = saveTirService.calculateTotal(list);
            assert true == false;

        } catch (MyServiceException e) {
            assert true;

        }

    }

    @Test
    public void DatatableWithCheckboxTest() throws MyServiceException {
        SaveTirService saveTirService = new SaveTirService();
        ExportTirTableModel model = new ExportTirTableModel();

        model.rowData = saveTirService.GetAllDataTableWithCheckbox
                (10, 1397, 5);

        assert model.rowData[0].length == 6;
    }

    @Test
    public void PersonnelList() {
        SaveTirService saveTirService = new SaveTirService();

        List<Long> list = saveTirService.GetAllPersonnelCodes();

        assert list.size() >= 0;

    }

    @Test
    public void DatatableTest() throws MyServiceException {
        SaveTirService saveTirService = new SaveTirService();

        String[][] table = saveTirService.GetAllDataTable(5, false);

        assert table.length > 1;
        assert table[0][0] == "کد";
    }

    @Test
    public void SaveTest() {

        SaveTirService saveTirService = new SaveTirService();

        int length = saveTirService.GetAll().size();
        TirEntity t = new TirEntity();
        t.setCode(12);
        t.setCount(12);
        saveTirService.Save(t);


        int lengthafter = saveTirService.GetAll().size();
        assert length < lengthafter + 1;

    }
}
