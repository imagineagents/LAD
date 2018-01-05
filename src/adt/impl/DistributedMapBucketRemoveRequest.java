package adt.impl;

import peer.core.ActorMessageType;

public class DistributedMapBucketRemoveRequest extends DistributedMapBucketRequest {
    
    public DistributedMapBucketRemoveRequest(int requestNum, int index, Object k) {
        super(requestNum, index, k, ActorMessageType.DistributedMapBucketRemoveRequest);
    }
}
