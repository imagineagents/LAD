package tests.peer.communicate;

import akka.actor.ActorRef;
import akka.actor.Props;
import content.recommend.PeerRecommendationRequest;
import core.ActorNames;
import core.PeerToPeerActorInit;
import tests.actors.DummyActor;
import tests.actors.DummyInit;

public class DummyRecommender extends DummyActor {
    @Override
    public void onReceive(Object message) {
        if (message instanceof PeerToPeerActorInit) {
            PeerToPeerActorInit init = (PeerToPeerActorInit) message;
            super.initialisePeerToPeerActor(init);
        }
        else if (message instanceof DummyInit) {
            DummyInit init = (DummyInit) message;
            super.logger = init.getLogger();
            this.createAggregator();
        }
        else if (message instanceof PeerRecommendationRequest) {
            PeerRecommendationRequest request = (PeerRecommendationRequest) message;
            super.logger.logMessage("Recommender received PeerRecommendationRequest");
            super.logger.logMessage("Type: " + request.getType().toString());
            super.logger.logMessage("OriginalRequester: " + request.getOriginalRequester());
            super.logger.logMessage("OriginalTarget: " + request.getOriginalTarget());
        }
    }
    
    private void createAggregator() {
        ActorRef aggregator = getContext().actorOf(Props.create(DummyAggregator.class), ActorNames.AGGREGATOR);
        PeerToPeerActorInit initPeerId = new PeerToPeerActorInit(super.peerId, ActorNames.AGGREGATOR);
        aggregator.tell(initPeerId, null);
        
        DummyInit dummyInit = new DummyInit(super.logger);
        aggregator.tell(dummyInit, null);
    }
}
