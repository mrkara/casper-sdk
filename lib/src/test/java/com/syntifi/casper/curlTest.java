package com.syntifi.casper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
    private static List<String> ips = Arrays.asList("34.66.154.252", 
                                                    "134.209.16.172", 
                                                    "139.59.226.13", 
                                                    "135.181.56.53", 
                                                    "54.252.66.23");
    private static int port = 7777;
    private long lastBlockHeight;
    private String lastBlockParent;
    private static final Casper casper = new Casper(ip, port);
    private static final Casper casper10 = new Casper(ips, port, 10, 5);
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
        CasperBlock lastBlock = casper.getLastBlock();
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
        CasperBlock block = casper.getBlockByHeight(height);
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
    void getManyBlocskByHeight() throws IOException, InterruptedException, ExecutionException {
        List<Long> heights = Arrays.asList(lastBlockHeight - 1, 
                                            lastBlockHeight - 2, 
                                            lastBlockHeight - 3,
                                            lastBlockHeight - 4,
                                            lastBlockHeight - 5);
        List<CasperBlock> blocks = casper10.getBlocksByBlockHeights(heights);
        for (int i=0; i<heights.size(); i++) {
            String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                        "-H", "Content-Type:application/json", "-d", 
                        "{\"jsonrpc\":\"2.0\",\"id\":\"2\",\"method\":\"chain_get_block\", " +
                        "\"params\":{\"block_identifier\":{\"Height\": " + heights.get(i) + "}}}"};
            String curlResponse = curl(command);
            var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperBlockResponse.class);
            assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.block)), 
                         mapper.readTree(mapper.writeValueAsString(blocks.get(i))));
        }
    }

    @Test
    void getBlockByHash() throws IOException, InterruptedException {
        CasperBlock block = casper.getBlockByHash(lastBlockParent);
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
        CasperBlock block = casper.getBlockByHeight(height);
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
        List<CasperTransfer> transfers = casper.getLastBlockTransfers();
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
        List<CasperTransfer> transfers = casper.getTransfersByBlockHeight(height);
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
    void getManyTransfersByHeight() throws IOException, InterruptedException, ExecutionException {
        List<Long> heights = Arrays.asList(lastBlockHeight - 1, 
                                            lastBlockHeight - 2, 
                                            lastBlockHeight - 3,
                                            lastBlockHeight - 4,
                                            lastBlockHeight - 5);
        List<List<CasperTransfer>> transfers = casper10.getTransfersByBlockHeights(heights);
        for (int i=0; i<heights.size(); i++) {
            String[] command = {"curl", "-X",  "POST",  "http://"+ ip + ":" + port + "/rpc",
                        "-H", "Content-Type:application/json", "-d", 
                        "{\"jsonrpc\":\"2.0\",\"id\":\"2\",\"method\":\"chain_get_block_transfers\", " +
                        "\"params\":{\"block_identifier\":{\"Height\": " + heights.get(i) + "}}}"};
            String curlResponse = curl(command);
            var obj = mapper.treeToValue(mapper.readTree(curlResponse), CasperTransferResponse.class);
            assertEquals(mapper.readTree(mapper.writeValueAsString(obj.result.transfers)), 
                         mapper.readTree(mapper.writeValueAsString(transfers.get(i))));
        }
    }

    @Test
    void getTransfersByBlockByHash() throws IOException, InterruptedException {
        List<CasperTransfer> transfers = casper.getTransfersByBlockHash(lastBlockParent);
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
