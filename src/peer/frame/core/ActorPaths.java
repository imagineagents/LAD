package peer.frame.core;

public class ActorPaths {
    public static final String absolutePath = "/user/";
    
    public static final String getPathToViewer() {
        return absolutePath + ActorNames.VIEWER;
    }
    
    public static final String getPathToRecommender() {
        return absolutePath + ActorNames.RECOMMENDER;
    }
    
    public static final String getPathToAggregator() {
        return getPathToRecommender() + "/" + ActorNames.AGGREGATOR;
    }
    
    public static final String getPathToPeerLinker() {
        return absolutePath + ActorNames.PEER_LINKER;
    }
    
    public static final String getPathToWeighter(UniversalId peerId) {
        return getPathToPeerLinker() + "/" + ActorNames.getWeighterName(peerId);
    }
    
    public static final String getPathToGenerator(UniversalId generateForPeerId) {
        return getPathToRecommender() + "/" + ActorNames.getHistoryGeneratorName(generateForPeerId);
    }
    
    public static final String getPathToViewHistorian() {
        return absolutePath + ActorNames.VIEW_HISTORIAN;
    }
    
    public static final String getPathToInComm() {
        return absolutePath + ActorNames.INBOUND_COMM;
    }
    
    public static final String getPathToOutComm() {
        return absolutePath + ActorNames.OUTBOUND_COMM;
    }
    
    public static final String getPathToRetriever() {
        return absolutePath + ActorNames.RETRIEVER;
    }
    
    public static final String getPathToDatabaser() {
        return absolutePath + ActorNames.DATABASER;
    }
    
    public static final String getPathToGossiper() {
        return absolutePath + ActorNames.GOSSIPER;
    }
    
    public static final String getPathToSimilaritor() {
        return absolutePath + ActorNames.SIMILARITOR;
    }
}
