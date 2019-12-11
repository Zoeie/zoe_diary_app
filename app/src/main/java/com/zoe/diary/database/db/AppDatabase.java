package com.zoe.diary.database.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zoe.diary.app.DiaryApplication;
import com.zoe.diary.database.dao.DiaryColorDao;
import com.zoe.diary.database.dao.DiaryContentDao;
import com.zoe.diary.database.dao.DiaryImgDao;
import com.zoe.diary.database.dao.DiaryTagDao;
import com.zoe.diary.database.domain.DiaryColor;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.database.domain.ImgInfo;
import com.zoe.diary.database.domain.TagInfo;

/**
 * author zoe
 * created 2019/6/25 13:43
 */

//entities表示要包含哪些表；version为数据库的版本，数据库升级时更改；exportSchema是否导出数据库结构，默认为true
@Database(entities = {DiaryInfo.class, ImgInfo.class, TagInfo.class, DiaryColor.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DiaryContentDao getDiaryContentDao();
    public abstract DiaryTagDao getDiaryTagDao();
    public abstract DiaryImgDao getDiaryImgDao();
    public abstract DiaryColorDao getDiaryColorDao();

    //单例
    public static AppDatabase getDatabase() {
        return Holder.instance;
    }

    private static class Holder {
        private static final AppDatabase instance = Room.databaseBuilder(DiaryApplication.getInstance(), AppDatabase.class, "diary")
                .allowMainThreadQueries()   //设置允许在主线程进行数据库操作，默认不允许，建议都设置为默认
                .fallbackToDestructiveMigration()  //设置数据库升级的时候清除之前的所有数据
//                .addMigrations(MIGRATION_1_2)
                .build();
    }

    /**
     * 数据库升级  version1 -> version2
     */
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //在vod数据库中新增media cache表
            database.execSQL("CREATE TABLE IF NOT EXISTS media_cache(requestUrl text PRIMARY KEY NOT NULL,json text NOT NULL)");
            //在Vod数据库新增banner vod表
            database.execSQL("CREATE TABLE IF NOT EXISTS banner_vod(int pos PRIMARY KEY NOT NULL,url text NOT NULL)");
        }
    };
}

