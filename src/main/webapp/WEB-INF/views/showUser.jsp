
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TLTTestReportCreater</title>
    <link rel="icon" href="picture.ico" type="image/x-icon"/>
    <script type="text/javascript" src="/js/jQuery%20v3.3.1.js"></script>
    <script type="text/javascript">
        //新增某商户号的测试案例
        function addData(){
            console.log('新增测试案例');
            var datain = {
                cusi:$("#merchantId").val()
            };
            $.ajax({
                cache:true,//保留缓存数据
                type:"POST",//为post请求
                url:"addData",//这是我在后台接受数据的文件名
                data:datain,
                async:true,//设置成true，这标志着在请求开始后，其他代码依然能够执行。如果把这个选项设置成false，这意味着所有的请求都不再是异步的了，这也会导致浏览器被锁死
                error:function(request){//请求失败之后的操作
                    return;
                },
                success:function(data){//请求成功之后的操作
                    console.log(data);
                }
            });
        }
    </script>
    <style type="text/css">
        /* CSS Document */
        body {
            font: normal 11px auto;
            color: #4f6b72;
            background: #E6EAE9;
        }
        div{
            padding: 0px 0px 0px 30px;
            float:left}
        a {
            color: #c75f3e;
        }
        #mytable {
            width: 700px;
            padding: 0;
            margin: 0;
        }
        #tableTop {
            width: 1400px;
            padding: 0;
            margin: 0;
        }
        #xmltable {
            width: 600px;
            padding: 0;
            margin: 0;
        }
        textarea {
            width: 290px;
            height: 500px;
            //readonly:readonly;
        }
        th {
            font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
            color: #4f6b72;
            border-right: 1px solid #C1DAD7;
            border-bottom: 1px solid #C1DAD7;
            border-top: 1px solid #C1DAD7;
            letter-spacing: 2px;
            text-transform: uppercase;
            text-align: left;
            padding: 6px 6px 6px 12px;
            background: #CAE8EA  no-repeat;
        }

        th.nobg {
            border-top: 0;
            border-left: 0;
            border-right: 1px solid #C1DAD7;
            background: #fff;
        }

        td {
            border-right: 1px solid #C1DAD7;
            border-bottom: 1px solid #C1DAD7;
            background: #fff;
            font-size:11px;
            padding: 6px 6px 6px 12px;
            color: #4f6b72;
        }

        td.alt {
            background: #F5FAFA;
            color: #797268;
        }
        td.xmlshow {
            height: 505px;
        }
        input {
            background: #fff;
            border-right: 1px solid #C1DAD7;
            border-bottom: 1px solid #C1DAD7;
            border-top: 1px solid #C1DAD7;
            border-left: 1px solid #C1DAD7;
        }
        button {
            background: #F5FAFA;
        }
        html>body td{ font-size:11px;}
    </style>

</head>
<body>
<h2>商户/交易参数，测试商户号必填！！！其余选填（系统内部除测试商户号其余均有默认值设有默认值）</h2>
<table id="tableTop" cellspacing="0" summary="The technical specifications of the Apple PowerMac G5 series">
    <tr>
        <th scope="col" abbr="Configurations" class="spec">商户参数</th>
        <td class="alt"><input  id="merchantId" placeholder="商户号" ></td>
        <td class="alt"><input  id="acctName" placeholder="账户名(test)" ></td>
        <td class="alt"><input  id="amount" placeholder="交易金额(>=100000)" ></td>
        <td class="alt"><input  id="sum" placeholder="交易总金额(>=200000)" ></td>
        <td class="alt"><input  id="pfxPassword" placeholder="pfxPassword(111111)" ></td>
        <td class="alt"><input  id="tel" placeholder="电话(11位)" ></td>
        <td class="alt"><input  placeholder="备注(忽略即可)" ></td>
    </tr>
