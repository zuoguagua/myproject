//
// Copyright (c) 2012 Red Hat, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//           http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

// *********************************************************************
// ********************* GENERATED CODE - DO NOT MODIFY ****************
// *********************************************************************


package org.ovirt.engine.sdk.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Version complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Version">
 *   &lt;complexContent>
 *     &lt;extension base="{}BaseResource">
 *       &lt;attribute name="major" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *       &lt;attribute name="minor" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *       &lt;attribute name="build" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *       &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *       &lt;attribute name="full_version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Version")
@XmlSeeAlso({
    VersionCaps.class
})
public class Version
    extends BaseResource
{

    @XmlAttribute(name = "major")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer major;
    @XmlAttribute(name = "minor")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer minor;
    @XmlAttribute(name = "build")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer build;
    @XmlAttribute(name = "revision")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer revision;
    @XmlAttribute(name = "full_version")
    protected String fullVersion;

    /**
     * Gets the value of the major property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMajor() {
        return major;
    }

    /**
     * Sets the value of the major property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMajor(Integer value) {
        this.major = value;
    }

    /**
     * Gets the value of the minor property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMinor() {
        return minor;
    }

    /**
     * Sets the value of the minor property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMinor(Integer value) {
        this.minor = value;
    }

    /**
     * Gets the value of the build property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getBuild() {
        return build;
    }

    /**
     * Sets the value of the build property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setBuild(Integer value) {
        this.build = value;
    }

    /**
     * Gets the value of the revision property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getRevision() {
        return revision;
    }

    /**
     * Sets the value of the revision property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setRevision(Integer value) {
        this.revision = value;
    }

    /**
     * Gets the value of the fullVersion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFullVersion() {
        return fullVersion;
    }

    /**
     * Sets the value of the fullVersion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFullVersion(String value) {
        this.fullVersion = value;
    }

}

