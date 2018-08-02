package com.example.disen.cellpointassessment.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by disen on 7/31/2018.
 */

public class RepositoryContract {

    public static String repositoryAuthority = "com.example.disen.cellpointassessment";
    public static Uri BaseContent = Uri.parse("content://"+repositoryAuthority);
    public static String path = "repository";
    public static Uri Content_Uri = Uri.withAppendedPath(BaseContent, path);

    public static class RepositoryEntry implements BaseColumns{
        public static String db_name = "repositories";
        public static String ColumnID = BaseColumns._ID;
        public static String ColumnRepository_name = "repository_name";
        public static String ColumnCreated = "created";
        public static String ColumnUpdated= "updated";
        public static String ColumnDesc = "desc";
    }
}
