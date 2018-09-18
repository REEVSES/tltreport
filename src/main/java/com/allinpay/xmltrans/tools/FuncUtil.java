/*通联支付网络服务股份有限公司版权所有
 *未经授权严禁查看传抄
 *
 *作者:李国辉
 *日期:2010-7-2 上午11:12:46
 */
package com.allinpay.xmltrans.tools;

import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.sql.Timestamp;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FuncUtil {
	private static DecimalFormat df=new DecimalFormat("#0.00"); 
	private static Cipher encrypt_cipher; //加密cipher
	private static Cipher decrypt_cipher; //解密cipher
	public static String[] split(String line,char split_char) {
		List<String> segments = new ArrayList<String>();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == split_char) {
				segments.add(dropNull(sb.toString()));
				sb = new StringBuffer();
			} else {
				sb.append(c);
			}
		}
		segments.add(sb.toString());

		return (String[]) segments.toArray(new String[0]);
	}
	
	public static String dropNull(String input) {
		if (input == null) {
			input = "";
		}
		return input.trim();
	}
	
	public static String formatTime(Date date,String formatter){
		SimpleDateFormat sdf  = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}
	
	public static String formatTime(Timestamp date,String formatter){
		if(date==null)
	        return "";
		SimpleDateFormat sdf  = new SimpleDateFormat(formatter);
		Date date_new = new Date(date.getTime());
		return sdf.format(date_new);
	}
	
	
	public static Timestamp parseStampTime1(String datestr){
		if(isEmpty(datestr))
			return null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return new Timestamp(sdf.parse(datestr).getTime());
		}
		catch(Exception ex){
			return null;
		}
	}
	
	public static Timestamp getCurrTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	
	public static String getDayString(int amount) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, amount);
		return formatTime(now.getTime(), "yyyy-MM-dd");
	}
	
	public static String getDayString(int amount,String format) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, amount);
		return formatTime(now.getTime(), format);
	}
	
	
	
	/**
	 * 通过户名识别账户属性
	 * @param name
	 * @param def
	 * @return String
	 * @throws
	 */
	public static String getAccountpropByName(String name,String def){
		if(name==null) return def;
		Pattern pt=Pattern.compile("^[^0]*[0]*[公司|学校|政府|商店|医院]+[^0]*[0]*$");
		Matcher mt = pt.matcher(name);
		if(mt.matches()||name.length()>=10) return "1";
		else return def;
	}
	
	

	
	
	public static boolean isNum(String str) {
		boolean flag = false;
		Pattern pat = Pattern.compile("^[0-9]*$");
		Matcher mat = null;
		mat = pat.matcher(str);
		flag = mat.matches();
		return flag;
	}
	
	/**
	 * 判断是否为数字，包括小数
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		if(isEmpty(str))
			return false;
		boolean flag = false;
		Pattern pat = Pattern.compile("^[0-9|.]+$");
		Matcher mat = null;
		mat = pat.matcher(str);
		flag = mat.matches();
		return flag;
	}
	
	/**
	 * 判断是否为数字，包括小数 含负数
	 * @param str
	 * @return
	 */
	public static boolean isNumberEx(String str){
		if(isEmpty(str))
			return false;
		boolean flag = false;
		Pattern pat = Pattern.compile("^-?[0-9|.]+$");
		Matcher mat = null;
		mat = pat.matcher(str);
		flag = mat.matches();
		return flag;
	}
	
	public static BigDecimal ftoy(BigDecimal num){
		return num.divide(new BigDecimal(100), 2, BigDecimal.ROUND_CEILING);
	}
	
	public static BigDecimal ftoy(long num){
		return ftoy(new BigDecimal(num));
	}
	
	public static BigDecimal ftoy(String num){
		return ftoy(new BigDecimal(num));
	}
	
	public static BigDecimal ytof(String y){
		return new BigDecimal(Math.round(new BigDecimal(y).multiply(new BigDecimal(100)).doubleValue()));
//		if(y.charAt(0) == '.') {
//			y = '0' + y;
//		}
//		String[] ps = y.split("\\.");
//		long ttp = Long.parseLong(ps[0]);
//		long r = ttp*100;
//		if (ps.length == 2){
//			if (ps[1].length() == 1)
//				r += Long.parseLong(ps[1])*10;
//			else if (ps[1].length() == 2)
//				r += Long.parseLong(ps[1]);
//			else {
//				r += Long.parseLong(ps[1].substring(0, 2));
//				if (ps[1].charAt(2) > '4')
//					r++;
//			}
//		}
//		return new BigDecimal(r);
	}	
	
	public static BigDecimal ytof(double y){
		return ytof(String.valueOf(y));
	}
	
	public static BigDecimal ytof(BigDecimal y){
		return ytof(y.toString());
	}
	
	//Map排序
	public static SortedMap<String, String> mapSortByKey(Map<String, String> unsort_map){
		TreeMap<String, String> result = new TreeMap<String, String>();
		Object[] unsort_key = unsort_map.keySet().toArray();
		Arrays.sort(unsort_key);
		for (int i = 0; i < unsort_key.length; i++) {
			result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
		}
		return result.tailMap(result.firstKey());
	}
	
	
	
	
	
	/**
	 * 构建in字符串
	 * @param str
	 * @return
	 */
	public static String buildInStr(Object[] str){
		if(str==null||str.length==0)
			return null;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length;i++){
			if(str[i]==null||str[i].toString().trim().length()==0)
				continue;
			sb.append("'");
			sb.append(str[i]);
			sb.append("',");
		}
		String tmp = sb.toString();
		if(tmp!=null&&tmp.length()>1)
			return tmp.substring(0,tmp.length()-1);
		else
			return null;
	}
	
	
	/**
	 * 构建in字符串
	 * @param str
	 * @return
	 */
	public static String buildInStrNoDH(Object[] str){
		if(str==null||str.length==0)
			return null;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length;i++){
			if(str[i]==null||str[i].toString().trim().length()==0)
				continue;
			sb.append(str[i]);
			sb.append(",");
		}
		String tmp = sb.toString();
		if(tmp!=null&&tmp.length()>1)
			return tmp.substring(0,tmp.length()-1);
		else
			return null;
	}	
	
	/**
	 * 构建in字符串
	 * @param str
	 * @param spliter
	 * @return
	 */
	public static String buildInStr(String str,String spliter){
		if(str==null||str.trim().length()==0)
			return null;
		return FuncUtil.buildInStr(str.split(spliter));
	}
	
	/**
	 * 构建in字符串
	 * @param str
	 * @return
	 */
	public static String buildInStr(List list){
		if(list==null||list.size()==0)
			return null;
		return FuncUtil.buildInStr(list.toArray());
	}
	
	public static String buildInStr(Set set){
		if(set==null||set.size()==0)
			return null;
		return FuncUtil.buildInStr(set.toArray());
	}
	
	/**
	 * 构建in字符串
	 * @param str
	 * @param spliter
	 * @return
	 */
	public static String buildInStr(String str,char spliter){
		if(str==null||str.trim().length()==0)
			return null;
		return FuncUtil.buildInStr(str.split(String.valueOf(spliter)));
	}
	
	/**
	 * 获取时间偏移后时间量
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date getTimeOffset(Date date,long offset){
		if(date==null)
			return null;
		return new Date(date.getTime()+offset);
	}
	
	public static boolean isEmpty(String s){
		if(s==null||"".equals(s.trim()))
			return true;
		return false;
	}
	
	/**
	 * 是否为空数组
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(Object[] val) {
		if (val == null || val.length == 0) {
			return true;
		}
		for (int i = 0; i < val.length; i++) {
			if (val[i] != null && !"".equals(val[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查手机
	 * @param tel
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if (isEmpty(mobile)) {
			return true;
		}
		Pattern pattern = Pattern.compile("^(1[3|4|5|7|8|9])\\d{9}$");
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}

	public static boolean checkStr(String org,String reg){
		String regular="([0-9]{10}|[0-9]{15})[_]([S|F|J])02[0-9]{8}[_][_|\\-|@|#|A-Z|a-z|0-9|.|\\[\\]\\(\\)]*";//标准文件名
		if(reg!=null&&!"".equals(reg))
			regular=reg;
		Pattern emailer = Pattern.compile(regular); 
		if(emailer.matcher(org).matches()) 
			return true;
		else
			return false;
	}
	/**
	 * 四位随机码
	 * @return
	 */
	public static String getValidatecode(int n) {
		Random random = new Random();
		String sRand = "";
		n = n == 0 ? 4 : n;// default 4
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}
	
	/**
	 * 根据路径创建一系列的目录
	 * @param path
	 */
	public static void mkDir(String path) {
		File file;
		try {
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
	}	
	
	/**
	 * 文件名过滤特殊字符
	 * @param str
	 * @return
	 */
	public  static String SpecStrFilter(String str){
		String regEx="[*:\\/?<>|\"]";   
		Pattern   p=Pattern.compile(regEx);      
		Matcher   m=p.matcher(str);      
		return   m.replaceAll("_").trim();
	}
	
	public static boolean removeFile(String path){
		return removeFile(new File(path));
	}

	/**
	 * 删除文件
	 * @param path
	 * @return
	 */
	public static boolean removeFile(File path){
		boolean result = false;
		System.out.println("删除文件:"+path.getPath());
		if (path.isDirectory()){
			File[] child = path.listFiles();
			if (child != null && child.length != 0){
				for (int i = 0; i < child.length; i++){
					if(removeFile(child[i])==false){
						System.out.println("删除文件:"+child[i]+" 失败");
						return false;
					}
				    child[i].delete();
				 }
			}
		}
		path.delete();
		result = true;
		return result;
	}
	
	// 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

	
	/**
	 * 格式化数字0.00
	 * @param number
	 * @return
	 */
	public static String formatNumber(BigDecimal number){
		return formatNumber(number.doubleValue());
	}
	
	/**
	 * 格式化数字0.00
	 * @param number
	 * @return
	 */
	public static String formatNumber(double number){
		DecimalFormat df1=(DecimalFormat) DecimalFormat.getInstance();
		df1.applyPattern("0.00");
		return df1.format(number);
	}
	
	/**
	 * 格式化数字，默认是#,##0.00
	 * @param number
	 * @return
	 */
	public static String formatNumber1(BigDecimal number,String pattern){
		return formatNumber1(number.doubleValue(),pattern);
	}
	
	/**
	 * 格式化数字，默认是#,##0.00
	 * @param number
	 * @return
	 */
	public static String formatNumber1(double number,String pattern){
		DecimalFormat df1=(DecimalFormat) DecimalFormat.getInstance();
		if(FuncUtil.isEmpty(pattern)){
			pattern = "#,##0.00";
		}
		df1.applyPattern(pattern);
		return df1.format(number);
	}
	
	public static BigDecimal parseString(String source,String pattern) throws ParseException{
		DecimalFormat df1=(DecimalFormat) DecimalFormat.getInstance();
		if(FuncUtil.isEmpty(pattern)){
			pattern = "#,##0.00";
		}
		df1.applyPattern(pattern);
		return new BigDecimal(df1.parse(source).doubleValue());
	}
	
	
	 /** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }  
	
	/**
	 * 屏蔽账号 开头保留多少位 末尾保留多少位
	 * @return
	 */
	public static String shieldAccount(int head,int end,boolean isshield,String account){
		if(account == null||"".equals(account)){
			return "";
		}
		int total = head+end;
		if(account.length()<=total){
			if(isshield){//当位数不够的时候，是否屏蔽所有的
				int length = account.length();
				StringBuilder sb = new StringBuilder();
				while(length>0){
					sb.append("*");
					length--;
				}
				return sb.toString();
			}else{
				return account;
			}
		}
//		int sAccount = account.length()-total;//屏蔽成*的个数
		int sAccount = 4;
		StringBuilder sb = new StringBuilder();
		while(sAccount>0){
			sb.append("*");
			sAccount--;
		}
		return account.substring(0,head)+sb.toString()+account.substring(account.length()-end);
	}
	
	/***
	 * 检查身份证
	 * */
	public static boolean checkIdcard(String idcard){
		boolean valid = false;
		if(idcard!=null && !"".equals(idcard.trim())){
			if(idcard.length()==18){
				valid = isNum(idcard.substring(0, idcard.length()-1));
				if(valid){
					valid = isNum(idcard.substring(idcard.length()-1));
					if(!valid){
						if(idcard.endsWith("x") || idcard.endsWith("X")){
							valid = true;
						}
					}
				}
			}else if(idcard.length()==15){
				valid = isNum(idcard);
			}
		}
		return valid;
	}
	
	public static String join(String[] array){
		if(array == null ||array.length==0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(String s: array){
			if(sb.length()>0){
				sb.append(",");
			}
			sb.append(s);
		}
		return sb.toString();
	}
	
	
	
	
	/**
	 * 判断汉字
	 * comment here
	 * @param strVal
	 * @return
	 */
	public static boolean isChinese(String strVal) {
		int iRnt = 0;
		boolean rs=false;
		for (int i=0; i<strVal.length(); i++) {			
			String strHanzi = strVal.substring(i, i+1);
			byte[] bytes = null;
			try {
				bytes = (strHanzi).getBytes("gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}
			if (bytes == null || bytes.length > 2 || bytes.length <= 0
					|| bytes.length == 1) {
				//非汉字
				iRnt++;
			}
			if (bytes.length == 2) { // 汉字
				return true;
			}
		}//for
		rs=false;
		return rs;
	}
	
	public static boolean isUseSign(String tp){
		if(tp==null||tp.trim().equals("1")||tp.trim().equals("0"))
			return false;
		else
			return true;
	}
	
	public static int bgCompare(BigDecimal val1,BigDecimal val2){
		return val1.compareTo(val2);
	}
	
	public static byte[] getBytesFromIS(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b = 0;
		// BufferedReader br = new BufferedReader(new InputStreamReader(input,
		//		"GBK"));
		while ((b = is.read()) != -1)
			baos.write(b);

		return baos.toByteArray();
	}
	
	/**
	 * 删除文件夹及下所有文件
	 * @param dir
	 * @return
	 */
	public static boolean deleteFolder(File dir) {
		File filelist[] = dir.listFiles();
		int listlen = filelist.length;
		for (int i = 0; i < listlen; i++) {
			if (filelist[i].isDirectory()) {
				deleteFolder(filelist[i]);
			} else {
				if (!filelist[i].delete())
					return false;
			}
		}
		if (!dir.delete())
			return false;// 删除当前目录
		else
			return true;
	}
	
	/**
	 * 四舍五入处理
	 * @param pos 保留小数位
	 * @param bd
	 * @return BigDecimal
	 */
	public static BigDecimal setScale(int pos,BigDecimal bd){
		if(bd==null)return null;
		return bd.setScale(pos, BigDecimal.ROUND_HALF_UP);
	}
	
	
	
	/**
	 * 格式化金额
	 */
	public static String fmtMoney(Object obj)
	{
		try
		{
			Double d=Double.valueOf(obj!=null ? obj.toString() : "0.00");
			return df.format(d);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return df.format(0.00);
		}
	}
	

	
	
	
	
	
	
	
	/**
	 * comment 产生记录序列号
	 * @param num
	 * @return
	 */
	public static String getRecordSeq(int num){
		int tmp=1000000+num;
		String str=String.valueOf(tmp);
		return str.substring(1,str.length());
	}
	
	
	public static BigDecimal toDecimal(String str, String divide) {
		if (str == null || str.trim().length()==0) {
			str = "0";
		}
		BigDecimal bdRnt = new BigDecimal(str).divide(new BigDecimal(divide));
		return bdRnt;
	}
	
	public static BigDecimal toDecimalMut(String str, String muti) {
		if (str == null || str.trim().length()==0) {
			str = "0";
		}
		BigDecimal bdRnt = new BigDecimal(str).multiply(new BigDecimal(muti));
		return bdRnt;
	}	
	//百分格式化
	public static String Round(BigDecimal num)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		String str = df.format(num);
		return str;
	}

	public static String RoundAndScale(BigDecimal num)
	{
		num = num.divide(new BigDecimal("100.00"));
		DecimalFormat df = new DecimalFormat("0.00");
		String str = df.format(num);
		return str;
	}

	public static String RoundAndScalePer(BigDecimal num)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		String str = df.format(num.multiply(new BigDecimal("100.00")));
		return str;
	}
	/**
	 * 获得文件SHA1摘要信息
	 * @param file
	 * @return String
	 * @author l_ghui
	 * @date 2009-10-12
	 */
	public static String getSha1Str(File file){
		String str="";
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[2048];
			int c;
			while ((c = fis.read(buf)) > 0){
				baos.write(buf, 0, c);
			}
			str=new BASE64Encoder().encode(md.digest(baos.toByteArray()));
			fis.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return str;
	}
	
	public static String getSha1Str(String key){
		String str="";
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			str=new BASE64Encoder().encode(md.digest(key.getBytes()));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return str;
	}
	
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i]<0 ? src[i] + 256 : src[i];
            String hv = Integer.toHexString(v);

            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
   
  
   

    
    private static Field[] getAllDeclaredFields(Class c) {

        if (c.equals(Object.class)) {
            return null;
        }

        Field[] parentFields = getAllDeclaredFields(c.getSuperclass());
        Field[] thisFields = c.getDeclaredFields();
        
        if ((parentFields != null) && (parentFields.length > 0)) {
        	Field[] allFields =
                new Field[parentFields.length + thisFields.length];
            System.arraycopy(parentFields, 0, allFields, 0,
            				parentFields.length);
            System.arraycopy(thisFields, 0, allFields, parentFields.length,
            				thisFields.length);

            thisFields = allFields;
        }

        return thisFields;
    }

   
 

    /**
     * 截取字符串字节数，中文向前截取
     * @param str
     * @param subSLength
     * @return
     */
    public static String subStr(String str, int subSLength){    
		if (str == null) 
		    return "";
		else{
			try{
				byte[] strbyte = str.getBytes("GBK");
				int subStrByetsL = strbyte.length;//截取子串的字节长度 
				if(subStrByetsL<=subSLength){
					return str;
				}
			    String subStr = new String(strbyte,0,subSLength+1,"GBK");
			    subStr = subStr.substring(0,subStr.length()-1);
			    return subStr;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}   
	}  
    
    public static int chineseCompare(Object _oChinese1, Object _oChinese2) {
        return Collator.getInstance(Locale.CHINESE).compare(_oChinese1,
                _oChinese2);
    }
    
    
	/**
	* 解密并解压
	* @param ins
	* @param key base64编码后
	* @return
	 * @throws Exception 
	*/
   public static InputStream decStream(InputStream ins,String key) throws Exception{
	   byte[] inputbyte =IOUtils.toByteArray(ins);
//	   System.out.println("密文长度："+inputbyte.length);
		//解压
	   byte[]  decbyte =decByte(inputbyte,key);
	   byte[] output =decompress(decbyte);
	   return  new ByteArrayInputStream(output);
   }
 
   public static byte[] decByte(byte[] bs,String key) throws UnsupportedEncodingException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
	   InputStream inNew = null ;
	   Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	   //获得密钥
	   //String  strKey=key;  //base64格式密钥(16字节，即128位)
	   BASE64Decoder  debase64=new BASE64Decoder();
	   String  decodeKey=new String((debase64.decodeBuffer(key)),"UTF-8");
	   byte[]  rawkey=decodeKey.getBytes();
	   SecretKeySpec skeySpec=new SecretKeySpec(rawkey, "DESede");
	   decrypt_cipher=Cipher.getInstance( "DESede/ECB/PKCS7Padding");
	   decrypt_cipher.init(Cipher.DECRYPT_MODE,skeySpec);
	   System.out.println("开始解密.................");
	   // 一次读多个字节
	   byte[] tempbytes =bs;
	   int byteread = 0;
	   // 读入多个字节到字节数组中，byteread为一次读入的字节数
		byte[] dc = decryptDESede(tempbytes,tempbytes.length);
	    return dc;
   }
   
   /**
	* 压缩然后加密
	* @param ins
	* @param key base64 编码后
	* @return
 * @throws Exception 
	*/
   public static byte[]  encToStream(String ins,String key) throws Exception{
//	   System.out.println("加密前内容：\n"+ins);
	   byte[] input = ins.getBytes();
	   byte[] data = compress(input);
      return encByte(data,key);
   }
  
   public static  byte[]  encByte(byte[] tempbytes,String key ) throws UnsupportedEncodingException{
	   System.out.println("开始加密.................");
	   Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	   //获得密钥
	 // String  strKey=this.getMerKey();  //base64格式密钥(16字节，即128位)
	  BASE64Decoder  debase64=new BASE64Decoder();
	  String decodeKey;
   try {
	   	  decodeKey = new String((debase64.decodeBuffer(key)),"UTF-8");
		  byte[]  rawkey=decodeKey.getBytes();
		  SecretKeySpec skeySpec=new SecretKeySpec(rawkey, "DESede");
		  encrypt_cipher=Cipher.getInstance( "DESede/ECB/PKCS7Padding");
		  encrypt_cipher.init(Cipher.ENCRYPT_MODE,skeySpec);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
		 // 读入多个字节到字节数组中，byteread为一次读入的字节数
	   byte[] bb = encryptDESede(tempbytes,tempbytes.length);
//	   System.out.println("加密后长度： "+bb.length) ;
	   return bb;
   }
   
   /**
   * 数据压缩
   *
   * @param is
   * @param os
   * @throws Exception
   */
  public static void compress(InputStream is, OutputStream os,int bufferSize)throws Exception {
   
	   GZIPOutputStream gos = new GZIPOutputStream(os);
	   int count;
	   byte[]  data= new byte[bufferSize];
	   while ((count = is.read(data, 0, bufferSize)) != -1) {
		   gos.write(data, 0, count);
	   }
	   gos.finish();
	   gos.flush();
	   gos.close();
   }
  /**
  * 数据压缩
  *
  * @param data
  * @return
  * @throws Exception
  */
 public static byte[] compress(byte[] data) throws Exception {
	 ByteArrayInputStream bais = new ByteArrayInputStream(data);
	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 // 压缩
	 compress(bais, baos,data.length);
	 byte[] output = baos.toByteArray();
	 baos.flush();
	 baos.close();
	 bais.close();
	 return output;
 }
	/**
	  * 数据解压缩
	  *
	  * @param is
	  * @param os
	  * @throws Exception
	  */
	 public static void decompress(InputStream is, OutputStream os,int bufferSize)
			 throws Exception {

		  GZIPInputStream gis = new GZIPInputStream(is);

		  int count=0;
		  byte[] data = new byte[bufferSize];
		  while ((count = gis.read(data, 0, bufferSize)) != -1) {
			  os.write(data, 0, count);
		  }

		  gis.close();
	 }
   
	 /**
	 * 3des加密函数
	 * @param encodeStr  等加密字符串(8字节倍数)
	 * @return
	 */
	public static byte[] encryptDESede(byte[] encodeBy,int len){
	   try{
			byte[] cipherByte=encrypt_cipher.doFinal(encodeBy,0,len);
			return cipherByte;
		  }catch(Exception   e3)   {
			  e3.printStackTrace();
		 }
	   return   null;
   }
	
	/**
	 * 3des解密函数
	 * @param encodeStr  等解密字符串(8字节倍数)
	 * @return
	 */
	public static byte[] decryptDESede(byte[] decodeStr,int len){
	   try{
			return decrypt_cipher.doFinal(decodeStr,0,len);
		  } catch(Exception e3)   {
			  e3.printStackTrace();
		 }
	   return   null;
   }
	
	/**
	* 数据解压缩
	*
	* @param data
	* @return
	* @throws Exception
	*/
   public static byte[] decompress(byte[] data) throws Exception {
	   ByteArrayInputStream bais = new ByteArrayInputStream(data);
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   // 解压缩
	   decompress(bais, baos,data.length);
	   data = baos.toByteArray();
	   baos.flush();
	   baos.close();
	   bais.close();
	   return data;
   }
 
   
 	/**
	 * Function: 将byte数组转换为16进制值的字符串
	 * 
	 * @param byteAry
	 *            需要转换的byte数组
	 * @return 转换后的16进制值的字符串
	 * @throws Exception
	 *             抛出全部异常
	 */
	public static String binStrAry2HexStr(byte[] byteAry) throws Exception {
		int len = byteAry.length;

		// 每个字符占2位，所以字符串的长度是字节数据组长度的两倍
		StringBuffer sb = new StringBuffer(len * 2);
		for (int i = 0; i < len; i++) {
			if ((byteAry[i] & 0xFF) < 0x10) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(byteAry[i] & 0xFF));
		}
		return sb.toString();
	}

	/**
	 * Function: 将16进制值的字符串转换为byte数组
	 * 
	 * @param hexStr
	 *            需要转换的16进制值的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             抛出全部异常com.allinpay.aipg.gvycvt
	 */
	public static byte[] hexStr2BinStrAry(byte[] hexStrAry) throws Exception {
		int len = hexStrAry.length;

		// 每个字符占2位，所以字节数据组的长度是字符串的长度的二分之一
		byte[] byteResult = new byte[len / 2];
		for (int i = 0; i < len; i = i + 2) {
			String strTmp = new String(hexStrAry, i, 2);
			byteResult[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return byteResult;
	}

	/**
	 * Function: 将16进制值的字符串转换为byte数组
	 * 
	 * @param hexStr
	 *            需要转换的16进制值的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             抛出全部异常
	 */
	public static byte[] hexStr2BinStrAry(String hexStr) throws Exception {
		return hexStr2BinStrAry(hexStr.getBytes());
	}
    
	
	
	public static String getRightStr(String str){
	
		if(str.indexOf(".")>0){
			return str.split("\\.")[0];
		}
		return str;
	}
	
	
	
	public static long betweenDay(Date beginDate,Date enddate){
				
		return ((enddate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
		
	}
	
	
	
	
}
