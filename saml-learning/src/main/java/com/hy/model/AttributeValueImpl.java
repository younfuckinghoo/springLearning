package com.hy.model;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AttributeValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AttributeValueImpl   extends AbstractSAMLObject implements AttributeValue {

    protected AttributeValueImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Nullable
    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}
