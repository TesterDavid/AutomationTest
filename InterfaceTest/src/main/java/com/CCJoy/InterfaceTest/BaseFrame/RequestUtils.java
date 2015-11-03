package com.CCJoy.InterfaceTest.BaseFrame;

import java.io.*;
import java.util.*;

import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpDelete;
import net.sf.json.JSONObject;

public class RequestUtils {
    ReportUtils report = new ReportUtils();
    static DataUtils dataUtil = new DataUtils();
    private static HttpHost host = new HttpHost(ConfigUtils.HOST, Integer.parseInt(ConfigUtils.PORT), "https");
    SQLserverUtils sqldata = new SQLserverUtils();

    public RequestUtils() {

        // TODO Auto-generated constructor stub
    }

    /**
     * TODO basic auth认证
     *
     * @author 邱卫武
     */
    private HttpClientContext createBasicAuthContext(String user_id, String password) {
        // 使用用户id，密码构建一个basic auth登录数据
        Credentials defaultCreds = new UsernamePasswordCredentials(user_id, password);
        // 构建一个默认的凭据提供器
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        // 认证主机名，端口号，认证模式
        credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), defaultCreds);
        // 构建authcache
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);
        // 将authcache跟basicauth登录数据放入context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);
        // 打印context
        //report.highLight(context.toString());
        return context;
    }

    /**
     * TODO Get请求方式
     *
     * @author 邱卫武
     */
    public String doGet(String url) {
        report.log("开始执行GET请求");
        String responseResult = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // 创建HttpGet
            HttpGet doGet = new HttpGet(url);
            report.highLight("执行请求的URI为：" + doGet.getURI());
            // 执行get请求
            CloseableHttpResponse response = httpClient.execute(doGet);
            try {
                if (response != null) {
                    responseResult = EntityUtils.toString(response.getEntity(),"utf-8");
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接，释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseResult;
    }

    /**
     * TODO :Get请求方式basic auth 认证
     *
     * @author :邱卫武
     */
    public String doGet_auth(String url, List<String> LoginData) {
        report.log("开始执行GET basic auth请求");
        String jsonObject = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 从LoginData中取出user_id ,password
        String user_id = LoginData.get(0);
        String password = LoginData.get(1);
        report.log("usserid为:" + user_id);
        report.log("password为:" + password);
        try {
            // 创建HttpGet
            HttpGet doGet = new HttpGet(url);
            report.highLight("执行请求的URI为:" + doGet.getURI());
            HttpClientContext context = createBasicAuthContext(user_id, password);
            // 执行doGet请求
            CloseableHttpResponse response = httpClient.execute(doGet, context);
            try {
                if (response != null) {
                    jsonObject = EntityUtils.toString(response.getEntity());
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接，释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * TODO Delete请求方式
     *
     * @author 邱卫武
     */
    public String doDelete(String url) {
        report.log("开始执行DELETE请求");
        String jsonobject = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // 创建httpDelete
            HttpDelete doDelete = new HttpDelete(url);
            report.highLight("执行请求的URI为：" + doDelete.getURI());
            // 执行httppost
            CloseableHttpResponse response = httpClient.execute(doDelete);
            // 打印结果
            try {
                if (response != null) {
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    } else {
                        // 将响应内容转化成JSON对象
                        jsonobject = EntityUtils.toString(response.getEntity());
                        //report.log("响应内容为：" + EntityUtils.toString(response.getEntity()));
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonobject;
    }

    /**
     * TODO Delete请求方式basic auth 认证
     *
     * @author 作者 邱卫武:
     */
    public String doDelete_auth(String url, List<String> LoginData) {
        report.log("开始执行DELETE basic auth请求");
        JSONObject jsonresult;
        String result = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 从LoginData中取出user_id ,password
        String user_id = LoginData.get(0).trim();
        String password = LoginData.get(1).trim();
        report.log("usserid为：" + user_id);
        report.log("password为：" + password);
        try {
            // 创建HttpDelete
            HttpDelete doDelete = new HttpDelete(url);
            report.highLight("执行请求的URI为：" + doDelete.getURI());
            HttpClientContext context = createBasicAuthContext(user_id, password);
            // 执行delete请求
            CloseableHttpResponse response = httpClient.execute(doDelete, context);

            try {
                if (response != null) {

                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode == 200) {
                        result = EntityUtils.toString(response.getEntity(), "utf-8");
                        jsonresult = JSONObject.fromString(result);
                       // report.log("响应内容为：" + EntityUtils.toString(response.getEntity()));
                        // 判断IsSuccess是否为ture，
                        if (dataUtil.getResultDataBy_one(jsonresult, "IsSuccess").equals("ture")) {
                            report.log("请求" + url + "成功");
                        } else {
                            report.error("IsSuccess为false，请求失败");
                            return null;
                        }
                    } else {
                        report.error("请求失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接，释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * TODO Post请求方式
     *
     * @author 作者 邱卫武:
     */
    public String doPost(String url, JSONObject jsonObject) {
        report.log("开始执行POST请求");
        String result = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httppost
        HttpPost dopost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        try {
            dopost.setEntity(entity);
            report.highLight("执行请求的URI为：" + dopost.getURI());
            report.greenLight("请求参数为：" + jsonObject);
            // 执行httppost
            CloseableHttpResponse response = httpClient.execute(dopost);
            // 打印结果
            try {
                if (response != null) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * TODO Post请求方式basic auth 认证
     *
     * @author 作者 邱卫武:
     */
    public String doPost_auth(String url, List<String> LoginData, JSONObject jsonObject) {
        report.log("开始执行POST basic auth请求");
        String jsonobject = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 从LoginData中取出user_id ,password
        String user_id = LoginData.get(0);
        String password = LoginData.get(1);
        report.log("usserid为：" + user_id);
        report.log("password为：" + password);
        // 创建httppost
        HttpPost dopost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        try {
            dopost.setEntity(entity);
            report.highLight("执行请求的URI为：" + dopost.getURI());
            report.greenLight("请求参数为：" + jsonObject);
            HttpClientContext context = createBasicAuthContext(user_id, password);
            // 执行httppost
            CloseableHttpResponse response = httpClient.execute(dopost, context);
            // 打印结果
            try {
                if (response != null) {
                    jsonobject = EntityUtils.toString(response.getEntity());
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonobject;
    }

    /**
     * TODO Put请求方式
     *
     * @author 作者 邱卫武
     */
    public String doPut(String url, JSONObject jsonObject) {
        report.log("开始执行PUT请求");
        String jsonobject = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httppost
        HttpPut doput = new HttpPut(url);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        try {
            doput.setEntity(entity);
            report.highLight("执行请求的URI为：" + doput.getURI());
            report.greenLight("请求参数为：" + jsonobject);
            // 执行httppost
            CloseableHttpResponse response = httpClient.execute(doput);
            // 打印结果
            try {
                if (response != null) {
                    jsonobject = EntityUtils.toString(response.getEntity());
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonobject;
    }

    /**
     * TODO Put请求方式basic auth 认证
     *
     * @author 作者 邱卫武:
     */
    public String doPut_auth(String url, List<String> LoginData, JSONObject jsonObject) {
        report.log("开始执行PUT basic auth请求");
        String jsonobject = null;
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 从LoginData中取出user_id ,password
        String user_id = LoginData.get(0);
        String password = LoginData.get(1);
        report.log("usserid为：" + user_id);
        report.log("password为：" + password);
        // 创建httppost
        HttpPut doput = new HttpPut(url);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        try {
            doput.setEntity(entity);
            report.highLight("执行请求的URI为：" + doput.getURI());
            report.greenLight("请求参数为：" + jsonobject);
            HttpClientContext context = createBasicAuthContext(user_id, password);
            // 执行httppost
            CloseableHttpResponse response = httpClient.execute(doput, context);
            // 打印结果
            try {
                if (response != null) {
                    jsonobject = EntityUtils.toString(response.getEntity());
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonobject;
    }

    /**
     * TODO 获取登录后的user_id跟password，用于basic auth验证
     *
     * @author 作者 邱卫武:
     */
    public List<String> doPostBy_login(String url, JSONObject jsonObject) {
        report.log("开始执行登录POST请求");
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建数组LoginData，用于接收user_id,password
        List<String> LoginData = new ArrayList<String>();
        // 创建Map，中转JSON对象
        HashMap<String, String> dataMap = new HashMap<String, String>();
        // 创建httppost
        HttpPost dopost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        try {
            dopost.setEntity(entity);
            report.highLight("执行请求的URI为：" + dopost.getURI());
            report.greenLight("请求参数为：" + jsonObject);
            // 执行httppost
            CloseableHttpResponse response = httpClient.execute(dopost);
            try {
                if (response != null) {

                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode != 200) {
                        report.error("访问失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                    } else {
                        // 获取返回的JSON数据
                        JSONObject jsonobject = JSONObject.fromString(EntityUtils.toString(response.getEntity()));

                        if (!jsonobject.get("Data").toString().equals("null")) {
                            JSONObject jsondata = jsonobject.getJSONObject("Data");
                            // 遍历jsondata对象，并存入Map
                            Iterator<String> it = jsondata.keys();
                            while (it.hasNext()) {
                                String key = String.valueOf(it.next());
                                String value = jsondata.get(key).toString();
                                dataMap.put(key, value);
                            }
                            // 取data中的user_id跟token并放入数组
                            LoginData.add(dataMap.get("user_id") + "|1|1");
                            LoginData.add(dataMap.get("token"));
                        } else {
                            report.error("密码错误或账户已禁用");
                            return null;
                        }
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return LoginData;
    }

    /**
     * TODO：Logout专用Delete请求
     *
     * @author 作者：邱卫武
     */
    public boolean doDelete_logout(String url, List<String> LoginData) {
        report.log("开始执行退出DELETE请求");
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 从LoginData中取出user_id ,password
        String user_id = LoginData.get(0);
        String password = LoginData.get(1);
        report.log("登录的用户名为：" + user_id);
        report.log("登录的密码为：" + password);
        try {
            // 创建HttpDelete
            HttpDelete doDelete = new HttpDelete(url);
            report.highLight("执行请求的URI为：" + doDelete.getURI());
            HttpClientContext context = createBasicAuthContext(user_id, password);
            // 执行delete请求
            CloseableHttpResponse response = httpClient.execute(doDelete, context);

            try {
                if (response != null) {
                    // 获取状态码
                    int statuCode = response.getStatusLine().getStatusCode();
                    // 若状态码不为200，则关闭response，若为200，则获取返回信息
                    if (statuCode == 200) {
                        report.log("退出成功");

                    } else {
                        report.error("请求失败，返回的状态码为：" + statuCode + response.getStatusLine());
                        response.close();
                        return false;
                    }
                } else {
                    report.log("response为空");
                }
            } finally {
                assert response != null;
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接，释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * TODO login_auth
     *
     * @author 邱卫武
     */
    public List<String> login_auth(String url, String tablename, int dataindex) throws Exception {
        JSONObject jsonObject = sqldata.getData_login(tablename, dataindex);
        List<String> result = doPostBy_login(url, jsonObject);
        if (result == null) {
            report.error("没有获取到userid跟password");
            return null;
        }
        return result;
    }


}


