package com.shy.springboot.utils;

public class NormalVote implements Vote {

	@Override
	public void handle(String user, String voteItem, VoteManager voteManager) {
		//voteManager.getMapVote().put(user, voteItem);
        System.out.println(user + "： 恭喜您投票“"+ voteItem +"”成功!");
	}

}
