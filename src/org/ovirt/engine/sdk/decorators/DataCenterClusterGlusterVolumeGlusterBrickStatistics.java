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
 * <p>DataCenterClusterGlusterVolumeGlusterBrickStatistics providing relation and functional services
 * <p>to {@link org.ovirt.engine.sdk.entities.Statistics }.
 */
@SuppressWarnings("unused")
public class DataCenterClusterGlusterVolumeGlusterBrickStatistics extends
        CollectionDecorator<org.ovirt.engine.sdk.entities.Statistic,
                            org.ovirt.engine.sdk.entities.Statistics,
                            DataCenterClusterGlusterVolumeGlusterBrickStatistic> {

    private DataCenterClusterGlusterVolumeGlusterBrick parent;

    /**
     * @param proxy HttpProxyBroker
     * @param parent DataCenterClusterGlusterVolumeGlusterBrick
     */
    public DataCenterClusterGlusterVolumeGlusterBrickStatistics(HttpProxyBroker proxy, DataCenterClusterGlusterVolumeGlusterBrick parent) {
        super(proxy, "statistics");
        this.parent = parent;
    }

    /**
     * Lists DataCenterClusterGlusterVolumeGlusterBrickStatistic objects.
     *
     * @return
     *     List of {@link DataCenterClusterGlusterVolumeGlusterBrickStatistic }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public List<DataCenterClusterGlusterVolumeGlusterBrickStatistic> list() throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName();
        return list(url, org.ovirt.engine.sdk.entities.Statistics.class, DataCenterClusterGlusterVolumeGlusterBrickStatistic.class);
    }

    /**
     * Fetches DataCenterClusterGlusterVolumeGlusterBrickStatistic object by id.
     *
     * @return
     *     {@link DataCenterClusterGlusterVolumeGlusterBrickStatistic }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public DataCenterClusterGlusterVolumeGlusterBrickStatistic get(UUID id) throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName() + SLASH + id.toString();
        return getProxy().get(url, org.ovirt.engine.sdk.entities.Statistic.class, DataCenterClusterGlusterVolumeGlusterBrickStatistic.class);
    }

    /**
     * Fetches DataCenterClusterGlusterVolumeGlusterBrickStatistic object by id.
     *
     * @return
     *     {@link DataCenterClusterGlusterVolumeGlusterBrickStatistic }
     *
     * @throws ClientProtocolException
     *             Signals that HTTP/S protocol error has occurred.
     * @throws ServerException
     *             Signals that an oVirt api error has occurred.
     * @throws IOException
     *             Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public DataCenterClusterGlusterVolumeGlusterBrickStatistic getById(String id) throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName() + SLASH + id;
        return getProxy().get(url, org.ovirt.engine.sdk.entities.Statistic.class, DataCenterClusterGlusterVolumeGlusterBrickStatistic.class);
    }


}

