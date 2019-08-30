package com.yascn.smartpark;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yascn.smartpark.bean.SearchGaodeHistoryBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SEARCH_GAODE_HISTORY_BEAN".
*/
public class SearchGaodeHistoryBeanDao extends AbstractDao<SearchGaodeHistoryBean, String> {

    public static final String TABLENAME = "SEARCH_GAODE_HISTORY_BEAN";

    /**
     * Properties of entity SearchGaodeHistoryBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Lat = new Property(0, double.class, "lat", false, "LAT");
        public final static Property Lon = new Property(1, double.class, "lon", false, "LON");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Snaippt = new Property(3, String.class, "snaippt", false, "SNAIPPT");
        public final static Property Id = new Property(4, String.class, "id", true, "ID");
    };


    public SearchGaodeHistoryBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SearchGaodeHistoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SEARCH_GAODE_HISTORY_BEAN\" (" + //
                "\"LAT\" REAL NOT NULL ," + // 0: lat
                "\"LON\" REAL NOT NULL ," + // 1: lon
                "\"TITLE\" TEXT," + // 2: title
                "\"SNAIPPT\" TEXT," + // 3: snaippt
                "\"ID\" TEXT PRIMARY KEY NOT NULL );"); // 4: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SEARCH_GAODE_HISTORY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SearchGaodeHistoryBean entity) {
        stmt.clearBindings();
        stmt.bindDouble(1, entity.getLat());
        stmt.bindDouble(2, entity.getLon());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String snaippt = entity.getSnaippt();
        if (snaippt != null) {
            stmt.bindString(4, snaippt);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(5, id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SearchGaodeHistoryBean entity) {
        stmt.clearBindings();
        stmt.bindDouble(1, entity.getLat());
        stmt.bindDouble(2, entity.getLon());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String snaippt = entity.getSnaippt();
        if (snaippt != null) {
            stmt.bindString(4, snaippt);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(5, id);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4);
    }    

    @Override
    public SearchGaodeHistoryBean readEntity(Cursor cursor, int offset) {
        SearchGaodeHistoryBean entity = new SearchGaodeHistoryBean( //
            cursor.getDouble(offset + 0), // lat
            cursor.getDouble(offset + 1), // lon
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // snaippt
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SearchGaodeHistoryBean entity, int offset) {
        entity.setLat(cursor.getDouble(offset + 0));
        entity.setLon(cursor.getDouble(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSnaippt(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final String updateKeyAfterInsert(SearchGaodeHistoryBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(SearchGaodeHistoryBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
