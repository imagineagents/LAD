package adt.distributedmap.messages;

import peer.frame.core.ActorMessageType;

/**
 * Responds with whether an addition of a key value pair to a Distributed Map was successful or not
 *
 */
public class DistributedMapAdditionResponse extends DistributedMapResponse {
    private Object v;
    
    public DistributedMapAdditionResponse(int requestNum, int bucketNum, boolean success, Object k, Object v) {
        super(requestNum, bucketNum, success, k, ActorMessageType.DistributedMapAdditionResponse);
        this.v = v;
    }
    
    public Object getValue() {
        return this.v;
    }
}
