package pachong.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class Test {
	private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	public static void main(String[] args) {
		/* 1 生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5000);

		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(
				"http://dotamax.com/player/detail/136384759/");
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}

			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
			for (Header h : headers)
				System.out.println(h.getName() + " " + h.getValue());
			// 读取 HTTP 响应内容，这里简单打印网页内容
			byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
			System.out.println(new String(responseBody));
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			InputStream response = getMethod.getResponseBodyAsStream();//
		} catch (HttpException ee) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			ee.printStackTrace();
		} catch (IOException eee) {
			// 发生网络异常
			eee.printStackTrace();
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		
		extractKeyWordText("http://dotamax.com/player/detail/136384759/", "/match/detail/");
	}
	
	public static void extractKeyWordText(String url, String keyword) {
		try {
            //生成一个解析器对象，用网页的 url 作为参数
			Parser parser = new Parser(url);
			//设置网页的编码,这里只是请求了一个 gb2312 编码网页
			parser.setEncoding("utf-8");
			//迭代所有节点, null 表示不使用 NodeFilter
			NodeList list = parser.parse(null);
            //从初始的节点列表跌倒所有的节点
			processNodeList(list, keyword);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	private static void processNodeList(NodeList list, String keyword) {
		//迭代开始
		SimpleNodeIterator iterator = list.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			//得到该节点的子节点列表
			NodeList childList = node.getChildren();
			//孩子节点为空，说明是值节点
			if (null == childList)
			{
				//得到值节点的值
				String result = node.toPlainTextString();
				//若包含关键字，则简单打印出来文本
				if (result.indexOf(keyword) != -1)
					System.out.println(result);
			} //end if
			//孩子节点不为空，继续迭代该孩子节点
			else 
			{
				processNodeList(childList, keyword);
			}//end else
		}//end wile
	}
}