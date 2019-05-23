package com.shy.springboot.utils;

public interface Vote {
	
	public void handle(String user,String voteItem,VoteManager voteManager);
}
