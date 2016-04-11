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
 * <p>ClusterGlusterVolumeGlusterBrickStatistics providing relation and functional services
 * <p>to {@link org.ovirt.engine.sdk.entities.Statistics }.
 */
@SuppressWarnings("unused")
public class ClusterGlusterVolumeGlusterBrickStatistics extends
        CollectionDecorator<org.ovirt.engine.sdk.entities.Statistic,
                            org.ovirt.engine.sdk.entities.Statistics,
                            ClusterGlusterVolumeGlusterBrickStatistic> {

    private ClusterGlusterVolumeGlusterBrick parent;

    /**
     * @param proxy HttpProxyBroker
     * @param parent ClusterGlusterVolumeGlusterBrick
     */
    public ClusterGlusterVolumeGlusterBrickStatistics(HttpProxyBroker proxy, ClusterGlusterVolumeGlusterBrick parent) {
        super(proxy, "statistics");
        this.parent = parent;
    }

    /**
     * Lists ClusterGlusterVolumeGlusterBrickStatistic objects.
     *
     * @return
     *     List of {@link ClusterGlusterVolumeGlusterBrickStatistic }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public List<ClusterGlusterVolumeGlusterBrickStatistic> list() throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName();
        return list(url, org.ovirt.engine.sdk.entities.Statistics.class, ClusterGlusterVolumeGlusterBrickStatistic.class);
    }

    /**
     * Fetches ClusterGlusterVolumeGlusterBrickStatistic object by id.
     *
     * @return
     *     {@link ClusterGlusterVolumeGlusterBrickStatistic }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public ClusterGlusterVolumeGlusterBrickStatistic get(UUID id) throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName() + SLASH + id.toString();
        return getProxy().get(url, org.ovirt.engine.sdk.entities.Statistic.class, ClusterGlusterVolumeGlusterBrickStatistic.class);
    }

    /**
     * Fetches ClusterGlusterVolumeGlusterBrickStatistic object by id.
     *
     * @return
     *     {@link ClusterGlusterVolumeGlusterBrickStatistic }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public ClusterGlusterVolumeGlusterBrickStatistic getById(String id) throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName() + SLASH + id;
        return getProxy().get(url, org.ovirt.engine.sdk.entities.Statistic.class, ClusterGlusterVolumeGlusterBrickStatistic.class);
    }

    /**
     * Lists ClusterGlusterVolumeGlusterBrickStatistic objects.
     *
     * @param max
     *    <pre>
     *    [max results]
     *    </pre>
     *
     *
     * @return List of {@link ClusterGlusterVolumeGlusterBrickStatistic }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    public List<ClusterGlusterVolumeGlusterBrickStatistic> list(Integer max) throws ClientProtocolException,
            ServerException, IOException {

        HttpHeaderBuilder headersBuilder = new HttpHeaderBuilder();
        List<Header> headers = headersBuilder.build();

        UrlBuilder urlBuilder = new UrlBuilder(this.parent.getHref() + SLASH + getName());
        if (max != null) {
            urlBuilder.add("max", max, UrlParameterType.MATRIX);
        }
        String url = urlBuilder.build();

        return list(url, org.ovirt.engine.sdk.entities.Statistics.class,
                ClusterGlusterVolumeGlusterBrickStatistic.class, headers);
    }

}

