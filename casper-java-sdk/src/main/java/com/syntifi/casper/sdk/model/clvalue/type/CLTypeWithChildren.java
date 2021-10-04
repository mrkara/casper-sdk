package com.syntifi.casper.sdk.model.clvalue.type;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syntifi.casper.sdk.exception.NoSuchTypeException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all types which have an array of child types
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false, of = { "childTypes" })
public abstract class CLTypeWithChildren extends CLType {

    @JsonIgnore
    private List<CLType> childTypes = new ArrayList<>();

    @JsonIgnore
    public CLTypeData getChildClTypeData(int index) throws NoSuchTypeException {
        return CLTypeData.getTypeByName(getChildTypes().get(index).getTypeName());
    }

    public void loadCLTypes(List<Object> childTypeObjects)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchTypeException {
        childTypes.clear();

        if (childTypeObjects != null) {
            for (Object childTypeObject : childTypeObjects) {
                addChildType(childTypeObject, childTypes);
            }
        }
    }

    public List<Object> loadChildTypeObjects(List<Object> childTypeObjects) {
        if (childTypeObjects == null) {
            childTypeObjects = new ArrayList<>();
        }

        childTypeObjects.clear();

        if (childTypes != null) {
            for (CLType childCLType : childTypes) {
                if (childCLType instanceof CLTypeWithChildren) {
                    LinkedHashMap<String, List<String>> typeWithChildren = new LinkedHashMap<>();
                    List<String> subChildren = new ArrayList<>();
                    for (CLType subChild : ((CLTypeWithChildren) childCLType).getChildTypes()) {
                        subChildren.add(subChild.getTypeName());
                    }
                    typeWithChildren.put(childCLType.getTypeName(), subChildren);
                    childTypeObjects.add(typeWithChildren);
                } else {
                    childTypeObjects.add(childCLType.getTypeName());
                }
            }
        }

        return childTypeObjects;
    }

    private void addChildType(Object childTypeObject, List<CLType> parent)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchTypeException {
        if (childTypeObject instanceof String) {
            parent.add(CLTypeData.getTypeByName(childTypeObject.toString()).getClTypeClass().getConstructor()
                    .newInstance());
        } else if (childTypeObject instanceof ArrayList) {
            for (Object child : (ArrayList<?>) childTypeObject) {
                addChildType(child, parent);
            }
        } else if (childTypeObject instanceof LinkedHashMap) {
            LinkedHashMap<?, ?> subChildTypes = (LinkedHashMap<?, ?>) childTypeObject;

            for (Entry<?, ?> entry : subChildTypes.entrySet()) {
                CLType nextParent = CLTypeData.getTypeByName(entry.getKey().toString()).getClTypeClass()
                        .getConstructor().newInstance();
                parent.add(nextParent);
                addChildType(entry.getValue(), ((CLTypeWithChildren) nextParent).getChildTypes());
            }
        }
    }
}
