package com.tat.shoza.controller;

import java.io.IOException;
import java.io.ObjectInputFilter.Config;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.config.VNPayConfig;
import com.tat.shoza.dto.ItemDTO;
import com.tat.shoza.dto.OrderInfoDTO;
import com.tat.shoza.model.Product;

@RestController
@RequestMapping(value = "/pay")
public class PaymentController {

	@Autowired
	private UserHelper userHelper;

	Long totalAmount = (long) 0;
	
	@GetMapping(value = "/get-order")
	public RedirectView getOrder(@RequestParam(name = "item") String items,
			@RequestParam(name = "info") String info) {
		totalAmount = (long)0;
		Gson gson = new Gson();
		ItemDTO[] itemDTOs = gson.fromJson(items, ItemDTO[].class);
		OrderInfoDTO[] infoDTOs = gson.fromJson(info, OrderInfoDTO[].class);
		
		BASE_METHOD.setInfoDTOs(infoDTOs);
		BASE_METHOD.setItemDTOs(itemDTOs);
		
		for(ItemDTO i : itemDTOs) {
			Long nPrice = i.getPrice() * i.getQuantity();
			totalAmount += nPrice;
		}
		
		return  new RedirectView("/pay");
	}
	
	@GetMapping
	public RedirectView payment(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws ServletException, IOException {
		RedirectView redirectView = new RedirectView();
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String order_Type = "other";
        long amount = totalAmount * 100;
        String bankCode = "NCB";
        
        String vnp_TxnRef = BASE_METHOD.randomOrderCode() + userHelper.getUserAuthen(authentication).getId();
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String vnp_IpAddr = "127.0.0.1";
               
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", order_Type);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while(itr.hasNext()) {
        	String fieldName = (String) itr.next();
        	String fieldValue = (String) vnp_Params.get(fieldName);
        	if(fieldName != null && fieldValue.length() > 0) {
        		 hashData.append(fieldName);
                 hashData.append('=');
                 hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                 //Build query
                 query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                 query.append('=');
                 query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                 if (itr.hasNext()) {
                     query.append('&');
                     hashData.append('&');
                 }
        	}
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        
        redirectView.setUrl(paymentUrl);
        return redirectView;
    }
    
	
}
