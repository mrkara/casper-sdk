package com.syntifi.casper;

import com.syntifi.casper.model.info.get.peers.CasperNode;
import com.syntifi.casper.model.info.get.peers.CasperNodeResponse;
import com.syntifi.casper.model.chain.get.block.CasperBlock;
import com.syntifi.casper.model.chain.get.block.CasperBlockResponse;
import com.syntifi.casper.model.chain.get.block.transfer.CasperTransfer;
import com.syntifi.casper.model.chain.get.block.transfer.CasperTransferResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.time.Duration;
import java.util.function.Supplier;

public class Casper{
    private String nodeIP;
    private int port;
    private HttpClient httpClient;

    public Casper(String nodeAddress, int nodePort) {
        this(nodeAddress, nodePort, 10);
    }

    public Casper(String nodeIP, int nodePort, int connectionTimeOut) {
        this.nodeIP = nodeIP;
        port = nodePort;
        httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(connectionTimeOut))
            .build();
    }

    private HttpRequest buildRequest(String rpcMethod) {
        return HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(rpcMethod))
                .uri(URI.create("http://" + nodeIP + ":" + this.port + "/rpc"))
                .header("Content-Type", "application/json")
                .build();
    }

    private String buildRpcString(String method, String params){
        long id = Math.round(Math.random()*1000);
        StringBuilder rpcMethod = new StringBuilder()
                .append("{")
                .append("\"jsonrpc\":\"2.0\",")
                .append("\"id\":\""+ id +"\",")
                .append("\"method\":\""+ method +"\"");
        if (params != null) {
                rpcMethod.append(",\"params\":{" + params + "}");
        }

        return rpcMethod
                .append("}")
                .toString();
    }

    private <T> HttpResponse<Supplier<T>> getSyncHttpResponse(String rpcMethod, Class<T> targeClass) throws IOException, InterruptedException{
        HttpRequest request = buildRequest(rpcMethod);
        return httpClient.send(request, new JsonBodyHandler<>(targeClass));
    }

    private CasperBlock getBlockRPC(String param) throws IOException, InterruptedException {
        String rpcMethod = buildRpcString("chain_get_block", param); 
        var response = getSyncHttpResponse(rpcMethod, CasperBlockResponse.class);
        return response.body().get().result.block;
    }
    
    private List<CasperTransfer> getTransfersRPC(String param) throws IOException, InterruptedException {
        String rpcMethod = buildRpcString("chain_get_block_transfers", param);
        var response = getSyncHttpResponse(rpcMethod, CasperTransferResponse.class);
        return response.body().get().result.transfers;
    }

    /*
    curl -X POST -H 'Content-Type: application/json' \ 
          -d '{"jsonrpc":"2.0","id":"980","method":"info_get_peers"}' \
           http://<NODE_IP>:7777/rpc | json_pp
    */
    public List<CasperNode> getNodes() throws IOException, InterruptedException {
        String rpcMethod = buildRpcString("info_get_peers", null);
        var response = getSyncHttpResponse(rpcMethod, CasperNodeResponse.class);
        return response.body().get().result.peers;
    }

    /*
    curl -X POST -H 'Content-Type: application/json' \ 
          -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block", "params":{"block_identifier":{"Hash": "<BLOCK_HASH>"}}}' \
           http://<NODE_IP>:7777/rpc | json_pp
    */
    public CasperBlock getBlock(String blockHash) throws IOException, InterruptedException {
        return getBlockRPC("\"block_identifier\":{\"Hash\":\"" + blockHash + "\"}");
    }

    /*
    curl -X POST -H 'Content-Type: application/json' \ 
          -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block", "params":{"block_identifier":{"Height": <BLOCK_HEIGHT>}}}' \
           http://<NODE_IP>:7777/rpc | json_pp
    */
    public CasperBlock getBlock(long blockHeight) throws IOException, InterruptedException {
        return getBlockRPC("\"block_identifier\":{\"Height\":" + blockHeight + "}");
    }

    /*
    curl -X POST -H 'Content-Type: application/json' \ 
          -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block"}' \
           http://<NODE_IP>:7777/rpc | json_pp
    */
    public CasperBlock getBlock() throws IOException, InterruptedException {
        return getBlockRPC(null);
    }

    /*
    curl -X POST -H 'Content-Type: application/json' \ 
          -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block_transfers", "params":{"block_identifier":{"Hash": "<BLOCK_HASH>"}}}' \
           http://<NODE_IP>:7777/rpc | json_pp
    */
    public List<CasperTransfer> getTransfers(String blockHash) throws IOException, InterruptedException {
        return getTransfersRPC("\"block_identifier\":{\"Hash\":\""+ blockHash + "\"}");
    }

    /*
    curl -X POST -H 'Content-Type: application/json' \ 
          -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block_transfers", "params":{"block_identifier":{"Height": <BLOCK_HEIGHT>}}}' \
           http://<NODE_IP>:7777/rpc | json_pp
    */
    public List<CasperTransfer> getTransfers(long blockHeight) throws IOException, InterruptedException {
        return getTransfersRPC("\"block_identifier\":{\"Height\":\""+ blockHeight + "\"}");
    }

    /*
    curl -X POST -H 'Content-Type: application/json' \ 
          -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block_transfers"}' \
           http://<NODE_IP>:7777/rpc | json_pp
    */
    public List<CasperTransfer> getTransfers() throws IOException, InterruptedException {
        return getTransfersRPC(null);
    }

}
