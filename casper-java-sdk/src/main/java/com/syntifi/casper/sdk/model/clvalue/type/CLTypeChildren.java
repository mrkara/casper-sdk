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

@Setter
@Getter
@EqualsAndHashCode(callSuper = false, of = { "childTypes" })
public abstract class CLTypeChildren extends CLType {

    @JsonIgnore
    private List<CLType> childTypes = new ArrayList<>();

    @JsonIgnore
    public CLTypeData getChildClTypeData(int index) {
        try {
            return CLTypeData.getTypeByName(getChildTypes().get(index).getTypeName());
        } catch (NoSuchTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void loadCLTypes(List<Object> childTypeObjects) {
        childTypes.clear();

        if (childTypeObjects != null) {
            for (Object childTypeObject : childTypeObjects) {
                try {
                    addChildType(childTypeObject, childTypes);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException
                        | NoSuchTypeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
                if (childCLType instanceof CLTypeChildren) {
                    LinkedHashMap<String, List<String>> typeWithChildren = new LinkedHashMap<>();
                    List<String> subChildren = new ArrayList<>();
                    for (CLType subChild : ((CLTypeChildren) childCLType).getChildTypes()) {
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
                addChildType(entry.getValue(), ((CLTypeChildren) nextParent).getChildTypes());
            }
        }
    }
}
