package com.allinpay.xmltrans.controller;

import com.allinpay.xmltrans.service.TranxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tltbackMsg")
public class tltTestCtrl {
    TranxServiceImpl tranxService = new TranxServiceImpl();
    @Autowired
    private void setTranxService(TranxServiceImpl tranxService){this.tranxService=tranxService;}

    @RequestMapping("index")
    //@ResponseBody
    public String comIn(){
        return "/showUser";
    }

    /**
     * 添加测试案例
     * @return
     */
    @RequestMapping("addData")
    @ResponseBody
    public String addTest(String cusi){
        System.out.println(cusi);
        String trxcode = "101003";
        String acctno = "0";
        String acctname = "0";
        String idno = "0";
        String bankcode = "0";
        String propval = cusi+"04";
        String delay = "0";//测试交易的延迟时间，就用默认的0就行
        String errcode = "0000";//错误码
        String dataType = "0";
        System.out.println(sendGet("http://113.108.182.3:8282/techsp/helper/tranx/addTestdata","flag=1&cusi="+cusi+"&acctno="+acctno+"&acctname="+acctname+"&idno="+idno
                +"&bankcode="+bankcode+"&trxcode="+trxcode+"&propval="+propval+"&delay="+delay+"&errcode="+errcode
                +"&dataType="+dataType));
        return sendGet("http://113.108.182.3:8282/techsp/helper/tranx/addTestdata","flag=1&cusi="+cusi+"&acctno="+acctno+"&acctname="+acctname+"&idno="+idno
                +"&bankcode="+bankcode+"&trxcode="+trxcode+"&propval="+propval+"&delay="+delay+"&errcode="+errcode
                +"&dataType="+dataType);
    }

    //get方法请求
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            //System.out.println("发送参数信息为:"+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                //判断html中有更新成功的字样的话，返回
                if (line.contains("更新成功!")){
                    return "通联通更新OK！";
                }
                result += line+"\n";
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            return "更新tlt数据异常";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
