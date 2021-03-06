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
 * <p>Networks providing relation and functional services
 * <p>to {@link org.ovirt.engine.sdk.entities.Networks }.
 */
@SuppressWarnings("unused")
public class Networks extends
        CollectionDecorator<org.ovirt.engine.sdk.entities.Network,
                            org.ovirt.engine.sdk.entities.Networks,
                            Network> {

    /**
     * @param proxy HttpProxyBroker
     */
    public Networks(HttpProxyBroker proxy) {
        super(proxy, "networks");
    }

    /**
     * Lists Network objects.
     *
     * @return
     *     List of {@link Network }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public List<Network> list() throws ClientProtocolException,
            ServerException, IOException {
        String url = SLASH + getName();
        return list(url, org.ovirt.engine.sdk.entities.Networks.class, Network.class);
    }

    /**
     * Fetches Network object by id.
     *
     * @return {@link Network }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public Network get(UUID id) throws ClientProtocolException,
            ServerException, IOException {
        String url = SLASH + getName() + SLASH + id.toString();
        return getProxy().get(url, org.ovirt.engine.sdk.entities.Network.class, Network.class);
    }

    /**
     * Fetches Network object by id.
     *
     * @return {@link Network }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public Network getById(String id) throws ClientProtocolException,
            ServerException, IOException {
        String url = SLASH + getName() + SLASH + id;
        return getProxy().get(url, org.ovirt.engine.sdk.entities.Network.class, Network.class);
    }

    /**
     * Lists Network objects.
     *
     * @param query
     *    <pre>
     *    [search query]
     *    </pre>
     * @param caseSensitive
     *    <pre>
     *    [true|false]
     *    </pre>
     * @param max
     *    <pre>
     *    [max results]
     *    </pre>
     *
     *
     * @return List of {@link Network }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    public List<Network> list(String query, Boolean caseSensitive, Integer max) throws ClientProtocolException,
            ServerException, IOException {

        HttpHeaderBuilder headersBuilder = new HttpHeaderBuilder();
        List<Header> headers = headersBuilder.build();

        UrlBuilder urlBuilder = new UrlBuilder(SLASH + getName());
        if (query != null) {
            urlBuilder.add("search", query, UrlParameterType.QUERY);
        }
        if (caseSensitive != null) {
            urlBuilder.add("case_sensitive", caseSensitive, UrlParameterType.MATRIX);
        }
        if (max != null) {
            urlBuilder.add("max", max, UrlParameterType.MATRIX);
        }
        String url = urlBuilder.build();

        return list(url, org.ovirt.engine.sdk.entities.Networks.class,
                Network.class, headers);
    }
    /**
     * Adds Network object.
     *
     * @param network {@link org.ovirt.engine.sdk.entities.Network}
     *    <pre>
     *    network.data_center.id|name
     *    network.name
     *    [network.description]
     *    [network.comment]
     *    [network.vlan.id]
     *    [network.ip.address]
     *    [network.ip.gateway]
     *    [network.ip.netmask]
     *    [network.stp]
     *    [network.mtu]
     *    [network.profile_required]
     *    [network.usages.usage]
     *    </pre>
     *
     * @return
     *     {@link Network }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    public Network add(org.ovirt.engine.sdk.entities.Network network) throws
            ClientProtocolException, ServerException, IOException {
        String url = SLASH + getName();

        HttpHeaderBuilder headersBuilder = new HttpHeaderBuilder();
        List<Header> headers = headersBuilder.build();

        UrlBuilder urlBuilder = new UrlBuilder(url);
        url = urlBuilder.build();

        return getProxy().add(url, network,
                org.ovirt.engine.sdk.entities.Network.class,
                Network.class, headers);
    }
    /**
     * Adds Network object.
     *
     * @param network {@link org.ovirt.engine.sdk.entities.Network}
     *    <pre>
     *    network.data_center.id|name
     *    network.name
     *    [network.description]
     *    [network.comment]
     *    [network.vlan.id]
     *    [network.ip.address]
     *    [network.ip.gateway]
     *    [network.ip.netmask]
     *    [network.stp]
     *    [network.mtu]
     *    [network.profile_required]
     *    [network.usages.usage]
     *    </pre>
     *
     * @param expect
     *    <pre>
     *    [201-created]
     *    </pre>
     *
     * @return
     *     {@link Network }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    public Network add(org.ovirt.engine.sdk.entities.Network network, String expect) throws
            ClientProtocolException, ServerException, IOException {
        String url = SLASH + getName();

        HttpHeaderBuilder headersBuilder = new HttpHeaderBuilder();
        if (expect != null) {
            headersBuilder.add("Expect", expect);
        }
        List<Header> headers = headersBuilder.build();

        UrlBuilder urlBuilder = new UrlBuilder(url);
        url = urlBuilder.build();

        return getProxy().add(url, network,
                org.ovirt.engine.sdk.entities.Network.class,
                Network.class, headers);
    }
    /**
     * Adds Network object.
     *
     * @param network {@link org.ovirt.engine.sdk.entities.Network}
     *    <pre>
     *    network.data_center.id|name
     *    network.name
     *    [network.description]
     *    [network.comment]
     *    [network.vlan.id]
     *    [network.ip.address]
     *    [network.ip.gateway]
     *    [network.ip.netmask]
     *    [network.stp]
     *    [network.mtu]
     *    [network.profile_required]
     *    [network.usages.usage]
     *    </pre>
     *
     * @param expect
     *    <pre>
     *    [201-created]
     *    </pre>
     * @param correlationId
     *    <pre>
     *    [any string]
     *    </pre>
     *
     * @return
     *     {@link Network }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    public Network add(org.ovirt.engine.sdk.entities.Network network, String expect, String correlationId) throws
            ClientProtocolException, ServerException, IOException {
        String url = SLASH + getName();

        HttpHeaderBuilder headersBuilder = new HttpHeaderBuilder();
        if (expect != null) {
            headersBuilder.add("Expect", expect);
        }
        if (correlationId != null) {
            headersBuilder.add("Correlation-Id", correlationId);
        }
        List<Header> headers = headersBuilder.build();

        UrlBuilder urlBuilder = new UrlBuilder(url);
        url = urlBuilder.build();

        return getProxy().add(url, network,
                org.ovirt.engine.sdk.entities.Network.class,
                Network.class, headers);
    }

}

