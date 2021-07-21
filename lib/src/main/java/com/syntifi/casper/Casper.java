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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.time.Duration;
import java.util.function.Supplier;

public class Casper{
    private int port;
    private ExecutorService executorService;
    private HttpClient httpClient;
    private Random rand = new Random();
    private List<String> ips = new ArrayList<>();

    public Casper(String nodeAddress, int nodePort) {
        this(nodeAddress, nodePort, 10, 1);
    }

    public Casper(String nodeIP, int nodePort, int connectionTimeOut, int threads) {
        ips.add(nodeIP);
        port = nodePort;
        executorService = Executors.newFixedThreadPool(threads);
        httpClient = HttpClient.newBuilder()
            .executor(executorService)
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(connectionTimeOut))
            .build();
    }

    public Casper(List<String> ips, int nodePort, int connectionTimeOut, int threads) {
        for (String ip : ips) {
            this.ips.add(ip);
        }
        port = nodePort;
        executorService = Executors.newFixedThreadPool(threads);
        httpClient = HttpClient.newBuilder()
            .executor(executorService)
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(connectionTimeOut))
            .build();
    }

    public List<String> getIPs(){
        return this.ips;
    }

    private HttpRequest buildRequest(String rpcMethod, String ip) {
        return HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(rpcMethod))
                .uri(URI.create("http://" + ip + ":" + this.port + "/rpc"))
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

    private String getRandomNodeIP (){
        return ips.get(rand.nextInt(ips.size()));
    }

    private <T> HttpResponse<Supplier<T>> getSyncHttpResponse(String rpcMethod, Class<T> targeClass) throws IOException, InterruptedException{
        String IP = getRandomNodeIP();
        HttpRequest request = buildRequest(rpcMethod, IP);
        return httpClient
                    .send(request, new JsonBodyHandler<>(targeClass));
    }

    private <T> CompletableFuture<HttpResponse<Supplier<T>>> getAsyncHttpResponse(String rpcMethod, Class<T> targeClass) throws IOException, InterruptedException{
        String IP = getRandomNodeIP();
        HttpRequest request = buildRequest(rpcMethod, IP);
        return httpClient.sendAsync(request, new JsonBodyHandler<>(targeClass));
    }
    
    private <T> T getSyncRPC(String method, String param, Class<T> targetClass) throws IOException, InterruptedException {
        String rpcMethod = buildRpcString(method, param); 
        var response = getSyncHttpResponse(rpcMethod, targetClass);
        return response.body().get();
    }

    private <T> CompletableFuture<T> getAsyncRPC(String method, String param, Class<T> targetClass) throws IOException, InterruptedException {
        String rpcMethod = buildRpcString(method, param); 
        return getAsyncHttpResponse(rpcMethod, targetClass)
                            .thenApply(x -> x.body().get());
    }

    private <T> List<CompletableFuture<T>> getManyRPCs(String method, List<String> params, Class<T> targetClass) throws IOException, InterruptedException {
        List<CompletableFuture<T>> result = new ArrayList<>();
        for (String param: params) {
            result.add(getAsyncHttpResponse(buildRpcString(method, param), targetClass)
                            .thenApply(x -> x.body().get()));

        }
        return result;
    }

    /***
     * curl -X POST -H 'Content-Type: application/json' \ 
     *     -d '{"jsonrpc":"2.0","id":"980","method":"info_get_peers"}' \
     *      http://<NODE_IP>:7777/rpc | json_pp
     * 
     * @return List<CasperNode>
     * @throws IOException
     * @throws InterruptedException
     */
    public List<CasperNode> getNodes() throws IOException, InterruptedException {
        var response = getSyncRPC("info_get_peers", null, CasperNodeResponse.class);
        return response.result.peers;
    }

