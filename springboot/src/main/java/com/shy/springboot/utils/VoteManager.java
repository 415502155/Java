package com.shy.springboot.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.alibaba.fastjson.JSONObject;

public class VoteManager {
	//持有状体处理对象
    private Vote vote = null;
    //记录用户投票的结果，Map<String,String>对应Map<用户名称，投票的选项>
    private Map<String,String> mapVote = new HashMap<String,String>();
    //记录用户投票次数，Map<String,Integer>对应Map<用户名称+投票选项，投票的次数>
    private Map<String,Integer> mapVoteCount = new HashMap<String,Integer>();
    /**
     * 获取用户投票结果的Map
     */
    public Map<String, String> getMapVote() {
        return mapVote;
    }
    /**
     * 投票
     * @param user    投票人
     * @param voteItem    投票的选项
     */
    public void vote(String user,String voteItem){
        //1.为该用户增加投票次数
        //从记录中取出该用户已有的投票次数
        Integer oldVoteCount = mapVoteCount.get(user+"_"+voteItem);
        if(oldVoteCount == null){
            oldVoteCount = 0;
        }
        oldVoteCount += 1;
        mapVoteCount.put(user+"_"+voteItem, oldVoteCount);
        //2.判断该用户的投票类型，就相当于判断对应的状态
        //到底是正常投票、重复投票、恶意投票还是上黑名单的状态
        System.out.println(oldVoteCount);
        if(oldVoteCount == 1){
        	vote = new NormalVote();
        }
        else if(oldVoteCount > 1 && oldVoteCount < 5){
        	vote = new RepeatlVote();
        	oldVoteCount = 1;
        	mapVoteCount.put(user+"_"+voteItem, oldVoteCount);
        	System.out.println("您已重复投票，不会再次增加票数！");
        }
        /*else if(oldVoteCount >= 5 && oldVoteCount <8){
        	vote = new SpiteVoteState();
        }
        else if(oldVoteCount > 8){
        	vote = new BlackVoteState();
        }*/
        //然后转调状态对象来进行相应的操作
        vote.handle(user, voteItem, this);

        /***
         * 统计每个事项的人数
         */
        Map<String, Integer> userMap = new HashMap<String, Integer>();
        /***
         * 每个事项的票数
         */
        Map<String, Integer> itemMap = new HashMap<String, Integer>();
        
        Set<String> keySet = mapVoteCount.keySet();
        for (String key : keySet) {
			//Integer value = mapVoteCount.get(key);
			String[] split = key.split("_");
			String username = split[0];
			String item = split[1];
			
			if (userMap.containsKey(username)) {
				userMap.put(username, userMap.get(username) + 1);
			} else {
				userMap.put(username, 1);
			}
			
			if (itemMap.containsKey(item)) {
				itemMap.put(item, itemMap.get(item) + 1);
			} else {
				itemMap.put(item, 1);
			}
		}
        System.out.println("每个人投的票数 ：" + JSONObject.toJSONString(userMap));
       
        System.out.println("每个事项的票数 ：" + JSONObject.toJSONString(itemMap));
    }
}
