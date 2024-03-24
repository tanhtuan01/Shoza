package com.tat.shoza.base;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.tat.shoza.dto.ItemDTO;
import com.tat.shoza.dto.OrderInfoDTO;
import com.tat.shoza.model.User;
import com.tat.shoza.service.UserService;

public abstract class BASE_METHOD {

	@Autowired
	public UserService userService;
	
	private static ItemDTO[] itemDTOs;
	private static OrderInfoDTO[] infoDTOs;

	public User getUserAuthen(Authentication authentication) {
		if (authentication != null) {
			String email = authentication.getName();
			User user = userService.getByEmail(email);
			return user;
		} else {
			return null;
		}

	}

	public static String categoryPathUploadImg(String imageName) {
		String filePath = Paths.get("src", "main", "resources", "static", "img", "web", "category", imageName)
				.toString();
		return filePath;
	}

	public static String productPathUploadImg(String imageName) {
		String filePath = Paths.get("src", "main", "resources", "static", "img", "web", "product", imageName)
				.toString();
		return filePath;
	}

	public static String slidePathUploadImg(String imageName) {
		String filePath = Paths.get("src", "main", "resources", "static", "img", "web", "slide", imageName).toString();
		return filePath;
	}

	public static String randomImgName() {
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();

		String str = "Shoza_d" + currentDate.getDayOfMonth() + "m" + (currentDate.getMonthValue()) + "y"
				+ (currentDate.getYear()) + "_hh" + currentTime.getHour() + "mn" + currentTime.getMinute() + "sc"
				+ currentTime.getSecond() + "_tat";
		return str;
	}

	public static String randomOrderCode() {
		LocalDate currentDate = LocalDate.now();
		Random rand = new Random();
		int day = currentDate.getDayOfMonth();
		String str = "";
		if (day < 10) {
			str += currentDate.getDayOfMonth() + "" + currentDate.getDayOfMonth();
		}
		int month = currentDate.getMonthValue();
		if (month < 10) {
			str += currentDate.getMonthValue() + "" + currentDate.getMonthValue();
		}
		str += currentDate.getYear() + "" + rand.nextInt(1000000);
		// length: 14
		return str;
	}

	public static ItemDTO[] getItemDTOs() {
		return itemDTOs;
	}

	public static void setItemDTOs(ItemDTO[] itemDTOs) {
		BASE_METHOD.itemDTOs = itemDTOs;
	}

	public static OrderInfoDTO[] getInfoDTOs() {
		return infoDTOs;
	}

	public static void setInfoDTOs(OrderInfoDTO[] infoDTOs) {
		BASE_METHOD.infoDTOs = infoDTOs;
	}

	
	
	 

}
