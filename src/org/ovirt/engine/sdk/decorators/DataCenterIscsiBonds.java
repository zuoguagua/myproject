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

package org.ovirt.engine.sdk.decorators;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.ovirt.engine.sdk.common.CollectionDecorator;
import org.ovirt.engine.sdk.exceptions.ServerException;
import org.ovirt.engine.sdk.utils.CollectionUtils;
import org.ovirt.engine.sdk.utils.HttpHeaderBuilder;
import org.ovirt.engine.sdk.utils.HttpHeaderUtils;
import org.ovirt.engine.sdk.utils.UrlBuilder;
import org.ovirt.engine.sdk.utils.UrlBuilder;
import org.ovirt.engine.sdk.utils.UrlHelper;
import org.ovirt.engine.sdk.web.HttpProxyBroker;
import org.ovirt.engine.sdk.web.UrlParameterType;
import org.ovirt.engine.sdk.entities.Action;

/**
 * <p>DataCenterIscsiBonds providing relation and functional services
 * <p>to {@link org.ovirt.engine.sdk.entities.IscsiBonds }.
 */
@SuppressWarnings("unused")
public class DataCenterIscsiBonds extends
        CollectionDecorator<org.ovirt.engine.sdk.entities.IscsiBond,
                            org.ovirt.engine.sdk.entities.IscsiBonds,
                            DataCenterIscsiBond> {

    private DataCenter parent;

    /**
     * @param proxy HttpProxyBroker
     * @param parent DataCenter
     */
    public DataCenterIscsiBonds(HttpProxyBroker proxy, DataCenter parent) {
        super(proxy, "iscsibonds");
        this.parent = parent;
    }

    /**
     * Lists DataCenterIscsiBond objects.
     *
     * @return
     *     List of {@link DataCenterIscsiBond }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public List<DataCenterIscsiBond> list() throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName();
        return list(url, org.ovirt.engine.sdk.entities.IscsiBonds.class, DataCenterIscsiBond.class);
    }

    /**
     * Fetches DataCenterIscsiBond object by id.
     *
     * @return
     *     {@link DataCenterIscsiBond }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public DataCenterIscsiBond get(UUID id) throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName() + SLASH + id.toString();
        return getProxy().get(url, org.ovirt.engine.sdk.entities.IscsiBond.class, DataCenterIscsiBond.class);
    }

    /**
     * Fetches DataCenterIscsiBond object by id.
     *
     * @return
     *     {@link DataCenterIscsiBond }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public DataCenterIscsiBond getById(String id) throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName() + SLASH + id;
        return getProxy().get(url, org.ovirt.engine.sdk.entities.IscsiBond.class, DataCenterIscsiBond.class);
    }

    /**
     * Adds IscsiBond object.
     *
     * @param iscsibond {@link org.ovirt.engine.sdk.entities.IscsiBond}
     *    <pre>
     *    iscsibond.name
     *    </pre>
     *
     * @return
     *     {@link DataCenterIscsiBond }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    public DataCenterIscsiBond add(org.ovirt.engine.sdk.entities.IscsiBond iscsibond) throws
            ClientProtocolException, ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName();

        HttpHeaderBuilder headersBuilder = new HttpHeaderBuilder();
        List<Header> headers = headersBuilder.build();

        UrlBuilder urlBuilder = new UrlBuilder(url);
        url = urlBuilder.build();

        return getProxy().add(url, iscsibond,
                org.ovirt.engine.sdk.entities.IscsiBond.class,
                DataCenterIscsiBond.class, headers);
    }
    /**
     * Adds IscsiBond object.
     *
     * @param iscsibond {@link org.ovirt.engine.sdk.entities.IscsiBond}
     *    <pre>
     *    iscsibond.name
     *    </pre>
     *
     * @param expect
     *    <pre>
     *    [201-created]
     *    </pre>
     *
     * @return
     *     {@link DataCenterIscsiBond }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    public DataCenterIscsiBond add(org.ovirt.engine.sdk.entities.IscsiBond iscsibond, String expect) throws
            ClientProtocolException, ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName();

        HttpHeaderBuilder headersBuilder = new HttpHeaderBuilder();
        if (expect != null) {
            headersBuilder.add("Expect", expect);
        }
        List<Header> headers = headersBuilder.build();

        UrlBuilder urlBuilder = new UrlBuilder(url);
        url = urlBuilder.build();

        return getProxy().add(url, iscsibond,
                org.ovirt.engine.sdk.entities.IscsiBond.class,
                DataCenterIscsiBond.class, headers);
    }

}

