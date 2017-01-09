package com.sanyin.uranos.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cassette {
	private List<Integer> luckyNumber;
	private List<Integer> grandPrize;
	private List<Integer> firstPrize;
	private List<Integer> secondPrize;
	private List<Integer> thirdPrize;

	private Map<String, List<Integer>> p1; //实力非凡奖
	private Map<String, List<Integer>> p2; //亮瞎眼球奖
	private Map<String, List<Integer>> p3; //老司机奖
	private Map<String, List<Integer>> p4; //套路奖
	private Map<String, List<Integer>> p5; //最佳曲目奖

	public Cassette() {
		luckyNumber = new ArrayList<>();
		grandPrize = new ArrayList<>();
		firstPrize = new ArrayList<>();
		secondPrize = new ArrayList<>();
		thirdPrize = new ArrayList<>();

		p1 = new HashMap<>();
		p2 = new HashMap<>();
		p3 = new HashMap<>();
		p4 = new HashMap<>();
		p5 = new HashMap<>();
	}

	public Cassette(int minLn, int maxLn) {
		this();
		for (int i = minLn; i <= maxLn; i++) {
			luckyNumber.add(i);
		}
	}

	public List<Integer> getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(List<Integer> luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public List<Integer> getGrandPrize() {
		return grandPrize;
	}

	public void setGrandPrize(List<Integer> grandPrize) {
		this.grandPrize = grandPrize;
	}

	public List<Integer> getFirstPrize() {
		return firstPrize;
	}

	public void setFirstPrize(List<Integer> firstPrize) {
		this.firstPrize = firstPrize;
	}

	public List<Integer> getSecondPrize() {
		return secondPrize;
	}

	public void setSecondPrize(List<Integer> secondPrize) {
		this.secondPrize = secondPrize;
	}

	public List<Integer> getThirdPrize() {
		return thirdPrize;
	}

	public void setThirdPrize(List<Integer> thirdPrize) {
		this.thirdPrize = thirdPrize;
	}

	public Map<String, List<Integer>> getP1() {
		return p1;
	}

	public void setP1(Map<String, List<Integer>> p1) {
		this.p1 = p1;
	}

	public Map<String, List<Integer>> getP2() {
		return p2;
	}

	public void setP2(Map<String, List<Integer>> p2) {
		this.p2 = p2;
	}

	public Map<String, List<Integer>> getP3() {
		return p3;
	}

	public void setP3(Map<String, List<Integer>> p3) {
		this.p3 = p3;
	}

	public Map<String, List<Integer>> getP4() {
		return p4;
	}

	public void setP4(Map<String, List<Integer>> p4) {
		this.p4 = p4;
	}

	public Map<String, List<Integer>> getP5() {
		return p5;
	}

	public void setP5(Map<String, List<Integer>> p5) {
		this.p5 = p5;
	}

}