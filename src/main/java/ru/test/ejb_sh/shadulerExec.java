/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.test.ejb_sh;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import javax.ejb.Local;
import org.jboss.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import rtk.eip.params.addUserParam;
import rtk.eip.params.result;

/**
 *
 * @author Vasiliy.Andricov
 */
@Singleton
@Local(shadulerExec.class)
public class shadulerExec {

    private Logger log = Logger.getLogger(getClass().getName());
    private long i = 0;

    @Schedule(minute = "*/1", hour = "*")
    public void runSh() {
        try {
            i++;
            log.info("i = " + i);
            int timeout = 30;
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000).build();
            HttpClient http = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

            log.info("http = " + http.toString());
           
            HttpGet get = new HttpGet("http://www.sql.ru");
            HttpResponse response = http.execute(get);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                json.append(line);
            }

            addUserParam par1 = new addUserParam();
            par1.setAutoCreateFlag(0);
            par1.setDob(new Date());
            par1.setName("Иванов");
            par1.setRegion("23");
            System.out.println(par1.convertObjectToXml());

            result res = new result();
            res.setResultCode("0");
            res.setResultComment("Yes");
            //System.out.println(res.convertObjectToXml());
            System.out.println(res.convertFromXml(res.convertObjectToXml()));
            
        } catch (Exception e) {
            log.info("e = " + e.getMessage());
        }
    }
}
