package com.syntifi.casper.sdk.service;

import java.net.MalformedURLException;

import com.syntifi.casper.sdk.model.AbstractJsonTests;

import org.junit.jupiter.api.BeforeAll;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Abstract class for testing json rpc methods
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public abstract class AbstractJsonRpcTests extends AbstractJsonTests {
	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public enum CasperNetwork {
		MAIN_NET("195.201.142.76", 7777), TEST_NET("65.21.238.244", 7777), LOCAL_NET("csprnd.ddns.net", 11101);

		private String ip;
		private int port;
	}

	protected static CasperService casperServiceMainnet;
	protected static CasperService casperServiceTestnet;
	protected static CasperService casperServiceLocalnet;

	@BeforeAll
	public static void setUp() throws MalformedURLException {
		casperServiceMainnet = CasperService.usingPeer(CasperNetwork.MAIN_NET.getIp(),
				CasperNetwork.MAIN_NET.getPort());
		casperServiceTestnet = CasperService.usingPeer(CasperNetwork.TEST_NET.getIp(),
				CasperNetwork.TEST_NET.getPort());
		casperServiceLocalnet = CasperService.usingPeer(CasperNetwork.LOCAL_NET.getIp(),
				CasperNetwork.LOCAL_NET.getPort());
	}

}
