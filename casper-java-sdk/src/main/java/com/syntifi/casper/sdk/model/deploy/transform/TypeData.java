package com.syntifi.casper.sdk.model.deploy.transform;

import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.Getter;

@Getter
public enum TypeData {
    ENUM_ID("Identity", WriteContract.class),
    ENUM_CONTR_WASM("WriteContractWasm", WriteContract.class),
    ENUM_CONTR("WriteContract", WriteContract.class),
    ENUM_CONTR_PKG("WriteContractPackage", WriteContract.class),
    WRITE_CLVALUE("WriteCLValue", WriteCLValue.class),
    WRITE_ACCOUNT("WriteAccount", WriteAccount.class),
    WRITE_DEPLOY_INFO("WriteDeployInfo", WriteDeployInfo.class),
    WRITE_ERA_INFO("WriteEraInfo", WriteEraInfo.class),
    WRITE_TRANSFER("WriteTransfer", WriteTransfer.class),
    WRITE_BID("WriteBid", WriteBid.class),
    WRITE_WITHDRAW("WriteWithdraw", WriteWithdraw.class),
    WRITE_ADDU512("AddUInt512", AddUInt512.class);

    private final String name;
    private final Class<?> clazz;

    private TypeData(String name, Class<?> clazz) {
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
        for (TypeData t : values()) {
            if (t.name.equals(name)) {
                return t.getClazz();
            }
        }
        throw new NoSuchTypeException();
    }
 
}