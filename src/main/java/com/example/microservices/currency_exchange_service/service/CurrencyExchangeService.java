package com.example.microservices.currency_exchange_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservices.currency_exchange_service.beans.CurrencyExchange;
import com.example.microservices.currency_exchange_service.repository.CurrencyExchangeRepository;


@Service
public class CurrencyExchangeService {
	
	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;
	
	public CurrencyExchange findByFromAndTo(String from,String to) {
		
	return currencyExchangeRepository.findByFromAndTo(from, to);	
		
	}
	
	

}
