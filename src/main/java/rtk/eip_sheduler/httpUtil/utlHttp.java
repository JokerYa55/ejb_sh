/*
 * Пакет реализующий функционал протокола HTTP  
 */
package rtk.eip_sheduler.httpUtil;

//import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.jboss.logging.Logger;
import rtk.eip.params.result;
import rtk.eip_sheduler.XMLUtil.utlXML;

/**
 * Кллас реализует методы протокола HTTP
 * @author vasil
 */
public class utlHttp {

    private final Logger log = Logger.getLogger(getClass().getName());

    /**
     * Реализация POST запроса с передачей параметров в виде xml
     * @param url           - аддрес по которому выполняется запрос
     * @param params        - параметры запроса в виде объекта. при отправке преобразуется в xml
     * @param headerList    - header запроса
     * @return 
     */
    public String doPost(String url, Object params, Map<String, String> headerList) {
        log.debug("doPost => " + params.toString());
        String res = null;
        try {
            int timeout = 30;
            // Устанавливаем параметры соединения для передачи poat - запроса
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000).build();

            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();            
            HttpPost post = new HttpPost(url);
            
            // Добавляем параметны
            StringEntity postingString = new StringEntity((String) params, "application/xml", "UTF-8");
            log.debug(postingString.toString());
            post.setEntity(postingString);

            // Добавляем заголовки
            if (headerList != null) {
                headerList.entrySet().stream().forEach((t) -> {
                    Header header = new BasicHeader(t.getKey(), t.getValue());                    
                    post.setHeader(header);
                });
            }
            
            HttpResponse response = httpClient.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                json.append(line);
            }

            res = json.toString();

        } catch (Exception e) {
            log.error(e.getMessage());
            result resErr = new result();
            resErr.setResultCode("-1");
            resErr.setResultComment(e.getMessage());
            utlXML<result> utlxml = new utlXML();
            res = utlxml.convertObjectToXml(resErr);
        }
        return res;
    }

    /**
     * Реализация POST запроса с параметрами в виде списка
     * @param url
     * @param params
     * @param headerList
     * @return
     */
    public String doPost(String url, List params, Map<String, String> headerList) {
        String res = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            if (headerList != null) {
                headerList.entrySet().stream().forEach((t) -> {
                    Header header = new BasicHeader(t.getKey(), t.getValue());
                    post.setHeader(header);
                });
            }

            if (params != null) {
                //System.out.println((new UrlEncodedFormEntity(params)).toString());
                post.setEntity(new UrlEncodedFormEntity(params));
            }

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                //System.out.println(line);
                json.append(line);
            }

            res = json.toString();

        } catch (Exception e) {
            log.error(e.getMessage());
            result resErr = new result();
            resErr.setResultCode("-1");
            resErr.setResultComment(e.getMessage());
            utlXML<result> utlxml = new utlXML();
            res = utlxml.convertObjectToXml(resErr);
        }
        return res;
    }

    /**
     * Реализация GET запроса
     * @param url
     * @param params
     * @param headerList
     * @return
     */
    public String doGet(String url, List params, Map<String, String> headerList) {
        System.out.println("doGet");
        String res = null;

        if (params != null) {
            StringBuilder pStr = new StringBuilder();
            for (Object param : params) {
                pStr.append(param.toString());                
            }
            if (pStr.toString().length() > 0) {
                url = url + "?" + pStr.toString();                
            }
        }

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;

        if (headerList != null) {
            headerList.entrySet().stream().forEach((t) -> {
                Header header = new BasicHeader(t.getKey(), t.getValue());
                request.setHeader(header);
            });
        }

        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                json.append(line);
            }

            log.debug("json = " + json.toString());
            res = json.toString();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result resErr = new result();
            resErr.setResultCode("-1");
            resErr.setResultComment(ex.getMessage());
            utlXML<result> utlxml = new utlXML();
            res = utlxml.convertObjectToXml(resErr);
        }
        return res;
    }

//    /**
//     * Реализация PUT запроса
//     * @param url
//     * @param params
//     * @param headerList
//     * @return
//     */
//    public int doPut(/*String data,*/String url, Object params, Map<String, String> headerList) {
//        System.out.println("doPut => " + url + " param = " + params);
//        int responseCode = -1;
//        HttpClient httpClient = new DefaultHttpClient();
//        try {
//            HttpPut request = new HttpPut(url);
//
//            // Set PARAMS
//            if (params != null) {
//                Gson gson = new Gson();
//                StringEntity paramStr = new StringEntity(gson.toJson(params), "UTF-8");
//                request.setEntity(paramStr);
//            }
//
//            // Set HEADERS
//            if (headerList != null) {
//                headerList.entrySet().stream().forEach((t) -> {
//                    Header header = new BasicHeader(t.getKey(), t.getValue());
//                    request.setHeader(header);
//                });
//            }
//
//            HttpResponse response = httpClient.execute(request);
//            responseCode = response.getStatusLine().getStatusCode();
//            System.out.println("responseCode = " + responseCode);
//            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204) {
//                if (responseCode != 204) {
//                    BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "UTF-8"));
//                    System.out.println("br = " + br);
//                    String output;
//                    // System.out.println("Output from Server ...." + response.getStatusLine().getStatusCode() + "\n");
//                    while ((output = br.readLine()) != null) {
//                        System.out.println(output);
//                    }
//                    System.out.println("output = " + output);
//                }
//            } else {
//                System.out.println(response.getStatusLine().getStatusCode());
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + response.getStatusLine().getStatusCode());
//            }
//        } catch (Exception ex) {
//            log.error("ex Code sendPut: " + ex);
//            log.error("url:" + url);
//            log.error("data:" + params);            
//        } finally {
//            httpClient.getConnectionManager().shutdown();
//        }
//        return responseCode;
//    }
}
