package service;

import main.java.models.SettingEntity;
import main.java.models.TirEntity;
import org.hibernate.Session;

import java.util.List;

public class SettingService {

    public SettingEntity initIfNotExists() throws Exception {

        SettingEntity settingEntity = isInited();

        if (settingEntity != null) {
            return settingEntity;
        } else {

            SettingEntity entity = new SettingEntity();
            entity.setSmonth( MyUtility.GetCurrentMonthNumber());
            entity.setSyear(MyUtility.GetCurrentYear());
            entity.setSday(MyUtility.GetCurrentDay());

            entity.setExcelOutputPath(System.getProperty("user.dir"));
            insert(entity);
            return entity;

        }


    }

    public void insert(SettingEntity entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.save(entity);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public SettingEntity isInited() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<SettingEntity> all = session.createQuery("from SettingEntity").list();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        if (all.size() == 0) {
            return null;
        } else if (all.size() == 1) {
            return all.get(0);
        } else {

            clearAll();
            isInited();
            throw new Exception("بیش از یک ابجکت برای تنظیمات یافت   شد");
        }
    }

    public void clearAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<SettingEntity> all = session.createQuery("from SettingEntity").list();

        for (SettingEntity ent : all) {
            session.delete(ent);
        }

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void save(SettingEntity settingEntity) {
        if (settingEntity.getId() == 0)
            insert(settingEntity);
        else {
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            session.update(settingEntity);
            //Commit the transaction
            session.getTransaction().commit();
            session.close();
        }
    }
}
