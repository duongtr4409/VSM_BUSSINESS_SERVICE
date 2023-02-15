package com.vsm.business.common.Graph;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GraphServiceV2 {
    private static final String CLIENT_ID = "d435afb3-26ea-44b8-9486-ccaf133c7c56";
    private static final String CLIENT_SECRET = "gLe8Q~ggF_gHYL9sXQBQ1aiAbTrv6yQkUiOXdbmK";
    private static final String TENANT_ID = "e2c6dedd-f4aa-4599-b436-e649faf0d375";
    private static final List<String> SCOPES = new ArrayList<>(Arrays.asList("https://graph.microsoft.com/.default"));
    ClientSecretCredential clientSecretCredential;
    TokenCredentialAuthProvider tokenCredentialAuthProvider;
    GraphServiceClient graphServiceClient;

    public GraphServiceV2() {
        clientSecretCredential = new ClientSecretCredentialBuilder().clientId(CLIENT_ID).clientSecret(CLIENT_SECRET).tenantId(TENANT_ID).build();
        tokenCredentialAuthProvider = new TokenCredentialAuthProvider(SCOPES, clientSecretCredential);
        graphServiceClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();
    }

    public ClientSecretCredential getClientSecretCredential() {
        return clientSecretCredential;
    }

    public void setClientSecretCredential(ClientSecretCredential clientSecretCredential) {
        this.clientSecretCredential = clientSecretCredential;
    }

    public TokenCredentialAuthProvider getTokenCredentialAuthProvider() {
        return tokenCredentialAuthProvider;
    }

    public void setTokenCredentialAuthProvider(TokenCredentialAuthProvider tokenCredentialAuthProvider) {
        this.tokenCredentialAuthProvider = tokenCredentialAuthProvider;
    }

    public GraphServiceClient getGraphServiceClient() {
        return graphServiceClient;
    }

    public void setGraphServiceClient(GraphServiceClient graphServiceClient) {
        this.graphServiceClient = graphServiceClient;
    }
}
