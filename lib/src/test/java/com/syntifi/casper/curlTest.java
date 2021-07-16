package com.syntifi.casper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import com.syntifi.casper.model.chain.get.block.CasperBlock;
import com.syntifi.casper.model.chain.get.block.CasperBlockResponse;
import com.syntifi.casper.model.chain.get.block.transfer.CasperTransfer;
import com.syntifi.casper.model.chain.get.block.transfer.CasperTransferResponse;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class curlTest {
    private static String ip = "34.66.154.252";
    private static int port = 7777;
    private long lastBlockHeight;
    private String lastBlockParent;
    private static final Casper casper = new Casper(ip, port);
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void config() {
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }
    
    private String curl(String[] command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        return new BufferedReader(
                        new InputStreamReader(process.getInputStream()))
                            .lines()
                            .collect(Collectors.joining("\n"));
    }

    @Test
    void getLastBlock() throws IOException, InterruptedException {
        CasperBlock lastBlock = casper.getBlock();
        lastBlockParent = lastBlock.header.parentHash;
        lastBlockHeight = lastBlock.header.height;
        String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                    "-H", "Content-Type:application/json", "-d", 
                    "{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":\"chain_get_block\"}"};
        String curlResponse = curl(command);
        var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperBlockResponse.class);
        assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.block)), 
                     mapper.readTree(mapper.writeValueAsString(lastBlock)));
    }

    @Test
    void getBlockByHeight() throws IOException, InterruptedException {
        long height = lastBlockHeight - 5;
        CasperBlock block = casper.getBlock(height);
        String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                    "-H", "Content-Type:application/json", "-d", 
                    "{\"jsonrpc\":\"2.0\",\"id\":\"2\",\"method\":\"chain_get_block\", " +
                    "\"params\":{\"block_identifier\":{\"Height\": " + height + "}}}"};
        String curlResponse = curl(command);
        var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperBlockResponse.class);
        assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.block)), 
                     mapper.readTree(mapper.writeValueAsString(block)));
    }

    @Test
    void getBlockByHash() throws IOException, InterruptedException {
        CasperBlock block = casper.getBlock(lastBlockParent);
        String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                    "-H", "Content-Type:application/json", "-d", 
                    "{\"jsonrpc\":\"2.0\",\"id\":\"3\",\"method\":\"chain_get_block\", " +
                    "\"params\":{\"block_identifier\":{\"Hash\": \"" + lastBlockParent + "\"}}}"};
        String curlResponse = curl(command);
        var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperBlockResponse.class);
        assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.block)), 
                     mapper.readTree(mapper.writeValueAsString(block)));
    }

    @Test
    void getEndEraBlock() throws IOException, InterruptedException {
        long height = 106;
        CasperBlock block = casper.getBlock(height);
        String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                    "-H", "Content-Type:application/json", "-d", 
                    "{\"jsonrpc\":\"2.0\",\"id\":\"4\",\"method\":\"chain_get_block\", " +
                    "\"params\":{\"block_identifier\":{\"Height\": " + height + "}}}"};
        String curlResponse = curl(command);
        var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperBlockResponse.class);
        assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.block)), 
                     mapper.readTree(mapper.writeValueAsString(block)));
    }

    @Test
    void getLastBlockTransfers() throws IOException, InterruptedException {
        List<CasperTransfer> transfers = casper.getTransfers();
        String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                    "-H", "Content-Type:application/json", "-d", 
                    "{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":\"chain_get_block_transfers\"}"};
        String curlResponse = curl(command);
        var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperTransferResponse.class);
        assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.transfers)), 
                     mapper.readTree(mapper.writeValueAsString(transfers)));
    }

    @Test
    void getTransfersByBlockHeight() throws IOException, InterruptedException {
        long height = lastBlockHeight - 5;
        List<CasperTransfer> transfers = casper.getTransfers(height);
        String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                    "-H", "Content-Type:application/json", "-d", 
                    "{\"jsonrpc\":\"2.0\",\"id\":\"2\",\"method\":\"chain_get_block_transfers\", " +
                    "\"params\":{\"block_identifier\":{\"Height\": " + height + "}}}"};
        String curlResponse = curl(command);
        var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperTransferResponse.class);
        assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.transfers)), 
                     mapper.readTree(mapper.writeValueAsString(transfers)));
    }

    @Test
    void getTransfersByBlockByHash() throws IOException, InterruptedException {
        List<CasperTransfer> transfers = casper.getTransfers(lastBlockParent);
        String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                    "-H", "Content-Type:application/json", "-d", 
                    "{\"jsonrpc\":\"2.0\",\"id\":\"3\",\"method\":\"chain_get_block_transfers\", " +
                    "\"params\":{\"block_identifier\":{\"Hash\": \"" + lastBlockParent + "\"}}}"};
        String curlResponse = curl(command);
        var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperTransferResponse.class);
        assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.transfers)), 
                     mapper.readTree(mapper.writeValueAsString(transfers)));
    }
}
