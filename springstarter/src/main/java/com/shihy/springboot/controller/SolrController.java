package com.shihy.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.shihy.springboot.utils.ReturnResult;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 
 * @data 2019年4月8日 下午4:16:37
 *
 */
@RestController
@RequestMapping(value = "/solr")
public class SolrController {

	@Autowired
	private SolrClient client;

	@RequestMapping(value = "/add1", method = RequestMethod.GET)
	public ReturnResult addSolr() throws SolrServerException, IOException {
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		String[] value  = { "通过spring boot构建solr查询实例 ", "SpringBoot整合Solr 入门", "Spring Boot整合solr,手把手教你使用solr",
				"springBoot整合solr", "spring boot和solr整合", "SpringBoot+solr配置入门 ",
				"SpringBoot整合Redis、ApachSolr和SpringSession的示例", "SpringBoot+solr配置入门", "测试1111", "测试22222" };
		for (int i = 0; i < 10; i++) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", "solr" + i);
			doc.setField("ik", value[i]);
			docs.add(doc);
		}
		client.add("new_core", docs);
		client.commit("new_core");
		return ReturnResult.success("");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ReturnResult getQueryResult() throws SolrServerException, IOException {
		SolrQuery query = new SolrQuery();
		query.setQuery("id:192*");
		//query.addField("*");
		// query.set("df", "ik");
		query.setHighlight(true);
		// 高亮显示的域
		query.addHighlightField("ik");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		// 第四步：执行查询。得到一个Response对象。
		QueryResponse response = client.query(query);
		System.out.println("response :" + response);
		// 第五步：取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());

		// 第六步：遍历结果并打印。
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("ik"));
			// 取高亮显示
			/*Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			Map<String, List<String>> map = highlighting.get(solrDocument.get("ik"));
			for (String key : map.keySet()) {
				System.out.println("key= " + key + " and value= " + map.get(key));
			}*/
		}
		return ReturnResult.success("");
	}
}
