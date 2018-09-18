/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接demo
 */
package com.allinpay.xmltrans.demo;

import com.allinpay.XmlTools;
import com.allinpay.xmltrans.service.TranxServiceImpl;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 特别申明，该demo仅供学习参考，未经过严格测试，不作为通联发布版本，其中有可能存在bug，
 * 请严格参照对接文档的格式要求，以及返回码说明进行系统 对接
 */
public class TranxMain {

	/**
	 * 外网ip；113.108.182.3 
	 * 端口：8083（http）
	 * 端口：443（https）
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String testTranURL1 = "http://172.16.1.11/aipg/query";//通联测试环境，内网（与查询有关的使用）
		String testTranURL="http://113.108.182.3/aipg/ProcessServlet"; //通联测试环境，外网（商户测试使用）
		String testTranURL33="https://113.108.182.3/debugaipg/ProcessServlet"; 
		String testTranURL12="http://172.16.1.11/debugaipg/ProcessServlet";
		String testTranURLqian="http://172.16.1.15:8090/aipg/ProcessServlet";
		String testTranURLquick="https://172.16.1.11:8443/aipg/quickpay";//快捷协议支付内网接口
		String testTranURLquick1="https://113.108.182.3/aipg/quickpay";//快捷协议支付外网接口
		String testTranURL11="https://172.16.1.11:8443/aipg/ProcessServlet"; //通联测试环境，内网（通联内部技术使用）
		String tranURL="http://tlt.allinpay.com/aipg/ProcessServlet";//通联生产环境（商户上线时使用）
		String testURLOfBill="https://113.108.182.3/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1"; //
		String URLOfBill="https://tlt.allinpay.com/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1"; //
		String testURLOfBill1="https://172.16.1.11/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1";
		boolean isfront=false;//是否发送至前置机（由前置机进行签名）如不特别说明，商户技术不要设置为true
		String reqsn="1404826943281";//交易流水号(交易结果查询时，待查询的原支付交易流水号)
		String trx_code,busicode;//100001批量代收 100002批量代付 100011单笔实时代收 100014单笔实时代付
		TranxServiceImpl tranxService=new TranxServiceImpl();
		
		/**
		 * 测试的时候不用修改以下业务代码，但上生产环境的时候，必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		 * 另外，特别说明：如果生产环境对接的时候返回”未开通产品“那么说明该商户开通的接口与目前测试的接口不一样，需要找业务确认
		 * 代收是批量代收接口的简称，代付 是批量代付接口的简称，
		 * 对接报文中，info下面的用户名一般是：商户号+04，比如商户号为：200604000000445，那么对接用户一般为：20060400000044504
		 */
		trx_code="100011";
		//trx_code="100014";
		//trx_code="REFUND";
		if("100011".equals(trx_code) || "100001".equals(trx_code))//收款的时候，填写收款的业务代码
		{busicode="19900";}
		else
		{busicode="09900";}
		//设置安全提供者,注意，这一步尤为重要
		BouncyCastleProvider provider = new BouncyCastleProvider();
		XmlTools.initProvider(provider);
		
		
		//tranxService.batchTranx(testTranURL11,trx_code,busicode, isfront);
		//tranxService.downBills(testTranURL11, isfront);
		//签约结果通知
//		tranxService.signNotice(NOTICEURL, isfront);
		//简单对账文件下载
		//tranxService.downSimpleBills(testURLOfBill1, true);
		//交易查询
	 //tranxService.queryTradedetail(testTranURL, "200604000005134-1533618602715", isfront,"2018080700","2018080800");
		//单笔退款，一般busicode取09200
		//busicode="09200";
		//tranxService.refundTranx(testTranURL11, trx_code, busicode, isfront);
	
		
		//单笔实时代收
	//tranxService.singleTranx(testTranURL,trx_code,"19900",isfront);
		
		//for(int a=0;a<1;a++){
			tranxService.singleTranx(testTranURL,trx_code, busicode,isfront);
			//Thread.sleep(3000);
		//}
		
		
		//商户虚拟户历史余额查询
		//tranxService.merAcctBalance(testTranURL11,isfront,"20180321","20180321");
		
		//单笔实时身份验证
	//tranxService.singleAcctVerifytype(testTranURL,isfront);
		
		//批量身份验证
//		tranxService.batchAcctVerify(testTranURL, isfront);
		//账户余额查询
		//tranxService.acctBalance(testTranURL1, isfront, "20140805", "20140806");
		
		//国民身份验证
//		tranxService.idVerify(testTranURL, isfront);

		//国民身份验证查询
	//tranxService.idVerifyQ(testTranURL, isfront);
		
		//实名付申请
		//tranxService.rnpA(testTranURL11, isfront);
		
		//实名付短信重发
//		tranxService.rnpR(testTranURL11, isfront);
		
		//实名付确认
		//tranxService.rnpC(testTranURL11, isfront);
		
		//实名付结果查询
//	tranxService.rnpQ(tranURL, isfront);
		
		//内部转账
	//	tranxService.acctTransferReq(testTranURL11, isfront);
		//tranxService.tixian(testTranURL11, isfront);
	//卡bin查询
	//tranxService.checkcard(testTranURL12, isfront);
		//快捷签约短信触发AIP1981180404000000061
		//tranxService.quickAcctVerifytype(testTranURLquick, isfront);
	//快捷确认
	//tranxService.quickpaycerdid(testTranURLquick, isfront,"200604000003947-1522811238986");
	//快捷扣款
	//tranxService.quickpay(testTranURLquick, isfront,"AIP1981180330000000041");
//	tranxService.quickpayother(testTranURLquick, isfront);
	
	}

}
