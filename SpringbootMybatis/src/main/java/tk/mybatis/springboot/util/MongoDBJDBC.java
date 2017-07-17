package tk.mybatis.springboot.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBJDBC {
	public static void main( String args[] ){
	      try{   
	       // 连接到 mongodb 服务
	         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	       
	         // 连接到数据库
	         MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");  
	       System.out.println("Connect to database successfully");
	      
	       MongoCollection<Document> collection = mongoDatabase.getCollection("test");
	       System.out.println("集合 test 选择成功");
	       Document document = null;
	       //insert
//	       document = new Document("empno", "10086").  
//	         append("description", "i am is a admin").  
//	         append("sex", "ad").  
//	         append("address","shanghai").
//	         append("age", 28);  
//	         List<Document> documents = new ArrayList<Document>();  
//	         documents.add(document);  
//	         collection.insertMany(documents);  
//	         System.out.println("文档插入成功");  
	     //检索所有文档  
	         /** 
	         * 1. 获取迭代器FindIterable<Document> 
	         * 2. 获取游标MongoCursor<Document> 
	         * 3. 通过游标遍历检索出的文档集合 
	         * */  
//	         FindIterable<Document> findIterable = collection.find();  
//	         MongoCursor<Document> mongoCursor = findIterable.iterator();  
//	         while(mongoCursor.hasNext()){  
//	            System.out.println(mongoCursor.next());  
//	         } 
	         
	         FindIterable<Document> iter = collection.find(new Document("age",28));
	         FindIterable<Document> iter1 = collection.find();
	         iter1.forEach(new Block<Document>() {

				public void apply(Document arg0) {
					// TODO Auto-generated method stub
					System.out.println(arg0.toJson());
				}

        	});
	      }catch(Exception e){
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	     }
	   }
}
