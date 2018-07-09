package com.zoe.spring.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zoe.spring.resultInfo.CompanyInfo;
import com.zoe.spring.resultInfo.HttpDO;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈亚兰 on 2017/11/29.
 */
public class HttpUtils {
    /**
     * 使用URLConnection实现GET请求
     *
     * 1.实例化一个java.net.URL对象； 2.通过URL对象的openConnection()方法得到一个java.net.URLConnection;
     * 3.通过URLConnection对象的getInputStream()方法获得输入流； 4.读取输入流； 5.关闭资源；
     */
    public static<T> T get(String urlStr, TypeToken<T> token) throws Exception
    {

        URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection(); // 打开连接

        System.out.println(urlConnection.getURL().toString());

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // 获取输入流
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        br.close();
//        System.out.println(sb.toString());
        Gson gson=new Gson();
        T t=gson.fromJson(sb.toString(),token.getType());
        return t;
    }

    /**
     * 使用HttpURLConnection实现POST请求
     *
     * 1.实例化一个java.net.URL对象； 2.通过URL对象的openConnection()方法得到一个java.net.URLConnection;
     * 3.通过URLConnection对象的getOutputStream()方法获得输出流； 4.向输出流中写数据； 5.关闭资源；
     */
    public static<T> T post(String urlStr, Map<String, String> parameterMap) throws IOException
    {

        URL url = new URL(urlStr);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true); // 设置该连接是可以输出的
        httpURLConnection.setRequestMethod("POST"); // 设置请求方式
        httpURLConnection.setRequestProperty("charset", "utf-8");



        System.out.println(httpURLConnection.getURL().toString());

        PrintWriter pw = new PrintWriter(new BufferedOutputStream(httpURLConnection.getOutputStream()));

        StringBuffer parameter = new StringBuffer();
//        parameter.append("1=1");
        for (Map.Entry<String, String> entry : parameterMap.entrySet())
        {
            parameter.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        pw.write(parameter.toString());// 向连接中写数据（相当于发送数据给服务器）

        pw.flush();
        pw.close();

        System.out.println("parameter: " + parameter.toString());

        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        { // 读取数据
            sb.append(line + "\n");
        }

        return (T) sb.toString();
    }


    public static String httpPut(String urlPath, String data, String charSet, String[] header)
    {
        String result = null;
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try
        {
            url = new URL(urlPath);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setConnectTimeout(2000000);// 设置连接主机超时（单位：毫秒）
            httpurlconnection.setReadTimeout(2000000);// 设置从主机读取数据超时（单位：毫秒）

            if (header != null)
            {
                for (int i = 0; i < header.length; i++)
                {
                    String[] content = header[i].split(":");
                    httpurlconnection.setRequestProperty(content[0], content[1]);
                }
            }

            httpurlconnection.setRequestMethod("PUT");
            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (StringUtils.isNotBlank(data))
            {
                httpurlconnection.getOutputStream().write(data.getBytes("UTF-8"));
            }
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();
            int code = httpurlconnection.getResponseCode();

            if (code == 200)
            {
                DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());
                int len = in.available();
                byte[] by = new byte[len];
                in.readFully(by);
                if (StringUtils.isNotBlank(charSet))
                {
                    result = new String(by, Charset.forName(charSet));
                } else
                {
                    result = new String(by);
                }
                in.close();
            } else
            {
//                log.error("请求地址：" + urlPath + "返回状态异常，异常号为：" + code);
            }
        } catch (Exception e)
        {
//            log.error("访问url地址：" + urlPath + "发生异常", e);
        } finally
        {
            url = null;
            if (httpurlconnection != null)
            {
                httpurlconnection.disconnect();
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

//         String getUrl="http://localhost:1111/user/all?pageNum={0}&pageSize={1}";
//         String urlStr="http://localhost:1111/user";
//        //get方法测试
//        try {
//           Object resultData= HttpUtils.get(MessageFormat.format(getUrl, new Object[]{1,3}));
//           System.out.print(resultData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//           Map<String,String> map=new HashMap<>();
//           map.put("nickName","jk3");
//           map.put("name","宝贝1号");
//           map.put("password","chenyla");
//           map.put("state","1");
//        try {
//           Object resultData=HttpUtils.post(urlStr,map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String getUrl="http://42.123.99.75:20001/api/changcheng/company/news?page={0}&count={1}";
        try{
            HttpDO resultData= HttpUtils.get(MessageFormat.format(getUrl, new Object[]{1,10}),new TypeToken<HttpDO>(){});
            List<CompanyInfo> list=resultData.getData();
            System.out.println("code:"+resultData.getCode());

            list.forEach(n->{
                System.out.println(n.getName()+"\n"+n.getTitle()+"\n"+n.getContent());
                System.out.println("\n\n★★★★★★★★★★★★★★★★★★★★★★★★★★\n\n");
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

