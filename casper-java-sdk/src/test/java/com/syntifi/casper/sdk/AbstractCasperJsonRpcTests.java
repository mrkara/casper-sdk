package com.syntifi.casper.sdk;

import java.net.MalformedURLException;

import com.syntifi.casper.sdk.service.CasperService;

import org.junit.jupiter.api.BeforeAll;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class AbstractCasperJsonRpcTests {
	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public enum CasperNetwork {
		MAIN_NET("45.76.251.225", 7777), TEST_NET("144.76.97.151", 7777);

		private String ip;
		private int port;
	}

	protected static CasperService casperServiceMainnet;
	protected static CasperService casperServiceTestnet;

	@BeforeAll
	public static void setUp() throws MalformedURLException {
		casperServiceMainnet = CasperService.usingPeer(CasperNetwork.MAIN_NET.getIp(),
				CasperNetwork.MAIN_NET.getPort());
		casperServiceTestnet = CasperService.usingPeer(CasperNetwork.TEST_NET.getIp(),
				CasperNetwork.TEST_NET.getPort());
	}
    
}
