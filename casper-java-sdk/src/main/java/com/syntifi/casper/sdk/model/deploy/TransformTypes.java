package com.syntifi.casper.sdk.model.deploy;

import com.syntifi.casper.sdk.exception.NoSuchTypeException;
import com.syntifi.casper.sdk.model.storedvalue.clvalue.AbstractCLValue;

import lombok.Getter;

@Getter
public enum TransformTypes {
    ENUM("enum", TransformWriteContract.class),
    WRITE_CLVALUE("WriteCLValue", AbstractCLValue.class),
    WRITE_ACCOUNT("WriteAccount", String.class);
    //WRITE_DEPLOY_INFO("WriteDeployInfo", DeployInfo.class),
    //WRITE_ERA_INFO("WriteEraInfo", EraInfo.class),
    //WRITE_TRANSFER("WriteTransfer", )

    private final String name;
    private final Class<?> clazz;

    private TransformTypes(String name, Class<?> clazz) {
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
        for (TransformTypes t : values()) {
            if (t.name.equals(name)) {
                return t.getClazz();
            }
        }
        throw new NoSuchTypeException();
    }


 
}
