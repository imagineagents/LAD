package content.recommend.heuristic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import content.content.Content;
import content.recommend.PeerRecommendation;
import content.view.ContentView;
import content.view.ViewHistoryResponse;

/**
 * Deterministic Heuristic to be used for testing
 * Returns the Top N Content recommendations from View History
 *
 */
public class DeterministicHistoryHeuristic implements HistoryHeuristic {    
    /**
     * Recommendation from this peer based deterministically based on view history scores
     */
    public PeerRecommendation getRecommendation(ViewHistoryResponse viewHistoryResponse) {
        PeerRecommendation recommendation;
        
        List<ContentView> viewList = new LinkedList<ContentView>();
        
        Iterator<ContentView> views = viewHistoryResponse.getViewHistory().iterator();
        while (views.hasNext()) {
            ContentView view = views.next();
            viewList.add(view);
        }
        viewList.sort(new ContentViewComparator());
        while (viewList.size() > TOP_N) {
            viewList.remove(viewList.size() - 1);
        }
        
        List<Content> contentList = new LinkedList<Content>();
        for (ContentView view : viewList) {
            contentList.add(view.getContent());
        }
        recommendation = new PeerRecommendation(contentList, viewHistoryResponse.getMessageTrace());
        return recommendation;
    }
}