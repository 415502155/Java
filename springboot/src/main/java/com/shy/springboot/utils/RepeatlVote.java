package com.shy.springboot.utils;

public class RepeatlVote implements Vote {

	@Override
	public void handle(String user, String voteItem, VoteManager voteManager) {
		//voteManager.getMapVote().put(user, voteItem);
        System.out.println(user + " : 您已重复投票！， 请停止投票操作；");
	}

}
