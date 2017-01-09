package com.sanyin.uranos.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EngineService {
	private static Logger LOGGER = LoggerFactory.getLogger(EngineService.class);
	private static String CONFIG_PATH = SystemUtils.USER_DIR + "/config.json";
	private ObjectMapper mapper = new ObjectMapper();
	private DataBox box;

	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException {
		box = mapper.readValue(new File(CONFIG_PATH), DataBox.class);
		box.start(false);
	}

	public void vote(String p1, String p2, String p3, String p4, String p5, int src) {
		box.vote(p1, p2, p3, p4, p5, src);
		boxPersistence();
	}

	public List<Campaigner> getCampaigner() {
		return box.getCampaigner();
	}

	public byte[] getResource(String number) {
		try {
			return FileUtils.readFileToByteArray(new File(SystemUtils.USER_DIR + "/repo/player" + number + "@2x.jpg"));
		} catch (IOException e) {
			return new byte[] {};
		}
	}

	public String drawGrandPrize() {
		return drawPrize(box.drawGrandPrize());
	}

	public String drawFirstPrize() {
		return drawPrize(box.drawFirstPrize());
	}

	public String drawSecondPrize() {
		return drawPrize(box.drawSecondPrize());
	}

	public String drawThirdPrize() {
		return drawPrize(box.drawThirdPrize());
	}

	public synchronized String drawPrize(int ln) {
		try {
			mapper.writeValue(new File(CONFIG_PATH), box);
		} catch (IOException e) {
			LOGGER.error("保存抽奖记录失败", e);
		}
		return StringUtils.leftPad(ln + "", 3, '0');
	}

	public synchronized void boxPersistence() {
		try {
			mapper.writeValue(new File(CONFIG_PATH), box);
		} catch (IOException e) {
			LOGGER.error("持久化失败", e);
		}
	}

	public int getVoteState() {
		return box.getVoteState();
	}

	public Cassette getCassette() {
		return box.getCassette();
	}

	public void setVoteState(int voteState) {
		box.setVoteState(voteState);
		boxPersistence();
	}

	public void setNumberPeople(int pn) {
		box.setNumberPeople(pn);
		box.setVoteState(1);
		box.start(true);
		boxPersistence();
	}

	public int getNumberPeople() {
		return box.getMaxLuckyNumber();
	}

	public boolean isVote(int number) {
		return box.isVote(number);
	}

	public List<Campaigner> getWinners() {
		List<Campaigner> winner = new ArrayList<>();
		Map<String, List<Integer>> p1 = box.getCassette().getP1();
		Map<String, List<Integer>> p2 = box.getCassette().getP2();
		Map<String, List<Integer>> p3 = box.getCassette().getP3();
		Map<String, List<Integer>> p4 = box.getCassette().getP4();
		Map<String, List<Integer>> p5 = box.getCassette().getP5();

		winner.add(getWinner(p1, winner));
		winner.add(getWinner(p2, winner));
		winner.add(getWinner(p3, winner));
		winner.add(getWinner(p4, winner));
		winner.add(getWinner(p5, winner));
		return winner;
	}

	private Campaigner getWinner(Map<String, List<Integer>> map, List<Campaigner> winner) {
		String _key = "";
		int _count = -1;
		for (String key : map.keySet()) {
			if (_count < map.get(key).size() && !isContain(winner, key)) {
				_count = map.get(key).size();
				_key = key;
			}
		}
		return getCampaigner(_key);
	}

	private boolean isContain(List<Campaigner> winner, String number) {
		for (Campaigner campaigner : winner) {
			if (campaigner.getNumber().equals(number)) {
				return true;
			}
		}
		return false;
	}

	private Campaigner getCampaigner(String number) {
		List<Campaigner> campaigners = box.getCampaigner();
		for (Campaigner campaigner : campaigners) {
			if (campaigner.getNumber().equals(number)) {
				return campaigner;
			}
		}
		return null;
	}

	public static Map<String, List<Integer>> sortMapByValue(Map<String, List<Integer>> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, List<Integer>> sortedMap = new LinkedHashMap<String, List<Integer>>();
		List<Map.Entry<String, List<Integer>>> entryList = new ArrayList<Map.Entry<String, List<Integer>>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<String, List<Integer>>> iter = entryList.iterator();
		Map.Entry<String, List<Integer>> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
}

class MapValueComparator implements Comparator<Map.Entry<String, List<Integer>>> {
	@Override
	public int compare(Entry<String, List<Integer>> me1, Entry<String, List<Integer>> me2) {
		return me1.getValue().size() > me2.getValue().size() ? 1 : -1;
	}
}
