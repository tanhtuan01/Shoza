package com.tat.shoza.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.tat.shoza.model.SetUp;
import com.tat.shoza.model.User;
import com.tat.shoza.service.SetUpService;

@Component
public class WebInfoHelper {

	@Autowired
	private SetUpService infoService;
	
	@Autowired
	private CartHelper cartHelper;
	
	@Autowired
	private UserHelper userHelper;
	
	public SetUp getSetUp(String name) {
		SetUp setUp = infoService.findValueByName(name);
		return setUp;
	}
	
	public String hotline() {
		return getSetUp("shoza_hotline").getSetValue();
	}
	
	public String email() {
		return getSetUp("contact_email").getSetValue();
	}
	
	public Integer productBelowCategory() {
		return Integer.valueOf(getSetUp("index_product_below_category").getSetValue());
	}
	
	public Integer productByCategory() {
		return Integer.valueOf(getSetUp("product_by_category").getSetValue());
	}
	
	public String articleIntroduce() {
		return getSetUp("article_introduce").getSetValue();
	}
	
	public void dataWebLayout(String content ,Model model, Authentication authentication) {
		if(authentication != null) {
			User user = userHelper.getUserAuthen(authentication);
			model.addAttribute("user", user);
			model.addAttribute("totalQuantity", cartHelper.totalQuantityCartItemByUser(user.getId()));
			model.addAttribute("listCartByUser", cartHelper.listCartByUser(user.getId()));
		}
		model.addAttribute("content", content);
	}
	
}
