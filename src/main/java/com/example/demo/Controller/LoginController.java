package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	//ログイン画面のGET用コントローラー
	@GetMapping("/login")
	public String getLogin(Model model) {

		//login.htmlに画面遷移
		return "login/login"; //login.htmlのパス
	}

	//ログイン画面のPOST用コントローラー
	@PostMapping("/login")
	public String postLogin(Model model) {

		//login.htmlに画面遷移
		return "login/login"; //login.htmlのパス
	}
}
