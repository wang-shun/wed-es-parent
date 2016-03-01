package com.cip.wed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created by huachao on 2/26/16.
 */
public class WedEsTransportClient {

    private static final Logger logger = LoggerFactory.getLogger(WedEsTransportClient.class);

    private Client transportClient;
    private ObjectMapper mapper; // create once, reuse

    private String esServers;
    private String clusterName;

    public void init(){
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();
        transportClient = TransportClient.builder().settings(settings).build();
        TransportClient tmpClient = (TransportClient) transportClient;
        try {
            String[] esServerArray = esServers.split(" ");
            for (String esServer : esServerArray) {
                String[] parts = esServer.split(":");
                InetAddress inetAddress = InetAddress.getByName(parts[0]);
                int port = Integer.parseInt(parts[1]);
                tmpClient.addTransportAddress(new InetSocketTransportAddress(inetAddress,port));
            }
        } catch (Exception ex) {
            logger.error("client.properties文件中配置错误! 正确配置格式是:wed-es.servers=ip1:port1 ip2:port2", ex);
            System.exit(-1);
        }
        mapper = new ObjectMapper();
    }

    public void disconnect() {
        if (transportClient!=null)
            transportClient.close();
    }

    public Client getTransportClient(){
        return this.transportClient;
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

    public void setEsServers(String esServers) {
        this.esServers = esServers;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

}
