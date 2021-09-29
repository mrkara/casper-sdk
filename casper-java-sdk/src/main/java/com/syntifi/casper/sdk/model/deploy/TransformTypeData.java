package com.syntifi.casper.sdk.model.deploy;

import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.Getter;

@Getter
public enum TransformTypeData {
    ENUM_ID("Identity", TransformWriteContract.class),
    ENUM_CONTR_WASM("WriteContractWasm", TransformWriteContract.class),
    ENUM_CONTR("WriteContract", TransformWriteContract.class),
    ENUM_CONTR_PKG("WriteContractPackage", TransformWriteContract.class),
    WRITE_CLVALUE("WriteCLValue", TransformWriteCLValue.class),
    WRITE_ACCOUNT("WriteAccount", TransformWriteAccount.class),
    WRITE_DEPLOY_INFO("WriteDeployInfo", TransformWriteDeployInfo.class),
    WRITE_ERA_INFO("WriteEraInfo", TransformWriteEraInfo.class),
    WRITE_TRANSFER("WriteTransfer", TransformTransfer.class),
    WRITE_BID("WriteBid", TransformWriteBid.class),
    WRITE_WITHDRAW("WriteWithdraw", TransformWriteWithdraw.class),
    WRITE_ADDU512("AddUInt512", TransformAddUInt512.class);

    private final String name;
    private final Class<?> clazz;

    private TransformTypeData(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * Retrieve Transform implementation class from Transform name 
     * 
     * @param name
     * @return
     * @throws NoSuchCLTypeException
     */
    public static Class<?> getClassByName(String name) throws NoSuchTypeException {
        for (TransformTypeData t : values()) {
            if (t.name.equals(name)) {
                return t.getClazz();
            }
        }
        throw new NoSuchTypeException();
    }
 
}