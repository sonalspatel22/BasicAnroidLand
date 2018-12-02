package com.land.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;

import com.land.R;
import com.land.constant.ApiConstant;
import com.land.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Dashrath on 10/26/2017.
 */

public class MyHttpClientHelper implements Callback {

    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 20000;
    private static final int WRITE_TIMEOUT = 20000;
    private static MyHttpClientHelper ourInstance = new MyHttpClientHelper();
    public AlertDialog alertDialogNoInternet;
    public AlertDialog alertDialogRetry;
    private OkHttpClient mOkHttpClient;
    private HashMap<Call, MyRequestWrapper> mResponseHelperHashMap;

    private MyHttpClientHelper() {
        this.mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
        this.mResponseHelperHashMap = new HashMap<>();
    }

    public static MyHttpClientHelper getInstance() {
        return ourInstance;
    }

    public Call enqueueCallforCurrancy(Context context, String url, RequestBody requestBody, ProcessResponseHelper processResponseHelper) {
        if (NetworkUtil.getConnectivityStatus(context) == 0) {

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            showNoInternateConnection(request, context, url, processResponseHelper);

            try {

                new MyRequestWrapper(context, url, processResponseHelper).getResponseHelper().onFail();
                new MyRequestWrapper(context, url, processResponseHelper).getResponseHelper().onFinish();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;

        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            return processCall(request, new MyRequestWrapper(context, url, processResponseHelper));
        }
    }

    public Call enqueueCall(Context context, String url, RequestBody requestBody, ProcessResponseHelper processResponseHelper) {
        if (NetworkUtil.getConnectivityStatus(context) == 0) {

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .header("Content-Type", ApiConstant.CONTENT_TYPE)
                    .header(ApiConstant.AUTHENTICATION_KEY, ApiConstant.AUTHENTICATION_VALUE)
                    .build();

            showNoInternateConnection(request, context, url, processResponseHelper);

            try {

                new MyRequestWrapper(context, url, processResponseHelper).getResponseHelper().onFail();
                new MyRequestWrapper(context, url, processResponseHelper).getResponseHelper().onFinish();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;

        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .header("Content-Type", ApiConstant.CONTENT_TYPE)
                    .header(ApiConstant.AUTHENTICATION_KEY, ApiConstant.AUTHENTICATION_VALUE)
                    .build();
            return processCall(request, new MyRequestWrapper(context, url, processResponseHelper));
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        LogHelper.e("onair", "onFailure");
        e.printStackTrace();

        try {
            mResponseHelperHashMap.get(call).getResponseHelper().onFail();
            mResponseHelperHashMap.get(call).getResponseHelper().onFinish();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void cancelCall(Call call) {

        if (call != null && !call.isCanceled() && !call.isExecuted()) {
            call.cancel();
        }
        mResponseHelperHashMap.remove(call);
    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            if (call == null || mResponseHelperHashMap.get(call) == null) {
                return;
            }
            String responseString = response.body().string();
            LogHelper.d("URL", mResponseHelperHashMap.get(call).getUrl());
            LogHelper.d("Response", responseString);
            try {
                JSONObject jsonObject = new JSONObject(responseString);

                if (jsonObject.getInt(ApiConstant.JSON_KEY_CODE) == 10) {
                    //showLogoutDialog(call);
                } else {
                    MyRequestWrapper requestWrapper = mResponseHelperHashMap.get(call);
                    if (requestWrapper != null) {
                        try {
                            mResponseHelperHashMap.get(call).getResponseHelper().onResponse(jsonObject);
                            mResponseHelperHashMap.get(call).getResponseHelper().onFinish();
                            mResponseHelperHashMap.remove(call);
                            call = null;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                }

            } catch (JSONException e) {
                LogHelper.e("onair", "e:" + e);
                e.printStackTrace();
                showRetryDialogDialog(call);
            }
        } catch (Exception e) {
            LogHelper.e("onair", "e:" + e);
            e.printStackTrace();
            showRetryDialogDialog(call);
        }

        try {
            if (call != null) {
                mResponseHelperHashMap.get(call).getResponseHelper().onFinish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Call processCall(Request request, MyRequestWrapper requestWrapper) {
        Call call = mOkHttpClient.newCall(request);
        mResponseHelperHashMap.put(call, requestWrapper);
        call.enqueue(this);
        requestWrapper.getResponseHelper().onRequest();
        return call;
    }

    private void showRetryDialogDialog(final Call call) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                          public void run() {
                              try {
                                  if (mResponseHelperHashMap == null || call == null || mResponseHelperHashMap.get(call) == null || mResponseHelperHashMap.get(call).getContext() == null) {
                                      return;
                                  }
                                  if (alertDialogRetry != null) {
                                      alertDialogRetry.dismiss();
                                  }
                               /*   if (mResponseHelperHashMap.get(call).getUrl().equalsIgnoreCase(UrlConstant.API_LOVE_IT_URL) ||
                                          mResponseHelperHashMap.get(call).getUrl().equalsIgnoreCase(UrlConstant.API_PARTNER_PREFERENCE_URL))
                                  {
                                      return;
                                  }*/
                                  alertDialogRetry = new AlertDialog.Builder(mResponseHelperHashMap.get(call).getContext(), R.style.AppTheme_AlertDialogTheme)
                                          .setTitle(mResponseHelperHashMap.get(call).getContext().getString(R.string.app_name)).setMessage("Check internet connection & Retry")
                                          .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {
                                                  //TODO:Cancel callback code here
                                                  dialogInterface.dismiss();
                                              }
                                          }).setCancelable(false).create();

                                  alertDialogRetry.show();

                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                          }
                      }
                );
    }

   /* private void showLogoutDialog(final Call call) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                if (mResponseHelperHashMap == null || call == null || mResponseHelperHashMap.get(call) == null || mResponseHelperHashMap.get(call).getContext() == null) {
                    return;
                }
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(mResponseHelperHashMap.get(call).getContext(), R.style.AppTheme_AlertDialogTheme)
                            .setTitle("Logged In Expired")
                            .setMessage("Your login token expired. Please proceed to logout")
                            .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //TODO:Logout callback code here

                                    AlertDialog myDialog = (AlertDialog) dialogInterface;
                                    Context context = myDialog.getContext();
                                    new MyPreference(context).clear();
                                    new RealmDB(context).clearDatabase();
                                    cancelAllCalls();
                                    dialogInterface.dismiss();
                                    context.startActivity(new Intent(context, LoginActivity.class));

                                    EventBus.getDefault().post(new LogoutEvent());

                                }
                            }).setCancelable(false).create();

                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
*/

    public void showNoInternateConnection(final Request request, final Context context, final String url, final ProcessResponseHelper processResponseHelper) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                          public void run() {
                              if (alertDialogNoInternet != null) {
                                  alertDialogNoInternet.dismiss();
                              }
                              try {
                                  alertDialogNoInternet = new AlertDialog.Builder(context, R.style.AppTheme_AlertDialogTheme)
                                          .setTitle(context.getString(R.string.app_name)).setMessage("Check internet connection & Retry")
                                          .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {

                                                  //TODO:Cancel callback code here
                                                  dialogInterface.dismiss();
                                              }
                                          }).setCancelable(false).create();

                                  alertDialogNoInternet.show();
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                          }
                      }
                );
    }

    public void cancelAllCalls() {

        for (Call call : mResponseHelperHashMap.keySet()) {
            call.cancel();
        }
        mResponseHelperHashMap = new HashMap<>();
    }

    public interface ProcessResponseHelper {
        void onRequest();

        void onResponse(JSONObject object) throws Exception;

        void onFinish() throws Exception;

        void onFail() throws Exception;
    }

    public class MyRequestWrapper {
        private Context mContext;
        private String mUrl;
        private ProcessResponseHelper mResponseHelper;

        public MyRequestWrapper(Context mContext, String url, ProcessResponseHelper mResponseHelper) {
            this.mContext = mContext;
            this.mUrl = url;
            this.mResponseHelper = mResponseHelper;
        }

        public Context getContext() {
            return mContext;
        }

        public String getUrl() {
            return mUrl;
        }

        public ProcessResponseHelper getResponseHelper() {
            return mResponseHelper;
        }
    }

}

