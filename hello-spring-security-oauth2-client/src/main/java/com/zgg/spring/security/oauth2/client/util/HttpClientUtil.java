package com.zgg.spring.security.oauth2.client.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Z先生
 * @date : 2020-08-22 16:10
 **/
public class HttpClientUtil {
    private static CloseableHttpClient client;
    private static CloseableHttpResponse response;
    private static HttpEntity entity;
    /**
     * 获取页面内容
     * @param url	访问路径
     * @param headers	请求头
     * @param encoding	编码
     * @return
     */
    public static JSONObject GetBody(String url, Map<String,String> param , Map<String,String> headers, String encoding){
        JSONObject jsonObject=null;
        CookieStore cookieStore=new BasicCookieStore();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).build();
        //创建httpclient对象
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultRequestConfig(requestConfig).setConnectionManagerShared(true).build();
        //client = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultRequestConfig(requestConfig).build();

        try {

            URIBuilder uriBuilder = new URIBuilder(url);
            if(param!=null){
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(),entry.getValue());
                }
            }

            //创建get方式请求对象
            HttpGet httpget = new HttpGet(uriBuilder.build());

            if(headers!=null){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpget.setHeader(entry.getKey(),entry.getValue());
                }
            }

            response = client.execute(httpget);
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取结果实体
                entity = response.getEntity();
                if (entity != null) {
                    jsonObject= JSONObject.parseObject(EntityUtils.toString(entity));
                }
            }
        }
        catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally{
            try {
                EntityUtils.consume(entity);
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONObject PostBody(String url, Map<String,String> param,Map<String,String> headers,String encoding){
        JSONObject jsonObject=null;

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).build();

        //创建httpclient对象
        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        //装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if(param!=null){
            for (Map.Entry<String, String> entry : param.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置请求头
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        try {
            response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                entity = response.getEntity();
                System.out.println("response:"+entity);
                if (entity != null) {
                    jsonObject=JSONObject.parseObject(EntityUtils.toString(entity));
                    System.out.println("jsonObject:"+jsonObject);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
    public static JSONObject PutBody(String url, Map<String,String> param,Map<String,String> headers,String encoding){
        JSONObject jsonObject=null;

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).build();

        //创建httpclient对象
        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        //创建post方式请求对象
        HttpPut httpPut = new HttpPut(url);
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(param!=null){
            for (Map.Entry<String, String> entry : param.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        try {
            httpPut.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置请求头
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.setHeader(entry.getKey(),entry.getValue());
            }
        }
        try {
            response = client.execute(httpPut);
            if(response.getStatusLine().getStatusCode()==200){
                entity = response.getEntity();
                if (entity != null) {
                    jsonObject=JSONObject.parseObject(EntityUtils.toString(entity));
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONObject PostBody(String url, String jsonPara,Map<String,String> headers,String encoding){
        JSONObject jsonObject=null;

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).build();

        //创建httpclient对象
        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        //装填参数
        StringEntity stringEntity = new StringEntity(jsonPara,encoding);
        httpPost.setEntity(stringEntity);

        httpPost.setHeader("Content-type", "application/json");
        //设置请求头
        if(headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        try {
            response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                entity = response.getEntity();

                if (entity != null) {
                    jsonObject=JSONObject.parseObject(EntityUtils.toString(entity));
                    System.out.println("jsonObject:"+jsonObject);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}

