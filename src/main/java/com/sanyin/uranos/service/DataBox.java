package com.sanyin.uranos.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

import com.sanyin.uranos.exception.DrawPrizeException;
import com.sanyin.uranos.exception.VoteException;

public class DataBox {
	private int minLuckyNumber;
	private int maxLuckyNumber;
	private List<Campaigner> campaigner;

	private int grandPrizeQuota;
	private int firstPrizeQuota;
	private int secondPrizeQuota;
	private int thirdPrizeQuota;

	private int voteState;

	private Cassette cassette;

	public synchronized void start(boolean force) {
		if (cassette == null || force) {
			cassette = new Cassette(minLuckyNumber, maxLuckyNumber);
		}
	}

	public synchronized void vote(String p1, String p2, String p3, String p4, String p5, int src) {
		Map<String, List<Integer>> p1TicketPool = new HashMap<>(cassette.getP1());
		Map<String, List<Integer>> p2TicketPool = new HashMap<>(cassette.getP2());
		Map<String, List<Integer>> p3TicketPool = new HashMap<>(cassette.getP3());
		Map<String, List<Integer>> p4TicketPool = new HashMap<>(cassette.getP4());
		Map<String, List<Integer>> p5TicketPool = new HashMap<>(cassette.getP5());
		vote(src, p1, p1TicketPool);
		vote(src, p2, p2TicketPool);
		vote(src, p3, p3TicketPool);
		vote(src, p4, p4TicketPool);
		vote(src, p5, p5TicketPool);

		cassette.setP1(p1TicketPool);
		cassette.setP2(p2TicketPool);
		cassette.setP3(p3TicketPool);
		cassette.setP4(p4TicketPool);
		cassette.setP5(p5TicketPool);
	}

	public void vote(int src, String target, Map<String, List<Integer>> ticketPool) {
		if (src < minLuckyNumber || src > maxLuckyNumber) {
			throw new VoteException("您无权投票");
		}
		List<Integer> voters = ticketPool.get(target);
		if (voters == null) {
			voters = new ArrayList<>();
			ticketPool.put(target, voters);
		}
		if (voters.contains(src)) {
			throw new VoteException("您已经投过票了");
		}
		voters.add(src);
	}

	public int drawGrandPrize() {
		return drawPrize(cassette.getGrandPrize(), grandPrizeQuota);
	}

	public int drawFirstPrize() {
		return drawPrize(cassette.getFirstPrize(), firstPrizeQuota);
	}

	public int drawSecondPrize() {
		return drawPrize(cassette.getSecondPrize(), secondPrizeQuota);
	}

	public int drawThirdPrize() {
		return drawPrize(cassette.getThirdPrize(), thirdPrizeQuota);
	}

	public int drawPrize(List<Integer> prizePool, int quota) {
		if (prizePool.size() >= quota) {
			throw new DrawPrizeException("抽奖名额已经用完");
		}
		List<Integer> lnArray = cassette.getLuckyNumber();
		int index = drawLuckyNumberIndex();
		int ln = lnArray.get(index);
		lnArray.remove(index);
		prizePool.add(ln);
		return ln;
	}

	public int drawLuckyNumberIndex() {
		return RandomUtils.nextInt(0, cassette.getLuckyNumber().size());
	}

	public int getMinLuckyNumber() {
		return minLuckyNumber;
	}

	public void setMinLuckyNumber(int minLuckyNumber) {
		this.minLuckyNumber = minLuckyNumber;
	}

	public int getMaxLuckyNumber() {
		return maxLuckyNumber;
	}

	public void setMaxLuckyNumber(int maxLuckyNumber) {
		this.maxLuckyNumber = maxLuckyNumber;
	}

	public Cassette getCassette() {
		return cassette;
	}

	public void setCassette(Cassette cassette) {
		this.cassette = cassette;
	}

	public int getGrandPrizeQuota() {
		return grandPrizeQuota;
	}

	public void setGrandPrizeQuota(int grandPrizeQuota) {
		this.grandPrizeQuota = grandPrizeQuota;
	}

	public int getFirstPrizeQuota() {
		return firstPrizeQuota;
	}

	public void setFirstPrizeQuota(int firstPrizeQuota) {
		this.firstPrizeQuota = firstPrizeQuota;
	}

	public int getSecondPrizeQuota() {
		return secondPrizeQuota;
	}

	public void setSecondPrizeQuota(int secondPrizeQuota) {
		this.secondPrizeQuota = secondPrizeQuota;
	}

	public int getThirdPrizeQuota() {
		return thirdPrizeQuota;
	}

	public void setThirdPrizeQuota(int thirdPrizeQuota) {
		this.thirdPrizeQuota = thirdPrizeQuota;
	}

	public List<Campaigner> getCampaigner() {
		return campaigner;
	}

	public void setCampaigner(List<Campaigner> campaigner) {
		this.campaigner = campaigner;
	}

	public int getVoteState() {
		return voteState;
	}

	public void setVoteState(int voteState) {
		this.voteState = voteState;
	}
	
	public void setNumberPeople(int pn) {
		setMaxLuckyNumber(pn);
	}
	
	public boolean isVote(int number){
		for (List<Integer> val : cassette.getP1().values()) {
			if(val.contains(number)){
				return true;
			}
		}
		return false;
	}
}
