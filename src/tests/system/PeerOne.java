package tests.system;

import peer.frame.core.UniversalId;
import peer.graph.core.PeerWeightedLink;
import peer.graph.core.Weight;

public class PeerOne extends SystemTestPeerToPeerActorSystem {
    @Override
    public void createTestingSystem() {
        try {
            Thread.sleep(SystemTestConstants.TIME_TO_WAIT);
        } catch (Exception e) { };
        
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_TWO), new Weight(20)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_THREE), new Weight(30)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_FOUR), new Weight(40)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_FIVE), new Weight(50)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_SIX), new Weight(60)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_SEVEN), new Weight(70)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_EIGHT), new Weight(80)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_NINE), new Weight(90)));
        addPeer(new PeerWeightedLink(new UniversalId(SystemTestConstants.PEER_TEN), new Weight(100)));
    }
}
