package luo.library.base.base;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import luo.library.base.utils.MyLog;

/**
 * 数据库操作
 */

public class BaseDb {
    public static DbManager db;

    /**
     * 本地数据的初始化
     */
    public static void initDb() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("device_db") //设置数据库名
                .setDbVersion(5) //设置数据库版本,当数据库发生变化时，改变版本号才生效
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                        //开启WAL, 对写入加速提升巨大(作者原话)
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //数据库升级操作
                        /*try {
                            db.addColumn(DeviceOnlineStatus.class,"sensitivity");
                            db.addColumn(DeviceOnlineStatus.class,"carNum");
                            db.addColumn(DeviceOnlineStatus.class,"devInterNum");
                            db.addColumn(DeviceOnlineStatus.class,"driverName");
                            db.addColumn(DeviceOnlineStatus.class,"driverPhone");
                        } catch (DbException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
        db = x.getDb(daoConfig);
    }

    /**
     * 添加数据
     */
    public static boolean add(Object entity) {
        try {
            db.save(entity);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            MyLog.e("保存数据异常："+e.toString());
            return false;
        }
    }

    /**
     * 删除数据,同一个对象
     */
    public static boolean delete(Object entity) {
        try {
            db.delete(entity);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除数据，根据id删除
     */
    public static boolean delete(Class cls,int id) {
        try {
            db.deleteById(cls,id);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改数据
     */
    public static boolean update(Object entity) {
        try {
            db.update(entity);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 修改数据
     */
    public static boolean saveOrUpdate(Object entity) {
        try {
            //WhereBuilder b = WhereBuilder.b();
            //b.and("id","=",id); //构造修改的条件
            db.saveOrUpdate(entity);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查找数据
     */
    public static <T> List<T> find(Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            list = db.findAll(cls);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 查找数据
     */
    public static <T> T findOne(Class<T> cls) {
        T t = null;
        try {
            t = db.findFirst(cls);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return t;
    }

}