</table>
<h3>直接点击错误码即可配置帮助中心交易结果，无需单独配置</h3>
<div>
<table id="mytable" cellspacing="0" summary="The technical specifications of the Apple PowerMac G5 series">
    <tr>
        <th scope="col" abbr="Configurations" class="spec">接口测试</th>
        <th scope="col" abbr="Dual 1.8">测试卡号/其他</th>
        <th scope="col" abbr="Dual 2">更新流水号</th>
        <th scope="col" abbr="Dual 2.5">传错误码 + 执行(轮询)</th>
    </tr>
    <tr>
        <th scope="row" abbr="Model" class="spec">P12UpLoad</th>
        <td align="center"><button >选择P12文件</button></td>
        <td>...</td>
        <td align="center"><button>Upload</button></td>
    </tr>
    <tr>
        <th scope="row" abbr="G5 Processor" class="specalt">新增测试案例</th>
        <td class="alt">62030249345809341</td>
        <td class="alt" align="center"><button  onclick="addData()">新增案例</button></td>
        <td class="alt"><span>显示流水号</span></td>
    </tr>
    <tr>
        <th scope="row" abbr="Frontside bus" class="spec">TLT01交易成功</th>
        <td>62030249345809341</td>
        <td align="center"><input  id="lsh0000" placeholder="填写流水号" ></td>
        <td align="center"><button>0000</button>&nbsp;<button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="L2 Cache" class="specalt">TLT02余额不足</th>
        <td class="alt">62030249345809341</td>
        <td class="alt" align="center"><input  id="lsh0000" placeholder="填写流水号" ></td>
        <td class="alt" align="center"><button>3008</button>&nbsp;<button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="Frontside bus" class="spec">TLT03连接关闭</th>
        <td>6258101645858218</td>
        <td align="center">--------------------------------------</td>
        <td align="center"><button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="L2 Cache" class="specalt">TLT04 503</th>
        <td class="alt">6258101645858217</td>
        <td class="alt" align="center">--------------------------------------</td>
        <td class="alt" align="center"><button>==></button></td>
    </tr>   <tr>
        <th scope="row" abbr="Frontside bus" class="spec">TLT05 500</th>
        <td>6258101645858219</td>
        <td align="center">--------------------------------------</td>
        <td align="center"><button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="L2 Cache" class="specalt">TLT06超时等待</th>
        <td class="alt">6258101645858216</td>
        <td align="center">--------------------------------------</td>
        <td class="alt" align="center"><button>==></button></td>
    </tr>   <tr>
        <th scope="row" abbr="Frontside bus" class="spec">TLT07交易处理中</th>
        <td>6258101645858215</td>
        <td align="center">--------------------------------------</td>
        <td align="center"><button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="L2 Cache" class="specalt">TLT08账户状态错误</th>
        <td class="alt">6231132201000397000</td>
        <td class="alt" align="center"><input  id="lsh0000" placeholder="填写流水号" ></td>
        <td class="alt" align="center"><button>3057</button>&nbsp;<button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="Frontside bus" class="spec">TLT09原始交易不成功</th>
        <td>6217002920108608762</td>
        <td align="center"><input  id="lsh0000" placeholder="填写流水号" ></td>
        <td align="center"><button>3027</button>&nbsp;<button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="L2 Cache" class="specalt">TLT10外部系统错误</th>
        <td class="alt">6258101645858210</td>
        <td class="alt" align="center"><input  id="lsh0000" placeholder="填写流水号" ></td>
        <td class="alt" align="center"><button>3071</button>&nbsp;<button>==></button></td>
    </tr>
    <tr>
        <th scope="row" abbr="Frontside bus" class="spec">TLT11中间状态</th>
        <td>6203024934580934</td>
        <td align="center">--------------------------------------</td>
        <td align="center"><button>==></button></td>
    </tr>
</table>
</div>
<div><table id="xmltable" cellspacing="0" summary="The technical specifications of the Apple PowerMac G5 series">
    <tr>
        <th scope="col" abbr="Configurations" class="spec">请求报文</th>
        <th scope="col" abbr="Dual 1.8">响应报文</th>
    </tr>
    <tr>
        <td align="center" class="xmlshow"><textarea readonly="readonly" id="sendXML">1234567</textarea></td>
        <td align="center" class="xmlshow"><textarea readonly="readonly" id="backXML"></textarea></td>
    </tr>
</table>
</div>
${memberInfo.username}
</body>
</html>