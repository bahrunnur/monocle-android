package com.bahrunnur.monocle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

import com.bahrunnur.monocle.models.PostList;

public class PostCache {

	private static final String LAST_POST_PREFIX_FILENAME = "lastMonocle";
	private static final String TAG = "PostCache";
	
	public static final String POPULAR_TYPE = "popular";
	public static final String NEWEST_TYPE = "newest";
	
	private final Context mContext;
	
	public PostCache(Context context) {
		this.mContext = context;
	}
	
	public PostList getCache(String type) {
		ObjectInputStream obj = null;
		
		try {
			obj = new ObjectInputStream(new FileInputStream(getCacheFilePath(type)));
			Object rawPost = obj.readObject();
			
			if (rawPost instanceof PostList)
				return (PostList) rawPost;
			
		} catch (Exception e) {
			Log.e(TAG, "Could not get the cache" + e);
		} finally {
			if (obj != null) {
                try {
                    obj.close();
                } catch (IOException e) {
                    Log.e(TAG, "Could not close the cache file", e);
                }
            }
		}
		return null;
	}
	
	public void setCache(String type, final PostList postList) {
		ObjectOutputStream obj = null;
		
		try {
			obj = new ObjectOutputStream(new FileOutputStream(getCacheFilePath(type)));
			obj.writeObject(postList);
		} catch (Exception e) {
			Log.e(TAG, "Could not set the cache" + e);
		} finally {
			if (obj != null) {
				try {
					obj.close();
				} catch (IOException e) {
					Log.e(TAG, "Could not close the cache file", e);
				}
			}
		}
	}
	
	private String getCacheFilePath(String type) {
		File appDataDir = mContext.getFilesDir(); 
		return appDataDir.getAbsolutePath() + "/" + LAST_POST_PREFIX_FILENAME + "_" + type;
	}
	
}
