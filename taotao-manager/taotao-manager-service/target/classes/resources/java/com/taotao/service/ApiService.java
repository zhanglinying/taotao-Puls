package com.taotao.service;

import com.taotao.service.httpclient.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApiService implements BeanFactoryAware {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private RequestConfig requestConfig;

    /**
     * 执行Get请求，响应200返回内容，404返回null
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException {
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getCloseableHttpClient().execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * 带有参数的get请求
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException {
        URIBuilder urlBuilder = new URIBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.setParameter(entry.getKey(), entry.getValue());
        }
        return doGet(urlBuilder.build().toString());
    }

    /**
     * 执行post请求
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (null != params) {
            List<NameValuePair> pairList = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet())
                pairList.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            //构造一个form表单式的实体
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
            //将请求参数设置到httpPost对象中
            httpPost.setEntity(urlEncodedFormEntity);
        }
        CloseableHttpResponse response=null;
        try {
            //执行请求
            response=this.getCloseableHttpClient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
        }finally {
            if(response!=null){
                response.close();
            }
        }
    }

    private CloseableHttpClient getCloseableHttpClient(){
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }
}