/*    private List<CasperBlock> getBlockRPCs(List<String> params) throws IOException, InterruptedException, ExecutionException {
        List<String> rpcMethods = params
                .stream()
                .map(param -> buildRpcString("chain_get_block", param))
                .collect(Collectors.toList()); 
        var response = getConcurrentHttpResponse(rpcMethods, CasperBlockResponse.class);
        List<CasperBlock> out = new ArrayList<>();
        for (CompletableFuture<HttpResponse<Supplier<CasperBlockResponse>>> r : response) {
           out.add(r.get().body().get().result.block) ;
        }
        return out;
    }
*/
    /***
     * curl -X POST -H 'Content-Type: application/json' \ 
     *     -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block", "params":{"block_identifier":{"Hash": "<BLOCK_HASH>"}}}' \
     *      http://<NODE_IP>:7777/rpc | json_pp
     * 
     * @return List<CasperNode>
     * @throws IOException
     * @throws InterruptedException
     */
    public CasperBlock getBlockByHash(String blockHash) throws IOException, InterruptedException {
        var response = getSyncRPC("chain_get_block", 
                                "\"block_identifier\":{\"Hash\":\"" + blockHash + "\"}", 
                                CasperBlockResponse.class);
        return response.result.block;
    }
    
    public List<CasperBlock> getBlocksByBlockHashes(List<String> blockHashes) throws IOException, InterruptedException, ExecutionException {
        List<String> params = blockHashes.stream()
                                .map(blockHash -> "\"block_identifier\":{\"Hash\":\""+ blockHash + "\"}")
                                .collect(Collectors.toList());
        var response = getManyRPCs("chain_get_block", params, CasperBlockResponse.class);
        List<CasperBlock> output = new ArrayList<>();
        for (CompletableFuture<CasperBlockResponse> r: response) {
            output.add(r.get().result.block);
        }
        return output;
    }

    /***
     * curl -X POST -H 'Content-Type: application/json' \ 
     *     -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block", "params":{"block_identifier":{"Height": <BLOCK_HEIGHT>}}}' \
     *      http://<NODE_IP>:7777/rpc | json_pp
     * 
     * @return List<CasperNode>
     * @throws IOException
     * @throws InterruptedException
     */
    public CasperBlock getBlockByHeight(long blockHeight) throws IOException, InterruptedException {
        var response = getSyncRPC("chain_get_block", 
                                "\"block_identifier\":{\"Height\":" + blockHeight + "}",
                                CasperBlockResponse.class);
        return response.result.block;
    }

    public List<CasperBlock> getBlocksByBlockHeights(List<Long> blockHeights) throws IOException, InterruptedException, ExecutionException {
        List<String> params = blockHeights.stream()
                                .map(blockHeight -> "\"block_identifier\":{\"Height\":" + blockHeight + "}")
                                .collect(Collectors.toList());
        var response = getManyRPCs("chain_get_block", params, CasperBlockResponse.class);
        List<CasperBlock> output = new ArrayList<>();
        for (CompletableFuture<CasperBlockResponse> r: response) {
            output.add(r.get().result.block);
        }
        return output;
    }

    /***
     * curl -X POST -H 'Content-Type: application/json' \ 
     *     -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block"}' \
     *      http://<NODE_IP>:7777/rpc | json_pp
     * 
     * @return List<CasperNode>
     * @throws IOException
     * @throws InterruptedException
     */
    public CasperBlock getLastBlock() throws IOException, InterruptedException {
        var response = getSyncRPC("chain_get_block", 
                                null,
                                CasperBlockResponse.class);
        return response.result.block;
    }
    
    /***
     * curl -X POST -H 'Content-Type: application/json' \ 
     *     -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block_transfers", "params":{"block_identifier":{"Hash": "<BLOCK_HASH>"}}}' \
     *      http://<NODE_IP>:7777/rpc | json_pp
     * 
     * @return List<CasperNode>
     * @throws IOException
     * @throws InterruptedException
     */
    public List<CasperTransfer> getTransfersByBlockHash(String blockHash) throws IOException, InterruptedException {
        var response = getSyncRPC("chain_get_block_transfers", 
                                "\"block_identifier\":{\"Hash\":\""+ blockHash + "\"}",
                                CasperTransferResponse.class);
        return response.result.transfers;
    }

    public List<List<CasperTransfer>> getTransfersByBlockHashes(List<String> blockHashes) throws IOException, InterruptedException, ExecutionException {
        List<String> params = blockHashes.stream()
                                .map(blockHash -> "\"block_identifier\":{\"Hash\":\""+ blockHash + "\"}")
                                .collect(Collectors.toList());
        var response = getManyRPCs("chain_get_block_transfers", params, CasperTransferResponse.class);
        List<List<CasperTransfer>> output = new ArrayList<>();
        for (CompletableFuture<CasperTransferResponse> r: response) {
            output.add(r.get().result.transfers);
        }
        return output;
    }

    /***
     * curl -X POST -H 'Content-Type: application/json' \ 
     *     -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block_transfers", "params":{"block_identifier":{"Height": <BLOCK_HEIGHT>}}}' \
     *      http://<NODE_IP>:7777/rpc | json_pp
     * 
     * @return List<CasperNode>
     * @throws IOException
     * @throws InterruptedException
     */
    public List<CasperTransfer> getTransfersByBlockHeight(long blockHeight) throws IOException, InterruptedException {
        var response = getSyncRPC("chain_get_block_transfers", 
                                "\"block_identifier\":{\"Height\":\""+ blockHeight + "\"}",
                                CasperTransferResponse.class);
        return response.result.transfers;
    }

    public List<List<CasperTransfer>> getTransfersByBlockHeights(List<Long> blockHeights) throws IOException, InterruptedException, ExecutionException {
        List<String> params = blockHeights.stream()
                                .map(blockHeight -> "\"block_identifier\":{\"Height\":" + blockHeight + "}")
                                .collect(Collectors.toList());
        var response = getManyRPCs("chain_get_block_transfers", params, CasperTransferResponse.class);
        List<List<CasperTransfer>> output = new ArrayList<>();
        for (CompletableFuture<CasperTransferResponse> r: response) {
            output.add(r.get().result.transfers);
        }
        return output;
    }

    /***
     * curl -X POST -H 'Content-Type: application/json' \ 
     *     -d '{"jsonrpc":"2.0","id":"980","method":"chain_get_block_transfers"}' \
     *      http://<NODE_IP>:7777/rpc | json_pp
     * 
     * @return List<CasperNode>
     * @throws IOException
     * @throws InterruptedException
     */
    public List<CasperTransfer> getLastBlockTransfers() throws IOException, InterruptedException {
        var response = getSyncRPC("chain_get_block_transfers", 
                                null,
                                CasperTransferResponse.class);
        return response.result.transfers;
    }
}
