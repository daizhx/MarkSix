package daizhx.com.marksix;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author daizhx
 *
 */
public class HttpUtils {
	private static HttpUtils mInstance;
	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	private static Context mCtx;


	private HttpUtils(Context context){
		mCtx = context;
		mRequestQueue = getmRequestQueue();
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
			mImageLoader = new ImageLoader(mRequestQueue,new ImageLoader.ImageCache(){
                private final LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>(20);
                @Override
                public Bitmap getBitmap(String url) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
						return cache.get(url);
					}
					return null;
				}

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
						cache.put(url,bitmap);
					}
				}
            });
		}

	}

	public static HttpUtils getInstance(Context context){
		if(mInstance == null){
			synchronized (HttpUtils.class){
				if(mInstance == null){
					mInstance = new HttpUtils(context);
				}
			}
		}
		return mInstance;
	}

	public RequestQueue getmRequestQueue(){
		if(mRequestQueue == null){
			mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req){
		getmRequestQueue().add(req);
	}

	public ImageLoader getImageLoader(){
		return mImageLoader;
	}

	


    public String getUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(1000);
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            if (response == 200) {
                is = conn.getInputStream();
                String contentString = read(is);
                return contentString;
            } else {
                return null;
            }
        }finally {
            if(is != null){
                is.close();
            }
        }
    }

    public JSONObject getJsonUrl(String url) throws JSONException, IOException {
        String s = getUrl(url);
        JSONObject jsonObject = new JSONObject(s);
        return jsonObject;
    }

    private String read(InputStream is) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Reader reader = new InputStreamReader(is,"UTF-8");
        char[] buffer = new char[1024];
        int len = -1;
        while ((len = reader.read(buffer)) != -1){
            stringBuilder.append(buffer,0,len);
        }
        return stringBuilder.toString();
    }
}
