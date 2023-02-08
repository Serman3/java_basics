package searchengine.parsing;

import java.util.concurrent.CopyOnWriteArraySet;

public class URL implements Comparable<URL>{
    private volatile URL parentUrl;
    private String url;
    private volatile CopyOnWriteArraySet<URL> childUrl;

    public URL(String url) {
        this.url = url;
        parentUrl = null;
        childUrl = new CopyOnWriteArraySet<>();
    }

    public synchronized void addChildUrl(URL url) {
        URL link = getLink();
        if(!link.contains(url.getUrl())) {
            url.setParentUrl(this);
            childUrl.add(url);
        }
    }

    private boolean contains(String url) {
        if (this.url.equals(url)) {
            return true;
        }
        for (URL child : childUrl) {
            if(child.contains(url))
                return true;
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    private synchronized void setParentUrl(URL parentUrl) {
        this.parentUrl = parentUrl;
    }

    public URL getLink() {
        return parentUrl == null ? this : parentUrl.getLink();
    }

    public CopyOnWriteArraySet<URL> getChildUrl() {
        return childUrl;
    }

    @Override
    public int compareTo(URL o) {
        return url.compareTo(o.getUrl());
    }
}
