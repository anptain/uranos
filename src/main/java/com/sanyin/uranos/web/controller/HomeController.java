package com.sanyin.uranos.web.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanyin.uranos.exception.DrawPrizeException;
import com.sanyin.uranos.service.EngineService;

@Controller
public class HomeController extends AbstractController {
	private static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private EngineService engineService;

	@RequestMapping("/draw_prize")
	public String drawPrize(Model model, HttpServletRequest request) {
		model.addAttribute("totalPnumber", engineService.getNumberPeople());
		return "draw/index";
	}
	@RequestMapping("/rvote")
	public String rvote(Model model, HttpServletRequest request) {
		try {
			model.addAttribute("winners",new ObjectMapper().writeValueAsString(engineService.getWinners()));
		} catch (JsonProcessingException e) {
		}
		return "vote/result";
	}

	@RequestMapping("/")
	public String home(Integer code, Model model, HttpServletRequest request) {
		if(code == null){
			return "vote/voted";
		}
		model.addAttribute("code", code);
		model.addAttribute("campaigner", engineService.getCampaigner());
		if (engineService.getVoteState() == 1) {
			return "vote/unstart";
		} else if (engineService.getVoteState() == 2) {
			if(engineService.isVote(code)){
				return "vote/voted";
			}else{
				return "vote/index";
			}
		} else {
			return "vote/close";
		}
	}

	@ResponseBody
	@RequestMapping("/vote")
	public AjaxResponse vote(String p1, String p2, String p3, String p4, String p5, Integer code) {
		try {
			engineService.vote(p1, p2, p3, p4, p5, code);
		} catch (Exception e) {
			LOGGER.error("投票异常", e);
		}
		return AjaxResponse.createSuccessResp();
	}

	@RequestMapping("/admin")
	public String admin(Model model, HttpServletRequest request) {
		model.addAttribute("voteState", engineService.getVoteState());
		model.addAttribute("cassette", engineService.getCassette());
		return "admin";
	}

	@ResponseBody
	@RequestMapping("/set_pn")
	public AjaxResponse setNumberPeople(int pn) {
		engineService.setNumberPeople(pn);
		return AjaxResponse.createSuccessResp();
	}

	@ResponseBody
	@RequestMapping("/start_vote")
	public AjaxResponse startVote(Model model, HttpServletRequest request) {
		engineService.setVoteState(2);
		return AjaxResponse.createSuccessResp();
	}

	@ResponseBody
	@RequestMapping("/stop_vote")
	public AjaxResponse stopVote(Model model, HttpServletRequest request) {
		engineService.setVoteState(3);
		return AjaxResponse.createSuccessResp();
	}

	
	@RequestMapping("/draw_first_prize")
	public String drawFirstPrize(Model model, HttpServletRequest request) {
		model.addAttribute("totalPnumber", engineService.getNumberPeople());
		return "draw/drawFirst";
	}
	@RequestMapping("/draw_second_prize")
	public String drawSecondPrize(Model model, HttpServletRequest request) {
		model.addAttribute("totalPnumber", engineService.getNumberPeople());
		return "draw/drawSecond";
	}
	@RequestMapping("/draw_third_prize")
	public String drawThirdPrize(Model model, HttpServletRequest request) {
		model.addAttribute("totalPnumber", engineService.getNumberPeople());
		return "draw/drawThird";
	}
	@RequestMapping("/draw_grand_prize")
	public String draw_grand_prize(Model model, HttpServletRequest request) {
		model.addAttribute("totalPnumber", engineService.getNumberPeople());
		return "draw/drawGrand";
	}
	
	@ResponseBody
	@RequestMapping("/draw_first_prize2")
	public AjaxResponse drawFirstPrize2(Model model, HttpServletRequest request) {
		try {
			return AjaxResponse.createSuccessResp(engineService.drawFirstPrize());
		} catch (DrawPrizeException exception) {
			return AjaxResponse.createExceptionResp(exception.getMessage());
		} catch (Exception e) {
			return AjaxResponse.createExceptionResp();
		}
	}

	@ResponseBody
	@RequestMapping("/draw_second_prize2")
	public AjaxResponse drawSecondPrize2(Model model, HttpServletRequest request) {
		try {
			return AjaxResponse.createSuccessResp(engineService.drawSecondPrize());
		} catch (DrawPrizeException exception) {
			return AjaxResponse.createExceptionResp(exception.getMessage());
		} catch (Exception e) {
			return AjaxResponse.createExceptionResp();
		}
	}

	@ResponseBody
	@RequestMapping("/draw_third_prize2")
	public AjaxResponse drawThirdPrize2(Model model, HttpServletRequest request) {
		try {
			return AjaxResponse.createSuccessResp(engineService.drawThirdPrize());
		} catch (DrawPrizeException exception) {
			return AjaxResponse.createExceptionResp(exception.getMessage());
		} catch (Exception e) {
			return AjaxResponse.createExceptionResp();
		}
	}

	@ResponseBody
	@RequestMapping("/draw_grand_prize2")
	public AjaxResponse drawGrandPrize2(Model model, HttpServletRequest request) {
		try {
			return AjaxResponse.createSuccessResp(engineService.drawGrandPrize());
		} catch (DrawPrizeException exception) {
			return AjaxResponse.createExceptionResp(exception.getMessage());
		} catch (Exception e) {
			return AjaxResponse.createExceptionResp();
		}
	}

	@RequestMapping("/repo")
	public void handleRequest(String number, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");

		ServletOutputStream out = response.getOutputStream();
		// write the data out
		out.write(engineService.getResource(number));
		try {
			out.flush();
		} finally {
			out.close();
		}
	}
}
