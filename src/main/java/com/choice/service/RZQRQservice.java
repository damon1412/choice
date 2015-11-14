package com.choice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choice.model.RZRQ;

@Service
public abstract class RZQRQservice {
	public abstract List<RZRQ> getRZRQfromWeb();
	public abstract void saveRzrq(List<RZRQ> rzrqList);
}
