package com.wbh.easyjob4trainee.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RandomPasswordFactory {
	
	private String result;
	@Value("${randomPassword.length}")
	private int length;
	
	public String getResult(){
		final int maxNum = 36;
		int i; 
		int count = 0; 
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < length) {
		i = Math.abs(r.nextInt(maxNum)); 
		if (i >= 0 && i < str.length) {
		pwd.append(str[i]);
		count++;
		}
		}
		result =  pwd.toString();
		return result;
	}

}
