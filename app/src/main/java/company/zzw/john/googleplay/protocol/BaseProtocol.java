package company.zzw.john.googleplay.protocol;

import android.support.annotation.NonNull;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import company.zzw.john.googleplay.conf.Constants;
import company.zzw.john.googleplay.utils.FileUtils;
import company.zzw.john.googleplay.utils.IOUtils;

/**
 * Created by ZheWei on 2016/6/1.
 */
public abstract class BaseProtocol<T> {

    public T loadData(int index) throws Exception {

        /**从本地文件中得到数据*/
        T localData = getDataFromLocal(index);
        if (localData != null) {
            Log.d("aaa", "真正从文件中得到数据");
            return localData;
        }

        /**从网络得到数据*/
        String result = getDataFromNet(index);
        Log.d("aaa", "从网络中获取数据");

        T bean = parseBean(result);
        return bean;
    }

    /**
     * 从本地得到缓存的网络数据
     */
    private T getDataFromLocal(int index) {
        File cacheFile = getCacheFile(index);
        BufferedReader reader = null;
        if (cacheFile.exists()) {
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                //读取第一行
                String currentTimeMillis = reader.readLine();//读取时间戳
                if (System.currentTimeMillis() - Long.parseLong(currentTimeMillis) < Constants.PROTICOLOUTTIME) {
                    //如果缓存数据没有过期
                    //读取缓存内容
                    String jsonData = reader.readLine();

                    T bean = parseBean(jsonData);
                    return bean;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    /**
     * 得到具体的网络接口的文件,依赖于子类
     */
    @NonNull
    private File getCacheFile(int index) {
        String dir = FileUtils.getDir("json");
        String name = getInterfaceKey() + "." + index;
        return new File(dir, name);
    }

    /**
     * 从网络请求数据,并将其保存到本地文件中
     */
    private String getDataFromNet(int index) throws HttpException, IOException {
        //发送网络请求
        HttpUtils httpUtils = new HttpUtils(2000);
        //
        String url = Constants.URLS.BASE_URL + getInterfaceKey();

        RequestParams params = new RequestParams();
        params.addQueryStringParameter("index", index + "");
        ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);
        String jsonData = responseStream.readString();

        //在这里把数据保存到文件中
        BufferedWriter writer = null;
        try {
            File cacheFile = getCacheFile(index);
            writer = new BufferedWriter(new FileWriter(cacheFile));
            // 第一行写入时间戳
            writer.write(System.currentTimeMillis() + "");
            //进行换行
            writer.write("\r\n"); //换行符-->\r\n
            //第二行写入json字符串
            writer.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }

        return jsonData;

    }

    /**
     * 传入接口的关键字,由子类来完成
     *
     * @return
     */
    public abstract String getInterfaceKey();

    /**
     * 把得到的json字符串进行解析成为 bean
     * 由具体的子类来完成
     *
     * @param result
     * @return
     */
    protected abstract T parseBean(String result);

}
