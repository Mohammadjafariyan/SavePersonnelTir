package service;

import main.java.models.TirEntity;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class SaveTirService {

    public Object[][] GetAllDataTableWithCheckbox(int month, int year, long code) throws MyServiceException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


        Date from = MyUtility.GetMonthBeginInGaregorian(year, month);
        Date to = MyUtility.GetMonthEndInGaregorian(year, month);


        long count = (long) session.createQuery("select count(*) from TirEntity as tir where tir.code=:code").setParameter("code", code).uniqueResult();
        if (count == 0) {
            throw new MyServiceException("کد پرسنل وارد شده اشتباه است و هیچ دیتایی پیدا نشد");
        }


        List<TirEntity> all = session.createQuery
                ("from TirEntity as tir where tir.code=:code and tir.date between :from and :to order by tir.date desc ")
                .setParameter("from", from).setParameter("to", to).setParameter("code", code).list();


        int size = all.size();

        Object[][] dataTable = new Object[size + 1][3];

        //dataTable[0] = new String[]{"کد", "تیر", "ملاحضات", "تاریخ"};

        for (int i = 0; i < size; i++) {
            TirEntity item = all.get(i);
            dataTable[i] = new Object[]{
                    item.getDescription(), item.getCount() + ""
                    , MyUtility.ConvertToShamsi(item.getDate())};
        }

        int total = calculateTotal(all);
        dataTable[size] = new Object[]{
                "تیر", total + ""
                , "جمع"};


        return dataTable;
    }

    public int calculateTotal(List<TirEntity> entities) throws MyServiceException {
        int totoal = 0;
        long code = 0;
        for (TirEntity tir : entities) {
            totoal += tir.getCount();
            if (code != 0) {
                if (code != tir.getCode())
                    throw new MyServiceException("کدها برای جمع درست نیستند");
            }
            code = tir.getCode();
        }
        return totoal;
    }


    public String[][] GetAllDataTable(int rows, boolean isForPrint) throws MyServiceException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        rows = rows < 1 ? 1 : rows;

        List<TirEntity> all = session.createQuery("from TirEntity as tir order by tir.date desc ")
                .setMaxResults(rows).list();

        String[][] dataTable = null;
        if (isForPrint) {
            dataTable = toTwoDimentionalArrayForPrint(all);
        } else {
            dataTable = toTwoDimentionalArray(all);
        }

        return dataTable;

    }

    private String[][] toTwoDimentionalArrayForPrint(List<TirEntity> all) throws MyServiceException {
        int size = all.size();

        String[][] dataTable = new String[size + 1][3];

        //dataTable[0] = new String[]{"کد", "تیر", "ملاحضات", "تاریخ"};


        int i1 = calculateTotal(all);

        for (int i = 0; i < size; i++) {
            TirEntity item = all.get(i);

            String desc = item.getDescription();
            if (desc == null)
                desc = "";

            desc = MyUtility.convertToEnglishDigits(desc);
            String count = item.getCount() + "";
            count = MyUtility.convertToEnglishDigits(count);

            String date = printDate(item.getDate());

            dataTable[i] = new String[]{desc, count
                    , date};
        }

        String total = MyUtility.convertToEnglishDigits(i1 + "");

        dataTable[size] = new String[]{"تیر", total
                , "جمع"};
        return dataTable;
    }

    private String printDate(Date date) {
        String[] split = MyUtility.ConvertToShamsi(date).split("-");
        ArrayUtils.reverse(split);
        String s = String.join("-", split);
        String _date = MyUtility.convertToEnglishDigits(s);
        return _date;

    }

    private String[][] toTwoDimentionalArray(List<TirEntity> all) {
        int size = all.size();

        String[][] dataTable = new String[size][5];

        //dataTable[0] = new String[]{"کد", "تیر", "ملاحضات", "تاریخ"};

        for (int i = 0; i < size; i++) {
            TirEntity item = all.get(i);

            String code = MyUtility.convertToEnglishDigits(item.getCode() + "");
            String count = MyUtility.convertToEnglishDigits(item.getCount() + "");
            String description = MyUtility.convertToEnglishDigits(item.getDescription());
            String id = MyUtility.convertToEnglishDigits(item.getId() + "");

            String date = printDate(item.getDate());


            dataTable[i] = new String[]{code,
                    count, description
                    , date, id};
        }
        return dataTable;
    }


    public List<TirEntity> GetAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<TirEntity> all = session.createQuery("from TirEntity").list();
        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        return all;
    }

    public void Save(TirEntity tir) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(tir);
        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void DeleteById(String selectRowId) throws Exception {

        long id;
        try {
            id = Long.parseLong(selectRowId);
        } catch (Exception e) {
            throw new Exception("ورودی کد برای حذف تیر عدد نیست");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Object o = session.get(TirEntity.class, id);
        if (o == null) {
            session.getTransaction().commit();
            session.close();
            throw new Exception("یافت نشد");
        }
        TirEntity finded;
        try {
            finded = (TirEntity) o;

        } catch (Exception e) {
            session.getTransaction().commit();
            session.close();
            throw new Exception("خطا در تبدیل مقدار");
        }

        session.delete(finded);
        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public List<Long> GetAllPersonnelCodes() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<Long> all = session.createQuery("select distinct tir.code from TirEntity as tir ").list();
        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        return all;
    }

    public int FindPersonnelByCode(long code) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<Object> single = session.createQuery("select distinct tir.code from TirEntity as tir where tir.code=:code ")
                .setParameter("code", code).list();
        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        return single.size();
    }

    public String[][] GetPerMonthByPersonnel(long code, int year, int monthNumber, boolean isForPrint) throws Exception {

        long count = FindPersonnelByCode(code);
        if (count == 0)
            throw new Exception("پرسنل پیدا نشد" + " " + code);

        Date from = MyUtility.ConvertToGaregoiran(year, monthNumber, 1);
        Date to = DateUtils.addMonths(from, 1);
        to = DateUtils.addDays(to, -1);

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<TirEntity> all = (List<TirEntity>) session.createQuery("select tir from TirEntity as tir" +
                " where tir.code=:code and tir.date between :from and :to order by tir.date desc ")
                .setParameter("from", from).setParameter("to", to).setParameter("code", code).list();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        fillEmptyDays(code, all, from, to);

        all.sort(Comparator.comparing(o -> o.getDate()));

        String[][] dataTable = null;
        if (isForPrint) {
            dataTable = toTwoDimentionalArrayForPrint(all);
        } else {
            dataTable = toTwoDimentionalArray(all);
        }


        return dataTable;
    }

    private void fillEmptyDays(long code, List<TirEntity> all, Date from, Date to) {

        int c = 1;
        Date firstDay = from;

        long days = MyUtility.getDifferenceDays(from, to);
        for (int i = 0; i < days; i++) {
            TirEntity tir = null;

            if (all.size() > 0) {
                tir = findByDateInList(all, firstDay);
            }

            if (tir == null) {
                TirEntity fill = new TirEntity();
                fill.setDate((Date) firstDay.clone());
                fill.setCode(code);

                all.add(i, fill);
            }

            firstDay = DateUtils.addDays(firstDay, 1);

        }

    }

    private TirEntity findByDateInList(List<TirEntity> all, Date firstDay) {
        for (TirEntity tir : all) {
            if (DateUtils.isSameDay(tir.getDate(), firstDay))
                return tir;
        }
        return null;
    }

    public long GetLastInsertedCode() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<Object> single = session.createQuery("select  tir.code from TirEntity as tir order by tir.id desc ").list();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        if (single.size() > 0)
            return (long) single.get(0);

        return -1;
    }

    public TirEntity FindByTir(TirEntity tir) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        /*Calendar fromtime = Calendar.getInstance();
        fromtime.set(Calendar.HOUR, 0);
        fromtime.set(Calendar.MINUTE, 0);
        fromtime.set(Calendar.SECOND, 0);

        Calendar totime = Calendar.getInstance();
        totime.set(Calendar.HOUR, 23);
        totime.set(Calendar.MINUTE, 59);
        totime.set(Calendar.SECOND, 59);

        Date from=tir.getDate();
        Date to=tir.getDate();

        from.setTime(fromtime.getTimeInMillis());
        to.setTime(totime.getTimeInMillis());*/


        session.beginTransaction();
        List<Object> single = session.createQuery("select distinct tir from TirEntity as tir" +
                " where tir.code=:code and tir.date=:date")
                .setParameter("code", tir.getCode())
                .setTimestamp("date", tir.getDate()).list();
        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        if (single.size() > 0)
            return (TirEntity) single.get(0);

        return null;
    }
}

