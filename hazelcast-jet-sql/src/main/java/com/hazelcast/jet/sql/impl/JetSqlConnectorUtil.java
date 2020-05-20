/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet.sql.impl;

import com.hazelcast.jet.JetException;
import com.hazelcast.jet.sql.JetSqlConnector;
import com.hazelcast.jet.sql.impl.connector.imap.IMapSqlConnector;
import com.hazelcast.jet.sql.impl.schema.JetTable;
import com.hazelcast.sql.impl.schema.Table;
import com.hazelcast.sql.impl.schema.map.PartitionedMapTable;
import com.hazelcast.sql.impl.schema.map.ReplicatedMapTable;

public final class JetSqlConnectorUtil {

    private JetSqlConnectorUtil() { }

    public static JetSqlConnector getJetSqlConnector(Table table) {
        JetSqlConnector connector;
        if (table instanceof JetTable) {
            connector = ((JetTable) table).getSqlConnector();
        } else if (table instanceof PartitionedMapTable) {
            connector = new IMapSqlConnector();
        } else if (table instanceof ReplicatedMapTable) {
            throw new UnsupportedOperationException("Jet doesn't yet support writing to a ReplicatedMap");
        } else {
            throw new JetException("Unknown table type: " + table.getClass());
        }
        return connector;
    }

}
