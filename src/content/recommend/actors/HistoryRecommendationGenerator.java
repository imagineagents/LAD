package content.recommend.actors;


import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.PoisonPill;
import content.frame.core.Content;
import content.recommend.heuristic.HistoryHeuristic;
import content.recommend.messages.HistoryRecommendationGeneratorInit;
import content.recommend.messages.PeerRecommendation;
import content.recommend.messages.PeerRecommendationRequest;
import content.view.messages.ViewHistoryRequest;
import content.view.messages.ViewHistoryResponse;
import peer.frame.actors.PeerToPeerActor;
import peer.frame.core.ActorPaths;
import peer.frame.core.UniversalId;
import peer.frame.exceptions.PeerRecommendationRequestIdMismatchException;
import peer.frame.exceptions.UnknownMessageException;
import peer.frame.exceptions.WrongPeerIdException;
import peer.frame.messages.PeerToPeerActorInit;

/**
 * Generates Recommendation from this peer based on View History
 *
 */
public class HistoryRecommendationGenerator extends PeerToPeerActor {
    private UniversalId requestingPeer;
    private HistoryHeuristic heuristic;
    
    /**
     * Actor Message processing
     */
    @Override
    public void onReceive(Object message) {
        if (message instanceof PeerToPeerActorInit) {
            PeerToPeerActorInit init = (PeerToPeerActorInit) message;
            super.initialisePeerToPeerActor(init);
        }
        else if (message instanceof HistoryRecommendationGeneratorInit) {
            HistoryRecommendationGeneratorInit init = (HistoryRecommendationGeneratorInit) message;
            this.requestingPeer = init.getRequestingPeerId();
            this.heuristic = init.getHeuristic();
        }
        else if (message instanceof PeerRecommendationRequest) {
            PeerRecommendationRequest request = 
                    (PeerRecommendationRequest) message;
            this.processPeerRecommendationRequest(request);
        }
        else if (message instanceof ViewHistoryResponse) {
            ViewHistoryResponse viewHistoryResponse =
                    (ViewHistoryResponse) message;
            this.processViewHistoryResponse(viewHistoryResponse);
        }
        else {
            throw new UnknownMessageException();
        }
    }
    
    /**
     * Responds to request by requesting view history
     * @param request
     */
    protected void processPeerRecommendationRequest(PeerRecommendationRequest request) {
        if (!request.getOriginalTarget().equals(super.peerId)) 
            throw new WrongPeerIdException(request.getOriginalTarget(), super.peerId);
        if (!this.requestingPeer.equals(request.getOriginalRequester()))
            throw new PeerRecommendationRequestIdMismatchException(request.getOriginalRequester(), this.requestingPeer);
        
        ViewHistoryRequest historyRequest = new ViewHistoryRequest(request);
        
        ActorSelection viewHistorian = getContext().actorSelection(ActorPaths.getPathToViewHistorian());
        viewHistorian.tell(historyRequest, getSelf());
    }
    
    /**
     * Sends a recommendation based on this peer's view history back to this peer's recommender
     * Destined to be sent back to the original peer requesting this recommendation
     * @param response
     */
    protected void processViewHistoryResponse(ViewHistoryResponse response) {
        PeerRecommendation peerRecommendation = this.getPeerRecommendationBasedOnHistory(response);
        
        ActorSelection recommender = getContext().actorSelection(ActorPaths.getPathToRecommender());
        recommender.tell(peerRecommendation, getSelf());
        
        getSelf().tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
    
    /**
     * Creates Peer Recommendation based on View History and HistoryHeuristic
     * @param request
     * @return
     */
    private PeerRecommendation getPeerRecommendationBasedOnHistory(ViewHistoryResponse viewHistoryResponse) {
        PeerRecommendation recommendation;
        List<Content> contentList = this.heuristic.getRecommendation(viewHistoryResponse);
        recommendation = new PeerRecommendation(contentList, this.requestingPeer, super.peerId);
        return recommendation;
    }
}
